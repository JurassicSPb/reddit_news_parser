package epam.com.reddit_news_parser.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import epam.com.reddit_news_parser.database.entities.StorageNews;

/**
 * Created by yuri on 1/16/18.
 */

@Dao
public interface NewsDao {

    @Insert
    void insert(List<StorageNews> storageNews);

    @Query("SELECT * FROM storageNews")
    List<StorageNews> getAll();

    @Query("DELETE FROM storageNews")
    void deleteAll();
}
