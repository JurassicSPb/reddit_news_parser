package epam.com.reddit_news_parser.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import epam.com.reddit_news_parser.database.dao.NewsDao;
import epam.com.reddit_news_parser.database.entities.StorageNews;

/**
 * Created by yuri on 1/16/18.
 */

@Database(
        entities = StorageNews.class, version = 1
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract NewsDao newsDao();
}
