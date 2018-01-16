package epam.com.reddit_news_parser.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yuri on 1/16/18.
 */

public class ExecutorServiceHelper {
    private static ExecutorServiceHelper helper   = new ExecutorServiceHelper();
    private        ExecutorService       executor = Executors.newSingleThreadExecutor();

    private ExecutorServiceHelper() {
    }

    public static ExecutorServiceHelper getInstance() {
        return helper;
    }

    public ExecutorService getExecutor() {
        return executor;
    }
}
