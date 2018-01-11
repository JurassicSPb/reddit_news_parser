package epam.com.reddit_news_parser.entities;

import okhttp3.OkHttpClient;

/**
 * Created by yuri on 12/27/17.
 */

public class OKHttpInstance {
    private static OkHttpClient okHttpClient = new OkHttpClient();

    private OKHttpInstance() {
    }

    public static OkHttpClient getInstance() {
        return okHttpClient;
    }
}
