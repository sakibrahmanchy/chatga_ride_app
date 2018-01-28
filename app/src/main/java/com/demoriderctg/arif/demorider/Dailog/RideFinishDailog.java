package com.demoriderctg.arif.demorider.Dailog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.MainActivity;
import com.demoriderctg.arif.demorider.R;

import ContactWithFirebase.Main;

/**
 * Created by Arif on 1/17/2018.
 */

public class RideFinishDailog extends Dialog implements View.OnClickListener {
    public Activity activity;
    public Button btnOk;
    private FragmentActivity myContext;
    private Main main;
    private TextView total_cost;


    public RideFinishDailog(Activity activity) {
        super(activity);
        this.activity = activity;
        myContext = (FragmentActivity) activity;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ride_finish_dailog);
        btnOk = (Button) findViewById(R.id.btnOk);
        total_cost = (TextView) findViewById(R.id.total_cost);
        btnOk.setOnClickListener(this);
        main = new Main();
        total_cost.setText(AppConstant.FINAL_RIDE_COST+" TK");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOk:
                Intent intent = new Intent(getContext(),MainActivity.class);
                getContext().startActivity(intent);
                myContext.finish();
                break;
            default:
                break;
        }
        dismiss();
    }


}