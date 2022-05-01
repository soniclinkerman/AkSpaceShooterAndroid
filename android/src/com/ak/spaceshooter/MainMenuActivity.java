package com.ak.spaceshooter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.ak.spaceshooter.db.Level;
import com.ak.spaceshooter.db.LevelDatabase;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.os.Build;

import java.util.Calendar;
import java.util.List;

public class MainMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        final Button playButton = (Button) findViewById(R.id.data_upload);
        final Button settingsButton = (Button) findViewById(R.id.settings);
        final Button leaderboard = findViewById(R.id.leaderboard);
        playButton.setOnClickListener(this::onClickPlay);
        settingsButton.setOnClickListener(this::onClickSettings);
        leaderboard.setOnClickListener(this::onClickLeaderBoard);

        //NOTIFICATION CODE
        NotificationChannel();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 17);
        calendar.set(Calendar.SECOND, 0);

        if (Calendar.getInstance().after(calendar)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        Intent intent = new Intent(MainMenuActivity.this, MemoBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        //alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis()+10_000,pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),pendingIntent);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),1000*60*60,pendingIntent);
        //alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);



    }
    public void onClickPlay(View v){
        LevelDatabase levelDatabase = LevelDatabase.getDatabase(this);
        LiveData<List<Level>> levels = levelDatabase.levelDAO().getAll();
        startActivity(new Intent(this, LevelSelectActivity.class));

    }
    public void onClickSettings(View v){
        startActivity(new Intent(this, SettingsActivity.class));

    }
    public void onClickLeaderBoard(View v){
        startActivity(new Intent(this, LeaderboardActivity.class));

    }

    //NOTIFICATION CODE
    private void NotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Space Shooter";
            String description = "Space Shooter Game";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Notification", name, importance);
            channel.setDescription(description);


            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


        }
    }
    //
    //sanket was here
}
