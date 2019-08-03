package lk.icbt.fyp.helpYouNeed.ui.services;
/*
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DeviceMovementService extends Service implements SensorEventListener{

    private static final String TAG = DeviceMovementService.class.getSimpleName();

    private SensorManager mSensorManager = null;

    private FirebaseDatabase database;
    private DatabaseReference sleepDataRef;
    private DatabaseReference sleepStatusRef;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private final ScheduledThreadPoolExecutor executor_ =
            new ScheduledThreadPoolExecutor(1);

    private Boolean isSleeping = false;
    private String sleepType = "Light Sleep";
    private long lStartTime = System.nanoTime();
    private String outputTime = "0 h 0 min";


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        database = FirebaseDatabase.getInstance();
        sleepDataRef = database.getReference("sleepData");
        sleepStatusRef = database.getReference("sleepStatus");

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(this, sensor,
                SensorManager.SENSOR_DELAY_NORMAL);

        checkSleepTypeChange();

        return START_STICKY;
    }

    public DeviceMovementService() {

        this.executor_.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                setSleepType();
            }
        }, 0L, 1, TimeUnit.MINUTES);

    }

    private void checkSleepTypeChange(){ sleepStatusRef.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //TODO: check if user is in sleep mode and set sleep type
//                isSleeping = Boolean.parseBoolean(dataSnapshot.getValue().toString());
              /*  if(!isSleeping){
                    long endTime = System.nanoTime();
                    long output = endTime - lStartTime;
                    outputTime = Math.floor(TimeUnit.HOURS.convert(output, TimeUnit.NANOSECONDS)) + " h "
                            + Math.floor(TimeUnit.MINUTES.convert(output, TimeUnit.NANOSECONDS)) + " m";
                    saveData();
                }else{
                    lStartTime = System.nanoTime();
                }*/
//                Log.w("SLEEP STATUS CHANGED: ", dataSnapshot.getValue().toString());
            /*}

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private double gravity[] = new double[3];
    private double linear_acceleration[] = new double[3];

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        final double alpha = 0.8;

        // Isolate the force of gravity with the low-pass filter.
        gravity[0] = alpha * gravity[0] + (1 - alpha) * sensorEvent.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * sensorEvent.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * sensorEvent.values[2];

        // Remove the gravity contribution with the high-pass filter.
        linear_acceleration[0] = sensorEvent.values[0] - gravity[0];
        linear_acceleration[1] = sensorEvent.values[1] - gravity[1];
        linear_acceleration[2] = sensorEvent.values[2] - gravity[2];

        setSleepType();

//        StringBuilder sb = new StringBuilder();
//        for (double value : linear_acceleration)
//            sb.append(String.valueOf(value)).append(" | ");
//
//        Log.d(TAG, "received sensor values are: " + sb.toString());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void setSleepType(){
        if(Math.abs(linear_acceleration[0])<0.1 || Math.abs(linear_acceleration[1])<0.1 || Math.abs(linear_acceleration[2])<0.1){
            if(!this.sleepType.equals("Light Sleep")){
                this.sleepType = "Deep Sleep";
            }
        }else if(Math.abs(linear_acceleration[0])>1 || Math.abs(linear_acceleration[1])>1 || Math.abs(linear_acceleration[2])>1){
            this.sleepType = "Light Sleep";
        }
    }

    private void saveData(){
        HashMap sleepData = new HashMap();
        sleepData.put("sleepType", this.sleepType);
        sleepData.put("sleepTime", this.outputTime);
        sleepDataRef.child(this.currentUser.getUid()).setValue(sleepData);
    }
}
*/