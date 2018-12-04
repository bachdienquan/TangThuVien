package com.giaitri24h.io.tangthuvien;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
public class MainActivity extends AppCompatActivity {
    static Context myContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, "ca-app-pub-4553269182745827/6191592465");
        AdView adView = new AdView(this);
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        try{
            String strdata=Helper.loadJSONFromAsset(getApplicationContext() );
            JSONObject obj = new JSONObject(strdata);
            TextView tv = (TextView)findViewById(R.id.titleBook);
            tv.setText(obj.getString("Name"));
            TextView descriptBook = (TextView)findViewById(R.id.descriptBook);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                descriptBook.setText(Html.fromHtml(obj.getString("Description"), Html.FROM_HTML_MODE_COMPACT));
            } else {
                descriptBook.setText(Html.fromHtml(obj.getString("Description")));
            }
            Button btnChapter2 = (Button)findViewById(R.id.buttonChapter2);
        }catch (JSONException e){

        }
    }
    public void buttonClick(View view){
        Intent mintent = new Intent(MainActivity.this, ListChapter.class);
        startActivity(mintent);
    }
}
