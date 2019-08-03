package lk.icbt.fyp.helpYouNeed.FriendLocation;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

import lk.icbt.fyp.helpYouNeed.R;

public class MapTracking extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private String email;

    DatabaseReference locations;

    Double lat,lng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_tracking);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //ref to firebase
        locations= FirebaseDatabase.getInstance().getReference("Locations");

        //get Intent
        if(getIntent() != null)
        {
            email = getIntent().getStringExtra("email");
            lat = getIntent().getDoubleExtra("lat",0);
            lng = getIntent().getDoubleExtra("lng",0);
        }
        if(!TextUtils.isEmpty(email))
            loadLocationForThisUser(email);
    }

    private void loadLocationForThisUser(final String email) {
        Query user_location = locations.orderByChild("email").equalTo(email);

        //sendSMS("0752934929", "Hi You got a message!");




        user_location.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapShot:dataSnapshot.getChildren())
                {
                    Tracking tracking = postSnapShot.getValue(Tracking.class);


                    LatLng friendLocation = new LatLng(Double.parseDouble(tracking.getLat()),
                            Double.parseDouble(tracking.getLng()));

                    //create location from user coordinates
                    Location currentUser = new Location("");
                    currentUser.setLatitude(lat);
                    currentUser.setLongitude(lng);

                    //create location from friend coordinates
                    Location friend = new Location("");
                    friend.setLatitude(Double.parseDouble(tracking.getLat()));
                    friend.setLongitude(Double.parseDouble(tracking.getLng()));


                    //clear all old markers;
                    mMap.clear();

                    //add marker to friend
                    mMap.addMarker(new MarkerOptions()
                            .position(friendLocation)
                            .title(tracking.getEMail())
                            .snippet("Distance " + new DecimalFormat("#.#").format(currentUser.distanceTo(friend)))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),12.0f));


                }//(currentUser.distanceTo(friend))/1000)+ "km"

                //create marker to user
                LatLng current = new LatLng(lat,lng);
                mMap.addMarker(new MarkerOptions().position(current).title(FirebaseAuth.getInstance().getCurrentUser().getEmail()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void sendSMS(String s, String s1) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(s, null, s1, null, null);
    }

    private double distance(Location currentUser,Location friend){
        double theta = currentUser.getLongitude() - friend.getLongitude();
        double dist = Math.sin(deg2red(currentUser.getLatitude()))
                * Math.sin(deg2red(friend.getLatitude()))
                * Math.cos(deg2red(currentUser.getLatitude()))
                * Math.cos(deg2red(friend.getLatitude()))
                * Math.cos(deg2red(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        return (dist);


    }


    private double rad2deg(double red) {
        return (red * 180 / Math.PI);
    }

    private double deg2red(double deg) {
        return (deg * Math.PI / 180.0);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


    }
}

///////////////////////////////////////////////////////////////////////////////////////////////////////

