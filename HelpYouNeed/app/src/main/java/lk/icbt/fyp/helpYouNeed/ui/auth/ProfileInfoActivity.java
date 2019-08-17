package lk.icbt.fyp.helpYouNeed.ui.auth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import lk.icbt.fyp.helpYouNeed.R;
import lk.icbt.fyp.helpYouNeed.models.User;
import lk.icbt.fyp.helpYouNeed.ui.MainActivity;

public class ProfileInfoActivity extends AppCompatActivity {

    private static final String TAG = ProfileInfoActivity.class.getSimpleName()+" : ";

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

    }

    public void createAccount(View view) {

        TextView firstNameTxt = findViewById(R.id.firstName);
        TextView lastNameTxt = findViewById(R.id.lastName);
        TextView universityTxt = findViewById(R.id.university);
        TextView dobTxt = findViewById(R.id.dob);
        TextView frenum = findViewById(R.id.frenphone);
        RadioGroup genderRG = findViewById(R.id.genderRG);

        int selectedId = genderRG.getCheckedRadioButtonId();
        Log.d(TAG, Integer.toString(selectedId));
        RadioButton genderSelected = findViewById(selectedId);

        String firstName = firstNameTxt.getText().toString();
        String lastName = lastNameTxt.getText().toString();
        String gender = genderSelected.getText().toString();
        String university = universityTxt.getText().toString();
        String dob = dobTxt.getText().toString();
        String frenphone = frenum.getText().toString();
        String image = "default";
        String status = "Hi there, I am using HelpYouNeed Chat.";
        String thumb_image = "default";
        String device_token = FirebaseInstanceId.getInstance().getToken();

        if(firstName.isEmpty() || firstName == null){
            Toast.makeText(ProfileInfoActivity.this, "Please enter your first name!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if(lastName.isEmpty() || lastName == null){
            Toast.makeText(ProfileInfoActivity.this, "Please enter your last name!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if(university.isEmpty() || university == null){
            Toast.makeText(ProfileInfoActivity.this, "Please enter your university!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if(dob.isEmpty() || dob == null){
            Toast.makeText(ProfileInfoActivity.this, "Please enter your DOB!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if(gender.isEmpty() || gender == null){
            Toast.makeText(ProfileInfoActivity.this, "Please select your gender!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if(frenphone.isEmpty() || frenphone == null){
            Toast.makeText(ProfileInfoActivity.this, "Please Enter Your Best Friend's  Number!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        final User user = new User(firstName, lastName, university, dob, gender, frenphone, image, status, thumb_image, device_token);

        user.setUid(mUser.getUid());



        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(user.getDisplayName())
                .build();

        mUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            createUserRecord(user);
                        }
                    }
                });
    }

    private void createUserRecord(User user){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");

        ref.child(user.getUid()).setValue(user);
        goToMainView();
    }

    private void goToMainView(){

        Intent mainIntent = new Intent(ProfileInfoActivity.this, MainActivity.class);
        startActivity(mainIntent);

    }
}
