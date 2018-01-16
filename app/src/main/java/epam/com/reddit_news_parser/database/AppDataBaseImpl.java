package epam.com.reddit_news_parser.database;

import android.support.annotation.NonNull;

import java.util.List;

import epam.com.reddit_news_parser.database.mapper.NewsToStorageNewsMapper;
import epam.com.reddit_news_parser.entities.News;

/**
 * Created by yuri on 1/16/18.
 */

public class AppDataBaseImpl {
    @NonNull
    private final AppDatabase             appDatabase;
    @NonNull
    private final NewsToStorageNewsMapper newsToStorageNewsMapper;

    public AppDataBaseImpl(@NonNull AppDatabase appDatabase,
                           @NonNull NewsToStorageNewsMapper newsToStorageNewsMapper) {
        this.appDatabase = appDatabase;
        this.newsToStorageNewsMapper = newsToStorageNewsMapper;
    }

    public void saveNews(@NonNull List<News> news) {
        appDatabase.newsDao().insert(newsToStorageNewsMapper.map(news));
    }
}
