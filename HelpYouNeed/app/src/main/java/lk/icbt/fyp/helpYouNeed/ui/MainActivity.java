package lk.icbt.fyp.helpYouNeed.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import lk.icbt.fyp.helpYouNeed.Chat.MainChatIntActivity;
import lk.icbt.fyp.helpYouNeed.FriendLocation.ListOnline;
import lk.icbt.fyp.helpYouNeed.Images.ImagesSuggesting;
import lk.icbt.fyp.helpYouNeed.PsycologyProfile.Psychology_Profile;
import lk.icbt.fyp.helpYouNeed.R;
import lk.icbt.fyp.helpYouNeed.Videos.VideoSuggesting;
import lk.icbt.fyp.helpYouNeed.ui.auth.LoginActivity;
import lk.icbt.fyp.helpYouNeed.ui.fragments.chat;
import lk.icbt.fyp.helpYouNeed.ui.fragments.main;
import lk.icbt.fyp.helpYouNeed.ui.fragments.music;
import lk.icbt.fyp.helpYouNeed.ui.fragments.quotes;
import lk.icbt.fyp.helpYouNeed.ui.services.UserBehaviourService;


public class MainActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        chat.OnFragmentInteractionListener,
        main.OnFragmentInteractionListener,
        music.OnFragmentInteractionListener,
        quotes.OnFragmentInteractionListener {

   /* private FrameLayout frameLayout;
    private ImageView imageView;
    private StorageReference mStorageRef;
    private ProgressDialog mProgress;
    private int i=0;
    private TextView textView;
    private RelativeLayout relativeLayout;*/

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Intent videos = new Intent(MainActivity.this,VideoSuggesting.class);
//        startActivity(videos);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        View headerView = navigationView.getHeaderView(0);

        TextView displayNameTxt = headerView.findViewById(R.id.displayNameTxt);
        TextView displayEmailTxt = headerView.findViewById(R.id.displayEmailTxt);

        if(mUser != null){
            displayNameTxt.setText(mUser.getDisplayName());
            displayEmailTxt.setText(mUser.getEmail());

        }

        Intent explicitIntent = new Intent(this, UserBehaviourService.class);
        startService(explicitIntent);

       // Intent movementServiceIntent = new Intent(this, DeviceMovementService.class);
        //startService(movementServiceIntent);


        ReadText();
        onBackPressed();
    }

    private void ReadText() {
        String SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();
        String FILENAME = "HYNKeyboard.txt";

        File fis = new File(SDCARD + File.separator + FILENAME);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fis));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);

                RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
                String url = "http://192.168.1.3:5000/predict"; //ip of lap
                System.out.println(url);
                final String finalLine = line;

                StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("hello :"+response);

                        if(response.equals("['sad']")){
                            getNotification();
                        }

                        //This code is executed if the server responds, whether or not the response contains data.
                        //The String 'response' contains the server's response.
                    }
                }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("hi "+ error);
                        //This code is executed if there is an error.
                    }
                }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> MyData = new HashMap<String, String>();
                        MyData.put("first", finalLine); //Add the data you'd like to send to the server.
                        return MyData;
                    }
                };


                MyRequestQueue.add(MyStringRequest);
//                //text.append('\n');
//
//                if(line.equals("i feel sad")) {
//                    //Toast.makeText(Read_text.this, "Uploading Finished...", Toast.LENGTH_LONG).show();
//                    getNotification();
//                }
//                if(line.equals("i feel lonly")) {
//                   // Toast.makeText(MainActivity.this, "Uploading Finished...", Toast.LENGTH_LONG).show();
//                    getNotification();
//                }
            }
            br.close();
        } catch (IOException e) {
            //You'll need to add proper error handling here
        }


        // String SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();
//        String FILENAME = "FinKeyboard.txt";
//
//
//        File fis = new File(SDCARD + File.separator + FILENAME);
//        StringBuilder text = new StringBuilder();
//
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(fis));
//            String line;
//
//            while ((line = br.readLine()) != null) {
//                text.append(line);
//                //text.append('\n');
//
//                if(line.equals("i feel sad")) {
//                    //Toast.makeText(Read_text.this, "Uploading Finished...", Toast.LENGTH_LONG).show();
//                    getNotification();
//                }
//                if(line.equals("i feel lonly")) {
//                   // Toast.makeText(MainActivity.this, "Uploading Finished...", Toast.LENGTH_LONG).show();
//                    getNotification();
//                }
//            }
//            br.close();
//        } catch (IOException e) {
//            //You'll need to add proper error handling here
//        }

        //String a = text.toString();
        //Set the text
        // tv.setText(text);
    }

    private void getNotification() {
        Intent intent = new Intent(this,Psychology_Profile.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder notification = new Notification.Builder(MainActivity.this)
                .setSmallIcon(R.drawable.head)
                .addAction(R.drawable.online_icon,"YES",pendingIntent)
                .setContentTitle("Are you stressed?")
                .setContentText("HelpYouNeed has monitored your words behaviour!")
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(BIND_DEBUG_UNBIND))
                .setContentIntent(pendingIntent);

        ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(11111, notification.build());

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_POWER) {
            Log.w("HelpYouNeed", "POWER BUTTON");
            // Do something here...
            event.startTracking(); // Needed to track long presses
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_POWER) {
            // Do something here...
            return true;
        }
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        return;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.refreshmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if(item.getItemId() == R.id.refresh) {
            Intent intent = new Intent(MainActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.menu_item_chat) {
            Intent chat = new Intent(MainActivity.this,MainChatIntActivity.class);
            startActivity(chat);
//            Intent map = new Intent(MainActivity.this,Read_text.class);
//            startActivity(map);
        }

        else if (id == R.id.nav_friend_location) {
            Intent map = new Intent(MainActivity.this,ListOnline.class);
            startActivity(map);

        }
        else
        if (id == R.id.nav_psychology)
        {

            /*MainActivity.this.getFragmentManager().beginTransaction().replace(R.id.containerID, Psychology_prof.newInstance()).commit();
            getSupportActionBar().setTitle("Psychology Proffesions");
            item.setChecked(true);*/

            Intent map = new Intent(MainActivity.this,Psychology_Profile.class);
            startActivity(map);


        }
       else if (id == R.id.menu_item_images) {
            Intent images = new Intent(MainActivity.this,ImagesSuggesting.class);
            startActivity(images);

        }
        else if (id == R.id.menu_item_videos) {
            Intent videos = new Intent(MainActivity.this,VideoSuggesting.class);
            startActivity(videos);

        }
        else if (id == R.id.menu_item_logout) {
            Logout();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void Logout(){
        mAuth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new main(), "Home");
        adapter.addFragment(new chat(), "Chat");
        adapter.addFragment(new music(), "Music");
        adapter.addFragment(new quotes(), "Quotes");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>() ;
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 100 && resultCode == RESULT_OK) {
//
//            mProgress.setMessage("Uploading Image...");
//            mProgress.show();
//
//            Uri uri = data.getData();
//
//            //StorageReference filepath = mStorageRef.child("Photos" + System.currentTimeMillis() + "." + getImageExt(uri));
//            StorageReference filepath = mStorageRef.child("Photos").child(uri.getLastPathSegment());
//            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                    mProgress.dismiss();
//
//                    Toast.makeText(MainActivity.this, "Uploading Finished...", Toast.LENGTH_LONG).show();
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//
//                }
//            });
//
//        }
//
//    }

//    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
//        new AlertDialog.Builder(MainActivity.this)
//                .setMessage(message)
//                .setPositiveButton("OK", okListener)
//                .setNegativeButton("Cancel", null)
//                .create()
//                .show();
//    }
}
