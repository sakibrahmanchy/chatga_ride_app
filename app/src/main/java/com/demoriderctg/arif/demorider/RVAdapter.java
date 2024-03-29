package com.demoriderctg.arif.demorider;

import android.view.LayoutInflater;
import android.view.View;

        import android.support.v7.widget.CardView;
        import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {
   public List<SearchHistory> searchHistories;
    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }



    public RVAdapter(List<SearchHistory> persons, autoComplete autoComplete, String activatyName){
        this.searchHistories = persons;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
          personViewHolder.personName.setText(searchHistories.get(i).Place);
        personViewHolder.personAge.setText(searchHistories.get(i).searchLocation);
        personViewHolder.personPhoto.setImageResource(searchHistories.get(i).photoId);
    }

    @Override
    public int getItemCount() {

        return searchHistories.size();
    }
}