package lk.icbt.fyp.helpYouNeed.ui.auth;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import lk.icbt.fyp.helpYouNeed.R;
import lk.icbt.fyp.helpYouNeed.models.User;

public class UserProfile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Context context;

    private FirebaseDatabase database;
    private DatabaseReference sleepDataRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        sleepDataRef = database.getReference("sleepData");

        setUserDetails(currentUser);
        getSleepData();

        this.context = getApplicationContext();

    }


    private void setUserDetails(final FirebaseUser mUser){
        final TextView profileNameTxt = findViewById(R.id.user_profile_name);
        final TextView profileEmailTxt = findViewById(R.id.user_profile_email);
//        final TextView profileFirstNameTxt = findViewById(R.id.profile_firstname);
//        final TextView profileLastNameTxt = findViewById(R.id.profile_lastname);
        final TextView profileUniversityTxt = findViewById(R.id.user_profile_university);
        final TextView profileDOBTxt = findViewById(R.id.user_profile_dob);
        final TextView profileGenderTxt = findViewById(R.id.user_profile_gender);


        profileDOBTxt.setText("1992-08-12");
        profileEmailTxt.setText("supun_wijesundara@hotmail.com");
        profileNameTxt.setText("Supun Dee");
//        profileFirstNameTxt.setText("Supun");
//        profileLastNameTxt.setText("Wijesundara");
        profileUniversityTxt.setText("ICBT");
        profileGenderTxt.setText("Male");


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");
        Query query = ref.child(mUser.getUid());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                profileDOBTxt.setText(user.getDob());
                profileEmailTxt.setText(mUser.getEmail());
//                profileFirstNameTxt.setText(user.getFirstName());
                profileGenderTxt.setText(user.getGender());
//                profileLastNameTxt.setText(user.getLastName());
                profileNameTxt.setText(mUser.getDisplayName());
                profileUniversityTxt.setText(user.getUniversity());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void getSleepData(){

        Log.w("SLEEP: ", "GET SLEEP DATA CALLED!");

        final TextView sleepTime = findViewById(R.id.lastSleepTimeTxt);
        final TextView sleepType = findViewById(R.id.sleepTypeTxt);

        Query sleepDataQuery = sleepDataRef.orderByChild(currentUser.getUid());

        sleepDataQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.w("SLEEP DATA: ", dataSnapshot
                        .child(currentUser.getUid())
                        .getValue()
                        .toString());

                sleepTime.setText(dataSnapshot.child(currentUser.getUid()).child("sleepTime").getValue().toString());
                sleepType.setText(dataSnapshot.child(currentUser.getUid()).child("sleepType").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        database.goOffline();
    }


}
