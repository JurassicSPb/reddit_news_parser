package epam.com.reddit_news_parser.database;

import android.support.annotation.NonNull;

import java.util.List;

import epam.com.reddit_news_parser.database.mapper.NewsToStorageNewsMapper;
import epam.com.reddit_news_parser.database.mapper.StorageNewsToNewsMapper;
import epam.com.reddit_news_parser.entities.News;

/**
 * Created by yuri on 1/16/18.
 */

public class AppDatabaseImpl {
    @NonNull
    private final AppDatabase             appDatabase;
    @NonNull
    private final NewsToStorageNewsMapper newsToStorageNewsMapper;
    @NonNull
    private final StorageNewsToNewsMapper storageNewsToNewsMapper;

    public AppDatabaseImpl(@NonNull AppDatabase appDatabase,
                           @NonNull NewsToStorageNewsMapper newsToStorageNewsMapper,
                           @NonNull StorageNewsToNewsMapper storageNewsToNewsMapper) {
        this.appDatabase = appDatabase;
        this.newsToStorageNewsMapper = newsToStorageNewsMapper;
        this.storageNewsToNewsMapper = storageNewsToNewsMapper;
    }

    public void saveNews(@NonNull List<News> news) {
        appDatabase.newsDao().insert(newsToStorageNewsMapper.map(news));
    }

    public void deleteNews() {
        appDatabase.newsDao().deleteAll();
    }

    @NonNull
    public List<News> getNews() {
        return storageNewsToNewsMapper.map(appDatabase.newsDao().getAll());
    }
}
