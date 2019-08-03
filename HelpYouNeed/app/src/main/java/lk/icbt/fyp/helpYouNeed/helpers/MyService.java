package lk.icbt.fyp.helpYouNeed.helpers;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat.Builder;
import android.telephony.SmsManager;
import android.util.Log;

import lk.icbt.fyp.helpYouNeed.R;
import lk.icbt.fyp.helpYouNeed.ui.MainActivity;

public class MyService extends Service {
    Context context;
    private int count = 0;
    private int counttime = 0;
    Handler handler;
    private boolean isOn = false;


    Runnable runnable = new Runnable() {
        public void run() {
            System.out.println("Service running : " + MyService.this.isScreenOn(MyService.this.context) + " count :" + MyService.this.count);
            MyService.this.counttime = MyService.this.counttime + 1;
            if (MyService.this.counttime > 60) {
                MyService.this.counttime = 0;
                MyService.this.isOn = MyService.this.isScreenOn(MyService.this.context);
                MyService.this.count = 0;
            } else if (MyService.this.count >= 6) {
                MyService.this.sendNotification("HelpYouNeed has monitored unusual screen on/off patterns.", String.valueOf(MyService.this.count = MyService.this.count + 1));
                MyService.this.counttime = 0;
                MyService.this.isOn = MyService.this.isScreenOn(MyService.this.context);
                MyService.this.count = 0;
            } else if (MyService.this.isScreenOn(MyService.this.context) != MyService.this.isOn) {
                MyService.this.isOn = MyService.this.isScreenOn(MyService.this.context);
                MyService.this.count = MyService.this.count + 1;
            }
            MyService.this.handler.postDelayed(MyService.this.runnable, 1000);
        }
    };

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        this.handler = new Handler();
        this.context = this;
        System.out.println("Service created");
        this.isOn = isScreenOn(this.context);
        this.handler.post(this.runnable);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }


    public boolean isScreenOn(Context context) {
        PowerManager pm = (PowerManager) getSystemService(context.POWER_SERVICE);
        boolean screenOn = pm.isInteractive();
        if (screenOn == true) {
            Log.d("Screen", "Screen is ON");
            return true;
        } else
            Log.d("Screen", "Screen is OFF");
        return false;
    }


    private void sendNotification(String messageBody, String count) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("fromNotification", count);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Intent mainIntent = new Intent(this, MainActivity.class);
//        Intent intentConfirm = new Intent(this, NotificationActionReceiver.class);
//        intentConfirm.setAction("Yes");
//        intentConfirm.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        Intent intentCancel = new Intent(this, NotificationActionReceiver.class);
//        intentCancel.setAction("No");
//        intentCancel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, MODE_PRIVATE, intent, PendingIntent.FLAG_ONE_SHOT);
//        PendingIntent pendingIntentConfirm = PendingIntent.getBroadcast(this, 0, intentConfirm, PendingIntent.FLAG_CANCEL_CURRENT);
//        PendingIntent pendingIntentCancel = PendingIntent.getBroadcast(this, 1, intentCancel, PendingIntent.FLAG_CANCEL_CURRENT);
        Builder notificationBuilder = new Builder(this)
                .setSmallIcon(R.drawable.head)
                .addAction(R.drawable.online_icon,"YES",pendingIntent)
                .setContentTitle("Are you stressed?")
                .setContentText("" + messageBody)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(BIND_DEBUG_UNBIND))
                .setContentIntent(pendingIntent);
//        notificationBuilder.addAction(R.drawable.ic_launcher_background, "Yes", pendingIntentConfirm);
//        notificationBuilder.addAction(R.drawable.ic_launcher_background, "No", pendingIntentCancel);
        ((NotificationManager) getSystemService(context.NOTIFICATION_SERVICE)).notify(11111, notificationBuilder.build());
        /*Intent map = new Intent(MyService.this,ListOnline.class);
        startActivity(map);*/

        //sendSMS("0752934929", "I'm in stress. Please call me!");

    }
    private void sendSMS(String s, String s1) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(s, null, s1, null, null);
    }
}

