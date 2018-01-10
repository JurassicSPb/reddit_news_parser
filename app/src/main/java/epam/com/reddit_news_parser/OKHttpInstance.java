package epam.com.reddit_news_parser;

import okhttp3.OkHttpClient;

/**
 * Created by yuri on 12/27/17.
 */

class OKHttpInstance {
    private static OkHttpClient okHttpClient = new OkHttpClient();

    private OKHttpInstance() {
    }

    static OkHttpClient getInstance() {
        return okHttpClient;
    }
}
