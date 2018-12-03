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

public class ChapterActivity extends AppCompatActivity {
    static JSONArray jArray=new JSONArray();
    static Integer pre=0;
    static Integer next=0;
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
    }
    @Override
    public  boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menuitem,menu);
        return true;
    }
    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.preChapter:
                getContent(pre+"");
                break;
            case R.id.nexChapter:
                getContent(next+"");
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
                String _order = obj.getString("Order");
                // look for the entry with a matching `code` value
                if (_order.equals(order)) {
                    String id = obj.getString("ID");
                    String desc = obj.getString("Description");
                    String name = obj.getString("Name");
                    pre = order - 1;
                    next = order + 1;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        t2.setText(Html.fromHtml("<h1>" + name + "</h1><br/>" + desc, Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        t2.setText(Html.fromHtml(obj.getString("Description")));
                    }
                    String strPre = "";
                    if (obj != null) {
                        strPre = "Pre Chapter ||" ;
                        MenuItem  menu1 = (MenuItem ) findViewById(R.id.preChapter);
                        menu1.setTitle(strPre);
                    }
                    String strNext = "";
                    if (obj != null) {
                        strNext = "Next Chapter";
                        MenuItem  menu1 = (MenuItem ) findViewById(R.id.nexChapter);
                        menu1.setTitle(strNext);
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
}
