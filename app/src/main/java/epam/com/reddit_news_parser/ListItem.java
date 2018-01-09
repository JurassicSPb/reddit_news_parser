package epam.com.reddit_news_parser;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yuri on 1/9/18.
 */

public class ListItem implements Parcelable {
    private String title;
    private String thumbnail;
    private String url;
    private String subreddit;
    private String author;

    public ListItem(String title, String thumbnail, String url, String subreddit, String author) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.url = url;
        this.subreddit = subreddit;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public String getAuthor() {
        return author;
    }

    private ListItem(Parcel in) {
        title = in.readString();
        thumbnail = in.readString();
        url = in.readString();
        subreddit = in.readString();
        author = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
        out.writeString(thumbnail);
        out.writeString(url);
        out.writeString(subreddit);
        out.writeString(author);
    }

    public static final Parcelable.Creator<ListItem> CREATOR = new Parcelable.Creator<ListItem>() {
        public ListItem createFromParcel(Parcel in) {
            return new ListItem(in);
        }

        public ListItem[] newArray(int size) {
            return new ListItem[size];
        }
    };
}
