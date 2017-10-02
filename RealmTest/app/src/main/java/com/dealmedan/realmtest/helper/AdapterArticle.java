package com.dealmedan.realmtest.helper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dealmedan.realmtest.R;
import com.dealmedan.realmtest.model.ArticleModel;

import java.util.ArrayList;

/**
 * Created by daniel on 1/25/2017.
 */
public class AdapterArticle extends RecyclerView.Adapter<AdapterArticle.ViewHolder> {
    private final OnItemClickListener listener;
    private ArrayList<ArticleModel> article;

    public AdapterArticle(ArrayList<ArticleModel> article, OnItemClickListener listener) {
        this.article = article;
        this.listener = listener;
    }

    @Override
    public AdapterArticle.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_article, null);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }


    @Override
    public void onBindViewHolder(AdapterArticle.ViewHolder holder, int position) {
        holder.click(article.get(position), listener);
        holder.tvId.setText(String.valueOf(article.get(position).getId()));
        holder.title.setText(article.get(position).getTitle());
        holder.description.setText(article.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return article.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, title, description;

        public ViewHolder(View itemView) {
            super(itemView);
            tvId = (TextView) itemView.findViewById(R.id.tv_id);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            description = (TextView) itemView.findViewById(R.id.tv_description);
        }

        public void click(final ArticleModel articleModel, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(articleModel);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onClick(ArticleModel item);
    }
}
