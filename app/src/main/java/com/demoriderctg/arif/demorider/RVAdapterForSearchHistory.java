package com.demoriderctg.arif.DemoRider;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demoriderctg.arif.demorider.R;

import java.util.List;

/**
 * Created by Arif on 11/22/2017.
 */

public class RVAdapterForSearchHistory extends RecyclerView.Adapter<RVAdapterForSearchHistory.PersonViewHolder> {
    public List<SearchHistory> searchHistories;
    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView location;
        TextView city;
        ImageView locationPhoto;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            location = (TextView)itemView.findViewById(R.id.locatio_name);
            city = (TextView)itemView.findViewById(R.id.city);
            locationPhoto = (ImageView)itemView.findViewById(R.id.location_photo);

        }
    }



    RVAdapterForSearchHistory(List<SearchHistory> persons){
        this.searchHistories = persons;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search_history, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.location.setText(searchHistories.get(i).Place);
        personViewHolder.city.setText(searchHistories.get(i).searchLocation);
        personViewHolder.locationPhoto.setImageResource(searchHistories.get(i).photoId);
    }

    @Override
    public int getItemCount() {

        return searchHistories.size();
    }
}