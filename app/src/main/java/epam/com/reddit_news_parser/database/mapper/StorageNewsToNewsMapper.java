package epam.com.reddit_news_parser.database.mapper;

import android.support.annotation.NonNull;

import epam.com.reddit_news_parser.database.entities.StorageNews;
import epam.com.reddit_news_parser.entities.News;
import epam.com.reddit_news_parser.utils.Mapper;

/**
 * Created by yuri on 1/16/18.
 */

public class StorageNewsToNewsMapper extends Mapper<StorageNews, News> {

    @NonNull
    @Override
    public News map(@NonNull StorageNews storageNews) {
        return new News(storageNews.getTitle(), storageNews.getUrl(),
                        storageNews.getAuthor(), storageNews.getImage()
        );
    }
}
