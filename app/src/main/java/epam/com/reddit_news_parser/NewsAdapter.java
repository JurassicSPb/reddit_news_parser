package epam.com.reddit_news_parser;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yuri on 1/9/18.
 */

class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<ListItem>          news;
    private OnListItemClickListener clickListener;


    NewsAdapter(List<ListItem> news, OnListItemClickListener clickListener) {
        this.news = news;
        this.clickListener = clickListener;
    }

    void setNews(List<ListItem> news) {
        this.news = news;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
        String title = news.get(position).getTitle();
        String author = news.get(position).getAuthor();
        String url = news.get(position).getUrl();

        StringBuilder builder = new StringBuilder();
        builder.append("\n").append(title).append("\n\n").append(author).append("\n\n").append(url).append("\n");

        holder.title.setText(builder.toString());
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.content);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }
    }
}
