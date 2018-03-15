package com.example.ashish.n3wsfeed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{

    private Context mContext;
    private ArrayList<NewsItem> mNewsList;
    private OnRecyclerItemClickListener mListener;

    public interface OnRecyclerItemClickListener{

        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnRecyclerItemClickListener listener){
        mListener = listener;
    }

    public NewsAdapter(Context context, ArrayList<NewsItem> newsList){
        mContext = context;
        mNewsList = newsList;
    }



    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.single_news_item, parent, false);
        return new NewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {

        NewsItem currentNews = mNewsList.get(position);

        String imageUrl = currentNews.getImageUrl();
        String newsTitle = currentNews.getTitleOfNews();

        holder.mTextViewTitle.setText(newsTitle);
        Picasso.with(mContext).load(imageUrl).fit().centerInside()
                .into(holder.mNewsImageView);

    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{
        public ImageView mNewsImageView;
        public TextView mTextViewTitle;

        public NewsViewHolder(View itemView) {
            super(itemView);

            mNewsImageView = itemView.findViewById(R.id.imageView);
            mTextViewTitle = itemView.findViewById(R.id.title_of_news);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mListener != null){

                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
