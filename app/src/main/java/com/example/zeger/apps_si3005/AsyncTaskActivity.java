package com.example.zeger.apps_si3005;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.style.LocaleSpan;
import android.util.Log;
import android.widget.ListView;

import com.example.zeger.apps_si3005.adapter.AdapterListView;
import com.example.zeger.apps_si3005.entity.Contact;
import com.example.zeger.apps_si3005.service.DownloadIntentService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zeger on 08/04/17.
 */

public class AsyncTaskActivity extends AppCompatActivity{

    private String url = "http://dreamfighter.id/android/data.json";
    private AdapterListView adapterListView;
    DownloadJsonReceiver receiver = new DownloadJsonReceiver();


    public class DownloadJsonReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String json = intent.getExtras().getString("json");
            adapterListView.refresh(decodeJson(json));
            Log.d("JSON_RECEIVER","Data json from Download service is receive");
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView)findViewById(R.id.contact_listview);

        adapterListView = new AdapterListView(this);

        listView.setAdapter(adapterListView);



        IntentFilter intentFilter = new IntentFilter("DOWNLOAD_JSON");

        registerReceiver(receiver,intentFilter);


        Intent intent = new Intent(this, DownloadIntentService.class);
        startService(intent);

        //DownloadJsonTask task = new DownloadJsonTask();
        //task.execute(url);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private class DownloadJsonTask extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            return requestJson(params[0]);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("JSON","S=" + s);
            adapterListView.refresh(decodeJson(s));
        }
    }

    public List<Contact> decodeJson(String json){
        List<Contact> contacts = new ArrayList<>();
        try {
            JSONObject jsonObjectRoot = new JSONObject(json);

            JSONArray jsonArrayData = jsonObjectRoot.getJSONArray("data");


            for (int i=0;i<jsonArrayData.length();i++){
                JSONObject jsonObjectContact = jsonArrayData.getJSONObject(i);

                Contact c = new Contact();
                c.setNama(jsonObjectContact.getString("name"));
                c.setNoHp(jsonObjectContact.getString("phone"));
                c.setAvatarUrl(jsonObjectContact.getString("avatarUrl"));

                contacts.add(c);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return contacts;
    }

    public String requestJson(String urlWeb){

        StringBuilder sb = new StringBuilder();
        try {

            // conect to server
            URL url = new URL(urlWeb);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // get data
            Reader r = new InputStreamReader(connection.getInputStream());

            char[] chars = new char[4*1024];
            int len;
            while((len = r.read(chars))>=0) {
                sb.append(chars, 0, len);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
