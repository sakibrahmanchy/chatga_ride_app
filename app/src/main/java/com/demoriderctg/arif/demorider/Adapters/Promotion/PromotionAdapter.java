package com.demoriderctg.arif.demorider.Adapters.Promotion;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.demoriderctg.arif.demorider.R;
import com.demoriderctg.arif.demorider.models.ApiModels.UserDiscounts.UserDiscounts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SakibRahman on 1/22/2018.
 */

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.ViewHolder> {

    private Context context;
    private ArrayList<UserDiscounts> UserDiscounts;

    @Override
    public PromotionAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.discount_row_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PromotionAdapter.ViewHolder viewHolder, int position) {

        UserDiscounts Promotion = UserDiscounts.get(position);
        viewHolder.promotion_code.setText(Promotion.getPromotionCode());
        viewHolder.promo_code_descriptions.setText(Promotion.getPromoCodeDescriptions());
        viewHolder.end_time.setText(Promotion.getEndTime());
        viewHolder.ride_type.setText("BIKE");
    }

    public PromotionAdapter(Context mContext, ArrayList<UserDiscounts> Promotion) {
        this.context = mContext;
        this.UserDiscounts = Promotion;
    }

    @Override
    public int getItemCount() {
        return UserDiscounts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView promotion_code;
        private TextView promo_code_descriptions;
        private TextView end_time;
        private TextView ride_type;

        public ViewHolder(View view) {
            super(view);

            promotion_code = (TextView) view.findViewById(R.id.promotion_code);
            promo_code_descriptions = (TextView) view.findViewById(R.id.promo_code_descriptions);
            end_time = (TextView) view.findViewById(R.id.end_time);
            ride_type = (TextView) view.findViewById(R.id.ride_type);
        }
    }

}
