package lk.icbt.fyp.helpYouNeed.ui.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

import lk.icbt.fyp.helpYouNeed.R;

public class UserBehaviourService extends Service {

    private static Timer timer;
    private static Timer timerSetData;

    private FirebaseDatabase database;
    private DatabaseReference sleepStatusRef;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;


    private Context context;

    //Create broadcast object
    BroadcastReceiver mybroadcast = new BroadcastReceiver() {
        //When Event is published, onReceive method is called
        @Override
        public void onReceive(final Context context, Intent intent) {
            // TODO Auto-generated method stub
            Log.i("[BroadcastReceiver]", "MyReceiver");

            if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                Log.i("[BroadcastReceiver]", "Screen ON");
            }
            else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                Log.i("[BroadcastReceiver]", "Screen OFF");
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        sendNotification(context);
                    }
                }, 15000);
                timerSetData = new Timer();
                timerSetData.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        setSleepStatus(Boolean.TRUE);
                        NotificationManager mNotificationManager
                                = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager.cancel(001);
                    }
                }, 30000);
            }
            else if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
                Log.i("[BroadcastReceiver]", "USER PRESENT");
                if(timer  != null){
                    timer.cancel();
                    setSleepStatus(Boolean.FALSE);
                }
            }

        }
    };

   public static class NotificationUserReplyReceiver extends BroadcastReceiver {

       private FirebaseDatabase database = FirebaseDatabase.getInstance();
       private FirebaseAuth mAuth = FirebaseAuth.getInstance();
       private FirebaseUser currentUser = mAuth.getCurrentUser();
       private DatabaseReference sleepStatusRef = database.getReference("sleepStatus");


       @Override
       public void onReceive(Context context, Intent intent) {
           Log.w("NOTR:", "Receieved!");
           timerSetData = new Timer();
           setSleepStatus(Boolean.FALSE);
           NotificationManager mNotificationManager
                   = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
           mNotificationManager.cancel(001);
       }

       public void setSleepStatus(Boolean sleepStatus) {
           sleepStatusRef.child(this.currentUser.getUid()).setValue(sleepStatus);
       }
   }

    public UserBehaviourService() {

        this.database = FirebaseDatabase.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
        this.currentUser = mAuth.getCurrentUser();
        this.sleepStatusRef = database.getReference("sleepStatus");

    }




    @Override
    public IBinder onBind(Intent intent) {
        registerReceiver(mybroadcast, new IntentFilter(Intent.ACTION_SCREEN_ON));
        registerReceiver(mybroadcast, new IntentFilter(Intent.ACTION_SCREEN_OFF));
        registerReceiver(mybroadcast, new IntentFilter(Intent.ACTION_USER_PRESENT));
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerReceiver(mybroadcast, new IntentFilter(Intent.ACTION_SCREEN_ON));
        registerReceiver(mybroadcast, new IntentFilter(Intent.ACTION_SCREEN_OFF));
        registerReceiver(mybroadcast, new IntentFilter(Intent.ACTION_USER_PRESENT));
        return START_STICKY;
    }

    public void setSleepStatus(Boolean sleepStatus) {
//       this.sleepStatusRef.child(this.currentUser.getUid()).setValue(sleepStatus);
    }

    public void sendNotification(Context context){

        Log.i("NOTIFICATION:","SENT");
        Intent mainIntent = new Intent(this, NotificationUserReplyReceiver.class);
        PendingIntent testIntent =PendingIntent.getBroadcast(this, 1, mainIntent, 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.user)
                        .addAction(R.drawable.cast_ic_expanded_controller_stop, "NO", testIntent)
                        .setContentTitle("FIN Sleep Verification")
                        .setContentText("Are you sleeping?");

        //Create the intent that’ll fire when the user taps the notification//


        NotificationManager mNotificationManager =

                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, mBuilder.build());


    }
}
