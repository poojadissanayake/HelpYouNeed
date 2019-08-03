package lk.icbt.fyp.helpYouNeed.ui.auth;

import android.content.Context;
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
import lk.icbt.fyp.helpYouNeed.helpers.MyService;
import lk.icbt.fyp.helpYouNeed.ui.MainActivity;
import lk.icbt.fyp.helpYouNeed.ui.services.UserBehaviourService;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName() + ": ";

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private Context context;
//    //Create broadcast object
//    BroadcastReceiver mybroadcast = new BroadcastReceiver() {
//        //When Event is published, onReceive method is called
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            // TODO Auto-generated method stub
//            Log.i("[BroadcastReceiver]", "MyReceiver");
//
//            if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
//                Log.i("[BroadcastReceiver]", "Screen ON");
//            }
//            else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
//                Log.i("[BroadcastReceiver]", "Screen OFF");
//            }
//
//        }
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.context=this;
        startService(new Intent(this.context, MyService.class));

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        Intent explicitIntent = new Intent(this, UserBehaviourService.class);
        startService(explicitIntent);

        updateUI(mUser);

//        registerReceiver(mybroadcast, new IntentFilter(Intent.ACTION_SCREEN_ON));
//        registerReceiver(mybroadcast, new IntentFilter(Intent.ACTION_SCREEN_OFF));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI(mUser);
    }

    public void login(View view) {

        TextView emailTxt = findViewById(R.id.loginEmail);
        TextView passwordTxt = findViewById(R.id.loginPwd);

        String email = emailTxt.getText().toString();
        String password = passwordTxt.getText().toString();

        if(email.isEmpty() || email == null){
            Toast.makeText(LoginActivity.this, "Please enter your email!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.isEmpty() || password == null){
            Toast.makeText(LoginActivity.this, "Please enter your password!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });

        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);

    }

    public void goToSignUp(View view) {

        Intent signUpIntent = new Intent(this, SignupActivity.class);
        startActivity(signUpIntent);

    }

    private void updateUI(FirebaseUser user){
        if(user != null){
            Intent profileIntent = new Intent(LoginActivity.this, MainActivity.class);
            profileIntent.putExtra("userId", mUser.getUid());
            startActivity(profileIntent);
        }
    }
}
