package epam.com.reddit_news_parser;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yuri on 12/27/17.
 */

class NetworkHelper {
    private static NetworkHelper networkHelper = new NetworkHelper();
    private static       String url     = "https://www.reddit.com/r/news/top";
    private static final String jsonEnd = ".json";
    private static final String qCount  = "?count=";
    private static final String after   = "&after=";
    private static final int    counter = 10;
    private String responseBody;
    private String afterId;

    private List<ListItem> news = new ArrayList<>();

    private NetworkHelper() {
    }

    public static NetworkHelper getNetworkHelper() {
        return networkHelper;
    }

    void onRequest(@NonNull OkHttpClient client, int urlState) throws IOException {
        if (urlState == 0) {
            url = url + jsonEnd  + qCount + counter;
        } else {
            url = url + jsonEnd + qCount + counter + after + afterId;
        }

        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Response response = client.newCall(request).execute();
        responseBody = response.body().string();
    }

    void onResponse() throws JSONException {
        final JSONObject data = new JSONObject(responseBody).getJSONObject("data");
        afterId = data.getString("after");

        final JSONArray items = data.getJSONArray("children");

        for (int i = 0; i < items.length(); i++) {
            final JSONObject object = items.getJSONObject(i).getJSONObject("data");

            final ListItem listItem = new ListItem(
                    object.getString("title"),
                    object.getString("url"),
                    object.getString("author")
            );
            news.add(listItem);
        }
    }

    List<ListItem> getNews() {
        return news;
    }
}
