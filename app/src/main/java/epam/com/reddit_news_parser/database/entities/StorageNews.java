package epam.com.reddit_news_parser.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by yuri on 1/16/18.
 */

@Entity
public class StorageNews {

    @PrimaryKey(autoGenerate = true)
    private long   id;
    private String title;
    private String url;
    private String author;
    private String image;

    public StorageNews(String title, String url, String author, String image) {
        this.title = title;
        this.url = url;
        this.author = author;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthor() {
        return author;
    }

    public String getImage() {
        return image;
    }
}
