package com.demoriderctg.arif.demorider.Adapters.History;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.demoriderctg.arif.demorider.R;
import com.demoriderctg.arif.demorider.models.ApiModels.RideHistory.ClientHistory;
import com.demoriderctg.arif.demorider.models.ApiModels.RideHistory.RideHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SakibRahman on 1/22/2018.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ClientHistory> clientHistory;

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_row_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.ViewHolder viewHolder, int position) {

        ClientHistory history = clientHistory.get(position);
        viewHolder.tv_date_time.setText(history.getDateTime());
        viewHolder.tv_distance_minute.setText(history.getDistanceTime());
        viewHolder.tv_src_address.setText(history.getPickPointAddress());
        viewHolder.tv_dest_address.setText(history.getDestinationAddress());
        viewHolder.tv_total_fare.setText(history.getTotalFare());
    }

    public HistoryAdapter(Context mContext, ArrayList<ClientHistory> history) {
        this.context = mContext;
        this.clientHistory = history;
    }

    @Override
    public int getItemCount() {
        return clientHistory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_date_time;
        private TextView tv_total_fare;
        private TextView tv_distance_minute;
        private TextView tv_src_address;
        private TextView tv_dest_address;

        public ViewHolder(View view) {
            super(view);

                tv_date_time = (TextView) view.findViewById(R.id.date_time);
                tv_total_fare = (TextView) view.findViewById(R.id.total_fare);
                tv_distance_minute = (TextView) view.findViewById(R.id.distance_minute);
                tv_src_address = (TextView) view.findViewById(R.id.source_address);
                tv_dest_address = (TextView) view.findViewById(R.id.destination_address);

//            tv_country = (TextView)view.findViewById(R.id.tv_country);
        }
    }

}
