package com.demoriderctg.arif.demorider.Dailog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.R;

import ContactWithFirebase.Main;

/**
 * Created by Arif on 1/21/2018.
 */

public class FareCostInfo  extends Dialog implements android.view.View.OnClickListener  {
    public Activity activity;
    public Button btnOk;
    private FragmentActivity myContext;


    public FareCostInfo(Activity activity) {
        super(activity);
        this.activity = activity;
        myContext = (FragmentActivity) activity;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fare_cost_info);
        btnOk = (Button) findViewById(R.id.btnOK);
        btnOk.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        dismiss();
    }

}
