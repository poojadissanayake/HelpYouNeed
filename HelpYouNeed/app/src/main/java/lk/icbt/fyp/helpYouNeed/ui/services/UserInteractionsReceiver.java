package lk.icbt.fyp.helpYouNeed.ui.services;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

import lk.icbt.fyp.helpYouNeed.R;
import lk.icbt.fyp.helpYouNeed.models.User;

public class UserInteractionsReceiver extends BroadcastReceiver {

    private static final String KEY_TEXT_REPLY = "key_text_reply";
    private static final Timer timer = new Timer();



    @Override
    public void onReceive(final Context context, Intent intent) {

        String action = intent.getAction();
        Log.i("ACTION:","RECEIVED!");

        assert action != null;
        if(action.equalsIgnoreCase(Intent.ACTION_USER_PRESENT)){
            Log.i("ACTION:","USER PRESENT");
            timer.cancel();
        }else if(action.equalsIgnoreCase(Intent.ACTION_SCREEN_OFF)){
            Log.i("ACTION:", "SCREEN OFF");
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    sendNotification(context);
                }
            }, 5000);
        } else if(action.equalsIgnoreCase(Intent.ACTION_SCREEN_ON)){
            Log.i("ACTION:", "SCREEN ON");
        }



    }

    public void sendNotification(Context context){

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.user)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");

        //Create the intent that’ll fire when the user taps the notification//


        NotificationManager mNotificationManager =

                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, mBuilder.build());


    }

}
