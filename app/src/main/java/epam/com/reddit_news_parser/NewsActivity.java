package epam.com.reddit_news_parser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements UpdateCallback {
    public final static String BROADCAST_ACTION = "action";
    public final static String LOAD_MORE        = "loadMore";
    private NewsAdapter       adapter;
    private Intent            intent;
    private BroadcastReceiver receiver;
    private ProgressBar       progressBar;
    private ProgressBar       mainProgressBar;
    private List<ListItem>          news          = new ArrayList<>();
    private OnListItemClickListener clickListener = new OnListItemClickListener() {
        @Override
        public void onClick(View v, int position) {
            Intent intent = new Intent(NewsActivity.this, ContactsActivity.class);
            intent.putExtra("news", news.get(position));
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        if (savedInstanceState != null) {
            news = savedInstanceState.getParcelableArrayList("news");
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsAdapter(news, clickListener, this);
        recyclerView.setAdapter(adapter);

        progressBar = findViewById(R.id.progress_bar);
        mainProgressBar = findViewById(R.id.main_progress_bar);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    updateNews();
                }
            }
        });

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                news = intent.getParcelableArrayListExtra("news");
                stopUpdate();
                adapter.setNews(news);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        };

        IntentFilter filter = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        intent = new Intent(this, NewsService.class);
        startService(intent);
    }

    @Override
    public void updateNews() {
        progressBar.setVisibility(View.VISIBLE);
        intent.putExtra(LOAD_MORE, 1);
        startService(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("news", new ArrayList<>(news));
    }

    @Override
    public void stopUpdate() {
        stopService(intent);
        mainProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
