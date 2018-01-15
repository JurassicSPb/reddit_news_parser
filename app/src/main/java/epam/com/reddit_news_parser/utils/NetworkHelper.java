package epam.com.reddit_news_parser.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import epam.com.reddit_news_parser.entities.ListItem;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yuri on 12/27/17.
 */

public class NetworkHelper {
    private static       NetworkHelper networkHelper = new NetworkHelper();
    private static       String        url           = "https://www.reddit.com/r/gamernews";
    private static final String        jsonEnd       = ".json";
    private static final String        qCount        = "?count=";
    private static final String        after         = "&after=";
    private static final int           counter       = 10;
    private String          afterId;
    @Nullable
    private NetworkCallback callback;

    private List<ListItem> news = new ArrayList<>();

    private NetworkHelper() {
    }

    public static NetworkHelper getNetworkHelper() {
        return networkHelper;
    }

    public void onRequest(@NonNull OkHttpClient client, int urlState) throws IOException {
        if (urlState == 0) {
            url = url + jsonEnd + qCount + counter;
        } else {
            url = url + jsonEnd + qCount + counter + after + afterId;
        }

        final Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request)
              .enqueue(new Callback() {
                  @Override
                  public void onFailure(@NonNull Call call, @NonNull IOException e) {

                  }

                  @Override
                  public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                      try {
                          NetworkHelper.this.onResponse(response.body().string());
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                  }
              });
    }

    private void onResponse(@NonNull String responseBody) throws JSONException {
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

            final JSONObject image = object.getJSONObject("preview");
            final JSONArray images = image.getJSONArray("images");

            final JSONObject source = images.getJSONObject(0).getJSONObject("source");
            listItem.setImage(source.getString("url"));

            news.add(listItem);

            if (callback != null) {
                callback.onResult();
            }
        }
    }

    public void loadData(@NonNull NetworkCallback callback) {
        this.callback = callback;
    }

    @NonNull
    public List<ListItem> getNews() {
        return news;
    }
}
