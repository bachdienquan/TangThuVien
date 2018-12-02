package com.giaitri24h.io.tangthuvien;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.AsyncTask;

import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
public class ListChapter extends AppCompatActivity {
static JSONArray jArray=new JSONArray();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chapter);
        TextView t2 = (TextView) findViewById(R.id.chapterReading);
        t2.setMovementMethod(LinkMovementMethod.getInstance());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform whatever you want on back arrow click
                Intent mintent = new Intent(ListChapter.this, MainActivity.class);
                startActivity(mintent);
            }
        });
        //list view
        String[] from = {"Name"};
        int[] to = {R.id.txtViewItem};
        HashMap<String, String> hashmap;
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
        final ListView lView = (ListView) findViewById(R.id.lvMain);
        try{
            String strJson=Helper.loadJSONFromAsset(getApplicationContext() );
            JSONObject json = new JSONObject(strJson);
            jArray = json.getJSONArray("LstChapter");

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject friend = jArray.getJSONObject(i);

                String nameOS = friend.getString("Name");
                Log.d("FOR_LOG", nameOS);

                hashmap = new HashMap<String, String>();
                hashmap.put("Name", "" + nameOS);
                arrayList.add(hashmap);
            }

            final SimpleAdapter adapter = new SimpleAdapter(ListChapter.this, arrayList, R.layout.item, from, to);
            lView.setAdapter(adapter);
            lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try{
                        JSONObject obj=jArray.getJSONObject(position);
                        String val = obj.getString("ID");
                        Intent intent=new Intent(ListChapter.this,ChapterActivity.class);
                        intent.putExtra("ID",val);
                        startActivity(intent);
                    }catch (Exception ex){}
                }
            });
        }catch (Exception ex){}

    }



}
