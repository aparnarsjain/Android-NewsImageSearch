package com.example.aparna.newsimagesearch.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aparna.newsimagesearch.Models.Article;
import com.example.aparna.newsimagesearch.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aparna on 2/11/16.
 */
public class ArticlesAdapter extends
        RecyclerView.Adapter<ArticlesAdapter.ViewHolder>  {
    // Store a member variable for the Articles
    private List<Article> mArticles;
    Context mContext;
    // Define listener member variable
    private static OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    // Pass in the Article array into the constructor
    public ArticlesAdapter(Context context, List<Article> Articles) {
        mArticles = Articles;
        mContext = context;
    }

    public void addAll(ArrayList<Article> articles) {
        mArticles.addAll(articles);
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView ivArticleImage;
        public TextView tvTitle;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            ivArticleImage = (ImageView) itemView.findViewById(R.id.ivImage);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View ArticleView = inflater.inflate(R.layout.item_article_result, parent, false);

        ViewHolder viewHolder = new ViewHolder(ArticleView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Article article = mArticles.get(position);
        viewHolder.tvTitle.setText(article.getHeadline());
        viewHolder.ivArticleImage.setImageResource(0);
        //populate the image in the background
        String thumbnail = article.getThumbnail();
        if(!TextUtils.isEmpty(thumbnail)) {
//            Picasso.with(mContext).load(thumbnail).fit().into(viewHolder.ivArticleImage);
            Glide.with(mContext).load(thumbnail).centerCrop().into(viewHolder.ivArticleImage);

        }else {
            viewHolder.ivArticleImage.setVisibility(View.GONE);
        }

    }
    public void clearAdapter()
    {
        mArticles.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mArticles.size();
    }

}
