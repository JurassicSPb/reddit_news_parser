package epam.com.reddit_news_parser.news;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import epam.com.reddit_news_parser.entities.OKHttpInstance;
import epam.com.reddit_news_parser.utils.NetworkCallback;
import epam.com.reddit_news_parser.utils.NetworkHelper;

/**
 * Created by yuri on 12/27/17.
 */

public class NewsService extends Service {
    private NetworkHelper networkHelper;
    private Executor        executor = Executors.newSingleThreadExecutor();
    private NetworkCallback callback = new NetworkCallback() {
        @Override
        public void onResult() {
            Intent broadcastIntent = new Intent(NewsActivity.BROADCAST_ACTION);
            broadcastIntent.putParcelableArrayListExtra("news", new ArrayList<>(networkHelper.getNews()));
            sendBroadcast(broadcastIntent);
        }

        @Override
        public void onFailure() {

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

        networkHelper = NetworkHelper.getNetworkHelper();
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
