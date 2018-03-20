package com.demoriderctg.arif.demorider.Adapters.NewsCard;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.demoriderctg.arif.demorider.R;
import com.demoriderctg.arif.demorider.models.ApiModels.NewsCardModels.NewsCard;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SakibRahman on 1/22/2018.
 */

public class NewsCardAdapter extends RecyclerView.Adapter<NewsCardAdapter.ViewHolder> {

    private Context context;
    private ArrayList<NewsCard> newsCard;

    @Override
    public NewsCardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_card_row_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsCardAdapter.ViewHolder viewHolder, int position) {

        NewsCard newsCardRow = newsCard.get(position);
       // viewHolder.iv_news_card_image_view.setText(newsCardRow.getDateTime());
        viewHolder.tv_news_card_title.setText(newsCardRow.getTitle());
        viewHolder.tv_news_card_description.setText(newsCardRow.getDescription());
        Picasso.with(context).invalidate(newsCardRow.getImageUrl());
        Picasso.with(context)
                .load(newsCardRow.getImageUrl())
                .placeholder(R.drawable.profile_image)
                .error(R.drawable.profile_image)
                .into(viewHolder.iv_news_card_image_view);
        if(newsCardRow.getButton1()==null)
            viewHolder.button_1.setVisibility(View.GONE);

        if(newsCardRow.getButton2()==null)
            viewHolder.button_2.setVisibility(View.GONE);

        if(newsCardRow.getButton3()==null)
            viewHolder.button_3.setVisibility(View.GONE);

        viewHolder.button_1.setText(newsCardRow.getButton1());
        viewHolder.button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(newsCardRow.getUrl1()));
                context.startActivity(i);
            }
        });

        viewHolder.button_2.setText(newsCardRow.getButton2());
        viewHolder.button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(newsCardRow.getUrl2()));
                context.startActivity(i);
            }
        });

        viewHolder.button_3.setText(newsCardRow.getButton3());
        viewHolder.button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(newsCardRow.getUrl3()));
                context.startActivity(i);
            }
        });
    }

    public NewsCardAdapter(Context mContext, ArrayList<NewsCard> newsCardRow) {
        this.context = mContext;
        this.newsCard = newsCardRow;
    }

    @Override
    public int getItemCount() {
        return newsCard.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_news_card_image_view;
        private TextView tv_news_card_title;
        private TextView tv_news_card_description;
        private Button button_1;
        private Button button_2;
        private Button button_3;

        public ViewHolder(View view) {
            super(view);

                iv_news_card_image_view = (ImageView) view.findViewById(R.id.news_card_image_view);
                tv_news_card_title = (TextView) view.findViewById(R.id.tv_news_card_title);
                tv_news_card_description = (TextView) view.findViewById(R.id.tv_news_card_description);
                button_1 = (Button) view.findViewById(R.id.news_card_button1);
                button_2 = (Button) view.findViewById(R.id.news_card_button2);
                button_3 = (Button) view.findViewById(R.id.news_card_button3);

//            tv_country = (TextView)view.findViewById(R.id.tv_country);
        }
    }

}
