package com.demoriderctg.arif.demorider.Adapters.History;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.demoriderctg.arif.demorider.R;
import com.demoriderctg.arif.demorider.models.ApiModels.RideHistory.ClientHistory;
import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;


import java.util.HashSet;
import java.util.List;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class FoldingCellListAdapter extends ArrayAdapter<ClientHistory> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;

    public FoldingCellListAdapter(Context context, List<ClientHistory> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // get item for selected view
        ClientHistory item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell, parent, false);
            // binding view parts to view

            viewHolder.price = cell.findViewById(R.id.title_price);
            viewHolder.durationInshort = cell.findViewById(R.id.tv_duration_short);
            viewHolder.date = cell.findViewById(R.id.title_time_label);
            viewHolder.fromAddress = cell.findViewById(R.id.title_from_address);
            viewHolder.toAddress = cell.findViewById(R.id.title_to_address);
            viewHolder.duration = cell.findViewById(R.id.tv_duration_content);
            viewHolder.driverImage = cell.findViewById(R.id.content_avatar);
            viewHolder.avaterName = cell.findViewById(R.id.content_name_view);
            viewHolder.rating = cell.findViewById(R.id.content_rating_stars);
            viewHolder.sourceAddress = cell.findViewById(R.id.content_from_address_2);
            viewHolder.destinationAddress = cell.findViewById(R.id.content_to_address_2);
            viewHolder.distance=cell.findViewById(R.id.tv_distance_content);
            viewHolder.fare = cell.findViewById(R.id.tv_fare_content);
            viewHolder.dateTiem = cell.findViewById(R.id.content_delivery_date);
            viewHolder.promocode = cell.findViewById(R.id.content_deadline_badge);
            viewHolder.historyId=cell.findViewById(R.id.tv_history_id);
            viewHolder.distanceInshort = cell.findViewById(R.id.tv_distance_short);

            cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        if (null == item)
            return cell;

        // bind data from selected element to view through view holder
        viewHolder.historyId.setText("#"+item.getTransactionId());
        viewHolder.price.setText(item.getTotalFare());
        viewHolder.date.setText(item.getTime());
        viewHolder.fromAddress.setText(item.getPickPointAddress());
        viewHolder.toAddress.setText(item.getDestinationAddress());
        viewHolder.avaterName.setText(item.getRiderName());
        viewHolder.rating.setRating(item.getRating());
        viewHolder.distance.setText(item.getDistance());
        viewHolder.duration.setText(item.getDuration());
        viewHolder.fare.setText(item.getTotalFare());
        viewHolder.destinationAddress.setText(item.getDestinationAddress());
        viewHolder.sourceAddress.setText(item.getPickPointAddress());
        viewHolder.dateTiem.setText(item.getTime());
        viewHolder.distanceInshort.setText(item.getDistance());
        viewHolder.durationInshort.setText(item.getDuration());
        Picasso.with(getContext()).invalidate(item.getRiderAvatar());
        Picasso.with(getContext())
                .load(item.getRiderAvatar())
                .placeholder(R.drawable.profile_image)
                .error(R.drawable.profile_image)
                .into(viewHolder.driverImage);


        return cell;
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }


    // View lookup cache
    private static class ViewHolder {
        TextView price;
        TextView contentRequestBtn;
        TextView pledgePrice;
        TextView fromAddress;
        TextView toAddress;
        TextView requestsCount;
        TextView date;
        TextView time;
        TextView historyId;
        TextView duration;
        ImageView driverImage;
        RatingBar rating;
        TextView promocode;
        TextView avaterName;
        TextView sourceAddress;
        TextView destinationAddress;
        TextView distance;
        TextView fare;
        TextView dateTiem;
        TextView durationInshort;
        TextView distanceInshort;
        TextView fareCost;

    }
}
