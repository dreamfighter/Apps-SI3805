package com.example.zeger.apps_si3005.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.zeger.apps_si3005.R;
import com.example.zeger.apps_si3005.entity.Contact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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

public class DownloadIntentService extends IntentService{
    private String url = "http://dreamfighter.id/android/data.json";

    public DownloadIntentService(){
        super("DownloadIntentService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String dataJson = requestJson(url);

        Intent intentBroadcast = new Intent("DOWNLOAD_JSON");
        Bundle bundle = new Bundle();

        bundle.putString("json",dataJson);

        intentBroadcast.putExtras(bundle);
        //intentBroadcast.addCategory("DOWNLOAD_JSON");

        sendBroadcast(intentBroadcast);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("sample notif")
                .setContentText("ini bady notif")
                .setAutoCancel(true);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        Logger.log("notify send!");
        notificationManager.notify(89, notificationBuilder.build());
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
        Log.d("JSON","S=" + sb.toString());
        return sb.toString();
    }
}
