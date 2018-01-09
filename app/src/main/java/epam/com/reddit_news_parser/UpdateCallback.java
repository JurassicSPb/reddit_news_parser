package epam.com.reddit_news_parser;

/**
 * Created by yuri on 1/9/18.
 */

public interface UpdateCallback {

    void updateNews(int position);

    void stopUpdate();
}
