package epam.com.reddit_news_parser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements UpdateCallback {
    public final static String BROADCAST_ACTION = "action";
    public final static String LOAD_MORE        = "loadMore";
    public final static String COUNT            = "count";
    private NewsAdapter       adapter;
    private Intent            intent;
    private BroadcastReceiver receiver;
    private List<ListItem>          news          = new ArrayList<>();
    private OnListItemClickListener clickListener = new OnListItemClickListener() {
        @Override
        public void onClick(View v, int position) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        intent = new Intent(this, NewsService.class);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsAdapter(news, clickListener);
        recyclerView.setAdapter(adapter);

        startService(intent);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                news = intent.getParcelableArrayListExtra("news");
                stopUpdate();// on success stop service (callback)
                adapter.setNews(news);
                adapter.notifyDataSetChanged();
            }
        };

        IntentFilter filter = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    public void updateNews(int adapterPosition) { // on adapter scrolled
        intent.putExtra(LOAD_MORE, 1);
        intent.putExtra(COUNT, 1); // value from adapter
        startService(intent);
    }

    @Override
    public void stopUpdate() {
        stopService(intent);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
