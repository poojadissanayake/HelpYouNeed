package lk.icbt.fyp.helpYouNeed.ui.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Context context;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        getActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        setUserDetails(mUser);
        getSleepData();

        this.context = getApplicationContext();

        sharedPreferences = context.getSharedPreferences(
                getString(R.string.shared_preferences_file_name), Context.MODE_PRIVATE);

    }

    private void setUserDetails(final FirebaseUser mUser){
        final TextView profileNameTxt = findViewById(R.id.profile_name);
        final TextView profileEmailTxt = findViewById(R.id.profile_type);
        final TextView profileFirstNameTxt = findViewById(R.id.profile_firstname);
        final TextView profileLastNameTxt = findViewById(R.id.profile_lastname);
        final TextView profileUniversityTxt = findViewById(R.id.profile_university);
        final TextView profileDOBTxt = findViewById(R.id.profile_dob);
        final TextView bestfrendnumber = findViewById(R.id.frenphone);
        final TextView profileGenderTxt = findViewById(R.id.profile_gender);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");
        Query query = ref.child(mUser.getUid());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                profileDOBTxt.setText(user.getDob());
                profileEmailTxt.setText(mUser.getEmail());
                profileFirstNameTxt.setText(user.getFirstName());
                profileGenderTxt.setText(user.getGender());
                profileLastNameTxt.setText(user.getLastName());
                profileNameTxt.setText(mUser.getDisplayName());
                profileUniversityTxt.setText(user.getUniversity());
                bestfrendnumber.setText(user.getBfNum());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getSleepData(){
        String sleepType = sharedPreferences.getString("sleepMode", null);
        final TextView sleepTypeTxt = findViewById(R.id.sleepTypeTxt);
        final TextView sleepTimeTxt = findViewById(R.id.lastSleepTimeTxt);

        if(sleepType != null){
            sleepTypeTxt.setText(sleepType);
        }else{
            sleepTypeTxt.setText("No Records");
        }
    }
}
