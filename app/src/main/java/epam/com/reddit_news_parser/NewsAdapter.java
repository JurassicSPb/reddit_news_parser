package epam.com.reddit_news_parser;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by yuri on 1/9/18.
 */

class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<ListItem>          news;
    private OnListItemClickListener clickListener;
    private Context context;
    private StyleSpan styleSpan;
    private ForegroundColorSpan colorSpan;

    NewsAdapter(List<ListItem> news, OnListItemClickListener clickListener, Context context) {
        this.news = news;
        this.clickListener = clickListener;
        this.context = context;
    }

    void setNews(List<ListItem> news) {
        this.news = news;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);

        styleSpan = new StyleSpan(Typeface.BOLD);
        colorSpan = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary));

        return new NewsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
        String title = news.get(position).getTitle();
        String author = news.get(position).getAuthor();
        String url = news.get(position).getUrl();
        String image = news.get(position).getImage();

        final SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("\n").append(title).append("\n\n").append(author).append("\n\n").append(url).append("\n");

        builder.setSpan(styleSpan, 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(colorSpan, title.length() + author.length() + 5, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.title.setText(builder);

        Picasso.with(context)
               .load(image)
               .fit()
               .centerCrop()
               .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView  title;
        private ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.content);
            image = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }
    }
}
