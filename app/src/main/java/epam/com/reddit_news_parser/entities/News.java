package epam.com.reddit_news_parser.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yuri on 1/9/18.
 */

public class News implements Parcelable {
    private String title;
    private String url;
    private String author;
    private String image;

    public News(String title, String url, String author, String image) {
        this.title = title;
        this.url = url;
        this.author = author;
        this.image = image;
    }

    public News(String title, String url, String author) {
        this.title = title;
        this.url = url;
        this.author = author;
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

    public void setImage(String image) {
        this.image = image;
    }

    private News(Parcel in) {
        title = in.readString();
        url = in.readString();
        author = in.readString();
        image = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
        out.writeString(url);
        out.writeString(author);
        out.writeString(image);
    }

    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
