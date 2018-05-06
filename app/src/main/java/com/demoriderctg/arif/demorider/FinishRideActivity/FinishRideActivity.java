package com.demoriderctg.arif.demorider.FinishRideActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;


import com.demoriderctg.arif.demorider.ActiveContext;
import com.demoriderctg.arif.demorider.AppConfig.AppConstant;
import com.demoriderctg.arif.demorider.ClearData.ClearData;
import com.demoriderctg.arif.demorider.MainActivity;
import com.demoriderctg.arif.demorider.R;
import com.demoriderctg.arif.demorider.Rating.UserRating;

import ContactWithFirebase.Main;

public class FinishRideActivity extends AppCompatActivity implements View.OnClickListener {

    public Button btnOk;
    private RatingBar ratingBar;
    private Main main;
    private TextView total_cost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_ride);
        btnOk = (Button) findViewById(R.id.btnOk);
        total_cost = (TextView) findViewById(R.id.total_cost);
        btnOk.setOnClickListener(this);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        main = new Main();
        new ActiveContext(this);
        total_cost.setText("৳"+ AppConstant.TOTAL_COST);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                AppConstant.RATING = ratingBar.getRating();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOk:
                if(AppConstant.RATING !=0){
                    new UserRating(this).RatingDriver();
                }
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                new ClearData();
                finish();
                break;
            default:
                break;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }
}
