package com.example.android_news_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<News> news;

    private final OnNewsClickListener onNewsListener;

    NewsAdapter(Context context, List<News> news, OnNewsClickListener onNewsListener) {
        this.news = news;
        this.inflater = LayoutInflater.from(context);
        this.onNewsListener = onNewsListener;
    }


    //onCreateViewHolder: возвращает объект ViewHolder, который будет хранить данные по одному объекту News
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }


    //onBindViewHolder: выполняет привязку объекта ViewHolder к объекту News по определенной позиции.
    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        News nNews = news.get(position);

        Glide
                .with(holder.itemView.getContext())
                .load(nNews.getImageUrl())
                .into(holder.picture);

        holder.name.setText(nNews.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                // вызываем метод слушателя, передавая ему данные
                onNewsListener.onNewsClick(nNews, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView picture;
        final TextView name;



        ViewHolder(View view){
            super(view);
            picture = view.findViewById(R.id.news_picture);
            name = view.findViewById(R.id.title_name);
        }
    }

    interface OnNewsClickListener{
        void onNewsClick(News news, int position);
    }


}
