package com.demoriderctg.arif.demorider.Referral;

import android.content.ClipboardManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.demoriderctg.arif.demorider.R;
import com.demoriderctg.arif.demorider.UserInformation;
import com.demoriderctg.arif.demorider.models.ApiModels.User;

public class Refarrel extends AppCompatActivity {

    private UserInformation userInformation;
    private TextView referral,copy ;
    private Button invitebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refarrel);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userInformation = new UserInformation(this);
        referral = findViewById(R.id.referral_view);
        copy = findViewById(R.id.copy);
        invitebtn = findViewById(R.id.invite_btn);


        referral.setText(userInformation.getuserInformation().getReferralCode());
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringYouExtracted = referral.getText().toString();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                clipboard.setText(stringYouExtracted);
                Toast.makeText(getApplicationContext(),"copied",Toast.LENGTH_SHORT).show();
            }
        });

        invitebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "User following referral code for get discount\n"+userInformation.getuserInformation().getReferralCode());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;

    }
}
