package lk.icbt.fyp.helpYouNeed.ui.auth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import lk.icbt.fyp.helpYouNeed.R;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity(): ";

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        onBackPressed();
    }

    public void goToLogin(View view) {

        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);

    }

    public void signup(View view) {
        TextView emailTxt = findViewById(R.id.mail);
        String email = emailTxt.getText().toString();

        TextView passwordTxt = findViewById(R.id.passwrd);
        String password = passwordTxt.getText().toString();


        TextView conpasswordTxt = findViewById(R.id.conpassword);
        String conpassword = conpasswordTxt.getText().toString();

        TextView mobphoneTxt = findViewById(R.id.mobphone);
        String mobphone = mobphoneTxt.getText().toString();

        //Signup user

        if(password.length() < 6) {
            Toast.makeText(SignupActivity.this, "Password must have more than 6 characters!",
                    Toast.LENGTH_SHORT).show();
        }else if(!password.equals(conpassword)){
            Toast.makeText(SignupActivity.this, "Password and confirm password must match!",
                    Toast.LENGTH_SHORT).show();
        }
        else if(mobphone.length()!=10){
            Toast.makeText(SignupActivity.this, "Phone number needs to have 10 digits!",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                mUser = mAuth.getCurrentUser();

                                updateUI(mUser);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignupActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }

                            // ...
                        }
                    });
        }

    }
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        return;
    }

    private void updateUI(FirebaseUser user){
        if(user != null){
            Intent profileIntent = new Intent(SignupActivity.this, ProfileInfoActivity.class);
            profileIntent.putExtra("userId", mUser.getUid());
            startActivity(profileIntent);
        }
    }
}
