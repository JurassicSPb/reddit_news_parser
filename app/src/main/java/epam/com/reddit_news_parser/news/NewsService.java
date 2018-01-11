package epam.com.reddit_news_parser.news;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import epam.com.reddit_news_parser.entities.OKHttpInstance;
import epam.com.reddit_news_parser.utils.NetworkHelper;

/**
 * Created by yuri on 12/27/17.
 */

public class NewsService extends Service {
    private NetworkHelper networkHelper;
    private Executor executor = Executors.newSingleThreadExecutor();

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

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (loadMore == 1) {
                        networkHelper.onRequest(OKHttpInstance.getInstance(), 1);
                        networkHelper.onResponse();
                    } else {
                        networkHelper.onRequest(OKHttpInstance.getInstance(), 0);
                        networkHelper.onResponse();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent broadcastIntent = new Intent(NewsActivity.BROADCAST_ACTION);
                broadcastIntent.putParcelableArrayListExtra("news", new ArrayList<>(networkHelper.getNews()));
                sendBroadcast(broadcastIntent);
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }
}
