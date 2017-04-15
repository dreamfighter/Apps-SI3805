package com.example.zeger.apps_si3005.receiver;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.example.zeger.apps_si3005.R;

/**
 * Created by zeger on 15/04/17.
 */

public class GcmReceiver extends WakefulBroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("GcmReceiver","Receive data from server");
        Log.d("GcmReceiver","data="+intent.getExtras().get("message"));
        Log.d("GcmReceiver","userId="+intent.getExtras().get("userId"));

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)

                .setContentTitle(intent.getExtras().getString("userId"))
                .setContentText(intent.getExtras().getString("message"))
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Andi Lala (34), tersangka utama pembunuhan sadis satu keluarga di Medan, ditangkap di Indrigiri Hilir, Riau. Meski sudah dikepung, ia sempat melawan."));

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        Logger.log("notify send!");
        notificationManager.notify(89, notificationBuilder.build());
    }
}
