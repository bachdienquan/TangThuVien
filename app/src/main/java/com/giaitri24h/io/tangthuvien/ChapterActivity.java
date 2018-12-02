package com.giaitri24h.io.tangthuvien;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

public class ChapterActivity extends AppCompatActivity {
    static JSONArray jArray=new JSONArray();
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

            TextView t2 = (TextView) findViewById(R.id.chapterView);

            String strJson=Helper.loadJSONFromAsset(getApplicationContext() );
            JSONObject json = new JSONObject(strJson);
            jArray = json.getJSONArray("LstChapter");
            for (Integer i = 0; i < jArray.length(); i++) {
                JSONObject obj = jArray.getJSONObject(i);
                String id = obj.getString("ID");
                // look for the entry with a matching `code` value
                if (val.equals(id)) {
                    String desc = obj.getString("Description");
                    String name = obj.getString("Name");
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
        }
        catch (Exception ex){}
    }

}
