package epam.com.reddit_news_parser.news;

import android.app.Service;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

import epam.com.reddit_news_parser.database.AppDatabase;
import epam.com.reddit_news_parser.database.AppDatabaseImpl;
import epam.com.reddit_news_parser.database.mapper.NewsToStorageNewsMapper;
import epam.com.reddit_news_parser.database.mapper.StorageNewsToNewsMapper;
import epam.com.reddit_news_parser.entities.OKHttpInstance;
import epam.com.reddit_news_parser.utils.ExecutorServiceHelper;
import epam.com.reddit_news_parser.utils.NetworkCallback;
import epam.com.reddit_news_parser.utils.NetworkHelper;

/**
 * Created by yuri on 12/27/17.
 */

public class NewsService extends Service {
    private NetworkHelper   networkHelper;
    private ExecutorService executor;
    private AppDatabaseImpl appDatabase;
    private Intent          broadcastIntent = new Intent(NewsActivity.BROADCAST_ACTION);
    private NetworkCallback callback        = new NetworkCallback() {
        @Override
        public void onResult() {
            appDatabase.deleteNews();
            appDatabase.saveNews(networkHelper.getNews());

            broadcastIntent.putParcelableArrayListExtra("news", new ArrayList<>(networkHelper.getNews()));
            sendBroadcast(broadcastIntent);
        }

        @Override
        public void onFailure() {
            broadcastIntent.putExtra("error", true);
            broadcastIntent.putParcelableArrayListExtra("news", new ArrayList<>(appDatabase.getNews()));
            sendBroadcast(broadcastIntent);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        networkHelper = NetworkHelper.getInstance();
        final ExecutorServiceHelper executorServiceHelper = ExecutorServiceHelper.getInstance();
        executor = executorServiceHelper.getExecutor();

        appDatabase = new AppDatabaseImpl(
                Room.databaseBuilder(this, AppDatabase.class, "beacons-database").build(),
                new NewsToStorageNewsMapper(),
                new StorageNewsToNewsMapper()
        );
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final int loadMore = intent.getIntExtra("loadMore", 0);

        executor.execute(() -> {
            try {
                networkHelper.loadData(callback);
                if (loadMore == 1) {
                    networkHelper.onRequest(OKHttpInstance.getInstance(), 1);
                } else {
                    networkHelper.onRequest(OKHttpInstance.getInstance(), 0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }
}
