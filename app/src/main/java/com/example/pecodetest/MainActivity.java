package com.example.pecodetest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.pecodetest.Adapter.FragmentAdapter;

public class MainActivity extends AppCompatActivity {

    NotificationManager notificationManager;

    public ViewPager viewPager;
    FragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onNewIntent(getIntent());
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());

        viewPager.setAdapter(fragmentAdapter);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }


    public void addFragment(Fragment fragment) {
        fragmentAdapter.addFragment(fragment);
        viewPager.setCurrentItem(fragmentAdapter.getCount() - 1);
    }

    public int getNextSectionNumber() {
        return fragmentAdapter.getCount() + 1;
    }

    public void removeFragment(int id) {
        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        fragmentAdapter.removeFragment(id);
        cancelNotification(id + 1);
        Toast.makeText(this, String.valueOf(fragmentAdapter.getCount()), Toast.LENGTH_SHORT).show();

    }

    public int getCurentIndex() {
        return fragmentAdapter.getCount();
    }


    public void showNotification(int index) {
       /* Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("KEY", index);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);*/
        notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = String.valueOf(index);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_MAX);

            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);

        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder//.setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("You create a notification")
                .setContentText("Notification " + index)
                .setContentInfo("Info");

        notificationManager.notify(index, notificationBuilder.build());
    }

    public void cancelNotification(int index) {
        notificationManager.cancel(index);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if(extras != null){
            if(extras.containsKey("KEY"))
            {
                    viewPager.setCurrentItem(extras.getInt("KEY"));
            }
        }
    }


}
