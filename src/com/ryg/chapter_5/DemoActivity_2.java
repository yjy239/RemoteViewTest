package com.ryg.chapter_5;

import com.ryg.chapter_5.R;
import com.ryg.chapter_5.utils.MyConstants;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

public class DemoActivity_2 extends Activity {
    private static final String TAG = "DemoActivity_2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_2);
        Log.d(TAG, "onCreate");
        Toast.makeText(this, getIntent().getStringExtra("sid"),
                Toast.LENGTH_SHORT).show();
        initView();
    }

    private void initView() {
    }

    public void onButtonClick(View v) {
    	//加载RemoteView布局文件
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_simulated_notification);
        //加载资源文件
        remoteViews.setTextViewText(R.id.msg, "msg from process:" + Process.myPid());
        remoteViews.setImageViewResource(R.id.icon, R.drawable.icon1);
        //声明pendingintent是启动activity
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, new Intent(this, DemoActivity_1.class), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent openActivity2PendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, DemoActivity_2.class), PendingIntent.FLAG_UPDATE_CURRENT);
        //给控件绑定pendingIntent
        remoteViews.setOnClickPendingIntent(R.id.item_holder, pendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.open_activity2, openActivity2PendingIntent);
        Intent intent = new Intent(MyConstants.REMOTE_ACTION);
        //发送了remoteView，由于remoteview实现了Parcelable接口因此可以传送
        intent.putExtra(MyConstants.EXTRA_REMOTE_VIEWS, remoteViews);
        //发送广播
        sendBroadcast(intent);
    }

}
