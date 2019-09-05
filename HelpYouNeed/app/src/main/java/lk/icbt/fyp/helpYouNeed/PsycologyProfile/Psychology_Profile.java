package lk.icbt.fyp.helpYouNeed.PsycologyProfile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import lk.icbt.fyp.helpYouNeed.R;

public class Psychology_Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychology__profile);

        String[] NAMES={"Dr.abc aabbcc","Dr.bb bbb"};
        int[] IMAGES={R.drawable.doc1, R.drawable.doc2};
        String[] DESCRIPTION={"Counseling","Counseling"};

        ListView listView =(ListView)findViewById(R.id.listview);

        customAdapter customAdapter = new customAdapter(this, NAMES, DESCRIPTION, IMAGES);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {

                    Intent myintent = new Intent(view.getContext(), Profile_psycho.class);
                    startActivityForResult(myintent, 0);
                }
            }
        });

    }

    class customAdapter extends ArrayAdapter<String> {
        Context context;
        String psyNames[];
        String psyDes[];
        int [] images;

        customAdapter(Context c, String[] NAMES, String[] DESCRIPTION, int[] IMAGES){
            super(c,R.layout.customlayout, R.id.text1,NAMES);
            this.context=c;
            this.images=IMAGES;
            this.psyDes=DESCRIPTION;
            this.psyNames=NAMES;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.customlayout,parent,false);
            ImageView imageView = view.findViewById(R.id.imageView2);
            TextView name = view.findViewById(  R.id.textView_name);
            TextView description = view.findViewById(R.id.textView_description);
            imageView.setImageResource(images[position]);
            name.setText(psyNames[position]);
            description.setText(psyDes[position]);


            return view;
        }
    }
}
