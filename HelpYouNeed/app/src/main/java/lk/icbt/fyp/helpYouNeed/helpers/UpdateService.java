package lk.icbt.fyp.helpYouNeed.helpers;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class UpdateService extends Service {

    BroadcastReceiver mReceiver;
    public static int countOn = 0;
    public static int countOff = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        // register receiver that handles screen on and screen off logic
        Log.i("UpdateService", "Started");
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_ANSWER);
        mReceiver = new Receiver();
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onDestroy() {

        unregisterReceiver(mReceiver);
        Log.i("onDestroy Reciever", "Called");

        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        boolean screenOn = intent.getBooleanExtra("screen_state", false);
        if (!screenOn) {
            Log.i("screenON", "Called");
            Log.i("viaService", "CountOn =" + countOn);

            Toast.makeText(getApplicationContext(), "Awake", Toast.LENGTH_LONG)
                    .show();
        } else {
            Log.i("screenOFF", "Called");
            Log.i("viaService", "CountOff =" + countOff);
        }


        return START_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
}