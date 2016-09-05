package com.ryg.chapter_5;

import com.ryg.chapter_5.R;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;

public class TestActivity extends Activity implements OnClickListener {

    private static final String TAG = "TestActivity";

    private Button mButton1;
    private View mButton2;

    private static int sId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        mButton1 = (Button) findViewById(R.id.button1);
        mButton1.setOnClickListener(this);
        mButton2 = (TextView) findViewById(R.id.button2);
        mButton2.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressWarnings("deprecation")
    @Override
    public void onClick(View v) {
        if (v == mButton1) {
            sId ++;
            //声明一个新的Notification，在里面设置参数
            Notification notification = new Notification();
            notification.icon = R.drawable.ic_launcher;
            notification.tickerText = "hello world";
            notification.when = System.currentTimeMillis();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            Intent intent = new Intent(this, DemoActivity_2.class);
            //PendingIntent是延时Intent是指当点击通知栏之后弹出intent指向activity
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            //这里是指给予通知栏一个标准布局
            notification.setLatestEventInfo(this, "chapter_5", "this is notification.", pendingIntent);
            //声明一个NotificationManager
            NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            //根据ID发送
            manager.notify(sId, notification);
        } else if (v == mButton2) {
            sId ++;
            //声明一个新的Notification，在里面设置参数
            Notification notification = new Notification();
            notification.icon = R.drawable.ic_launcher;
            notification.tickerText = "hello world";
            notification.when = System.currentTimeMillis();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            Intent intent = new Intent(this, DemoActivity_1.class);
            intent.putExtra("sid", "" + sId);
          //PendingIntent是延时Intent是指当点击通知栏之后启动intent指向activity
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            System.out.println(pendingIntent);
            //调用remoteView去布局一个自定义Notification
            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
            remoteViews.setTextViewText(R.id.msg, "chapter_5: " + sId);
            remoteViews.setImageViewResource(R.id.icon, R.drawable.icon1);
          //PendingIntent是延时Intent是指当点击通知栏之后启动intent指向activity
            PendingIntent openActivity2PendingIntent = PendingIntent.getActivity(this,
                    0, new Intent(this, DemoActivity_2.class), PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.open_activity2, openActivity2PendingIntent);
            //设置contentview
            notification.contentView = remoteViews;
            //设置pendingItent
            notification.contentIntent = pendingIntent;
            NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(sId, notification);
        }
    }

}
