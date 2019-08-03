package lk.icbt.fyp.helpYouNeed.PsycologyProfile.mFragments;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import lk.icbt.fyp.helpYouNeed.PsycologyProfile.Profile_psycho;
import lk.icbt.fyp.helpYouNeed.R;

public class Psychology_prof extends ListFragment {


        String[] NAMES={"Dr.Pradeep Kariyawasam","Dr.Sunil Mendis"};
        int[] IMAGES={R.drawable.doc1, R.drawable.doc2};
        String[] DESCRIPTION={"Councling","Councling"};

    public static Psychology_prof newInstance()
    {
        Psychology_prof psychology_prof= new Psychology_prof();
        return psychology_prof;
    }

    ArrayList<HashMap<String,String>> data=new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        HashMap<String,String> map=new HashMap<String, String>();

        for (int i=0;i<NAMES.length;i++){
            map=new HashMap<String, String>();

            map.put("Name",NAMES[i]);
            map.put("Description",DESCRIPTION[i]);
            map.put("Image",Integer.toString(IMAGES[i]));

            data.add(map);
        }

        //keys in map
        String[] from={"Name","Description","Image"};

        //ids
        int[] to={R.id.textView_name, R.id.textView_description, R.id.imageView2};

        adapter=new SimpleAdapter(getActivity(),data, R.layout.customlayout,from,to);
        setListAdapter(adapter);


        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {

                // Toast.makeText(getActivity(),data.get(pos).get("Name"),Toast.LENGTH_SHORT).show();

                if(pos==0){

                    Intent myintent = new Intent(view.getContext(), Profile_psycho.class);
                    startActivityForResult(myintent,0);
                }

            }
        });
    }

    @Override
    public String toString(){
        return "Psychology_prof";
    }
}
