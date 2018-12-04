package com.giaitri24h.io.tangthuvien;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
public class ChapterActivity extends AppCompatActivity {
    static JSONArray jArray=new JSONArray();
    static Integer pre=0;
    static Integer next=0;
    Menu optionsMenu;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform whatever you want on back arrow click
                Intent mintent = new Intent(ChapterActivity.this, ListChapter.class);
                startActivity(mintent);
            }
        });
        try {

            String val=getIntent().getStringExtra("ID");


            String strJson=Helper.loadJSONFromAsset(getApplicationContext() );
            JSONObject json = new JSONObject(strJson);
            jArray = json.getJSONArray("LstChapter");
            getContent(val);
        }
        catch (Exception ex){}
        MobileAds.initialize(this,
                "ca-app-pub-4553269182745827/6191592465");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4553269182745827/6742256807");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }
    @Override
    public  boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menuitem,menu);
        optionsMenu = menu;
        return true;
    }
    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.preChapter:
                getContentNew(pre);
                break;
            case R.id.nexChapter:
                getContentNew(next);
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void getContent(String val){
        TextView t2 = (TextView) findViewById(R.id.chapterView);
        try{
            for (Integer i = 0; i < jArray.length(); i++) {
                JSONObject obj = jArray.getJSONObject(i);
                String id = obj.getString("ID");
                // look for the entry with a matching `code` value
                if (val.equals(id)) {
                    String desc = obj.getString("Description");
                    String name = obj.getString("Name");
                    Integer order = obj.getInt("Order");
                    pre = order - 1;
                    next = order + 1;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        t2.setText(Html.fromHtml("<h1>" + name + "</h1><br/>" + desc, Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        t2.setText(Html.fromHtml(obj.getString("Description")));
                    }
                    try {
                        FileOutputStream fOut = new FileOutputStream(new File(getFilesDir(), "ls_cache.txt"));
                        String str = id+"@@@"+name;
                        fOut.write(str.getBytes());
                        fOut.close();
                    }
                    catch (Exception e)
                    {

                    }
                    break;
                }
            }
        }catch (Exception ex){}


    }
    public void getContentNew(Integer order){
        TextView t2 = (TextView) findViewById(R.id.chapterView);
        try{
            for (Integer i = 0; i < jArray.length(); i++) {
                JSONObject obj = jArray.getJSONObject(i);
                Integer _order = obj.getInt("Order");
                // look for the entry with a matching `code` value
                if (_order==order) {
                    String id = obj.getString("ID");
                    String desc = obj.getString("Description");
                    String name = obj.getString("Name");
                    pre = _order - 1;
                    next = _order + 1;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        t2.setText(Html.fromHtml("<h1>" + name + "</h1><br/>" + desc, Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        t2.setText(Html.fromHtml(obj.getString("Description")));
                    }
                    try {
                        FileOutputStream fOut = new FileOutputStream(new File(getFilesDir(), "ls_cache.txt"));
                        String str = id+"@@@"+name;
                        fOut.write(str.getBytes());
                        fOut.close();
                    }
                    catch (Exception e)
                    {

                    }
                    String strPre = "";
                    if (obj != null) {
                        strPre = "Pre Chapter ||" ;
                        optionsMenu.getItem(0).setTitle(strPre);
                    }
                    String strNext = "";
                    if (obj != null) {
                        strNext = "Next Chapter";
                        optionsMenu.getItem(1).setTitle(strNext);
                    }

                    break;
                }
            }
        }catch (Exception ex){}


    }
}
