package com.example.aparna.newsimagesearch;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aparna.newsimagesearch.Models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aparna on 2/9/16.
 */
public class ArticleArrayAdapter extends ArrayAdapter<Article>{
    public  ArticleArrayAdapter(Context context, List<Article> articles) {
        super(context, android.R.layout.simple_list_item_1, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the data item for the current position
        Article article = this.getItem(position);

        ViewHolder holder;
        //check if the view is being recycled
        //not using recycled view, inflate the layout
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_article_result, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.tvTitle.setText(article.getHeadline());
        holder.imageView.setImageResource(0);
        //populate the image in the background
        String thumbnail = article.getThumbnail();

        if(!TextUtils.isEmpty(thumbnail)) {
            Picasso.with(getContext()).load(thumbnail).into(holder.imageView);
        }
        return convertView;


    }

    static class ViewHolder {
        @Bind(R.id.ivImage) ImageView imageView;
        @Bind(R.id.tvTitle) TextView tvTitle;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
