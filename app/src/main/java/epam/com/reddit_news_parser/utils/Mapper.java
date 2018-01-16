package epam.com.reddit_news_parser.utils;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuri on 1/16/18.
 */

public abstract class Mapper<From, To> {
    @NonNull
    public abstract To map(@NonNull From from);

    @NonNull
    public List<To> map(@NonNull List<From> from) {
        final List<To> newList = new ArrayList<>();
        for (From f : from) {
            newList.add(map(f));
        }
        return newList;
    }
}