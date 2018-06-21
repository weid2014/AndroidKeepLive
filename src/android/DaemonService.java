package com.cesc.ewater.cordovaPlugin;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;


/**
 * Created by 18603 on 2018/6/1.
 */

public class DaemonService extends Service {
    private static final String TAG = "weid";
    public static final int NOTICE_ID = 100;
    public static final boolean DEBUG = true;

    public static final String PACKAGE_NAME = "com.jiangdg.keepappalive";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
            Log.d(TAG,"DaemonService---->onCreate被调用，启动前台service");
        //如果API大于18，需要弹出一个可见通知
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            Notification.Builder builder = new Notification.Builder(this);
            builder.setContentTitle("KeepAppAlive");
            builder.setContentText("DaemonService is runing...");
            startForeground(NOTICE_ID,builder.build());
            // 如果觉得常驻通知栏体验不好
            // 可以通过启动CancelNoticeService，将通知移除，oom_adj值不变
            Intent intent = new Intent(this,CancelNoticeService.class);
            startService(intent);
        }else{
            startForeground(NOTICE_ID,new Notification());
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 如果Service被终止
        // 当资源允许情况下，重启service
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // 如果Service被杀死，干掉通知
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            NotificationManager mManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            mManager.cancel(NOTICE_ID);
        }
            Log.d(TAG,"DaemonService---->onDestroy，前台service被杀死");
        // 重启自己
        Intent intent = new Intent(getApplicationContext(),DaemonService.class);
        startService(intent);
    }
}
