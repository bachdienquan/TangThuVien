package com.giaitri24h.io.tangthuvien;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    static Context myContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        String strdata=Helper.loadJSONFromAsset(getApplicationContext() );
        try{
            JSONObject obj = new JSONObject(strdata);
            TextView tv = (TextView)findViewById(R.id.titleBook); tv.setText(obj.getString("Name"));
        }catch (JSONException e){

        }
    }
}
