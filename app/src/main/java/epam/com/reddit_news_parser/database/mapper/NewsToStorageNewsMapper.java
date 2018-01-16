package epam.com.reddit_news_parser.database.mapper;

import android.support.annotation.NonNull;

import epam.com.reddit_news_parser.database.entities.StorageNews;
import epam.com.reddit_news_parser.entities.News;
import epam.com.reddit_news_parser.utils.Mapper;

/**
 * Created by yuri on 1/16/18.
 */

public class NewsToStorageNewsMapper extends Mapper<News, StorageNews> {

    @NonNull
    @Override
    public StorageNews map(@NonNull News news) {
        return new StorageNews(news.getTitle(), news.getUrl(),
                               news.getAuthor(), news.getImage()
        );
    }
}
