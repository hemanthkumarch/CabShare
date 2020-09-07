package Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hemanth.cabshare.CustomAdapter;
import com.hemanth.cabshare.EditActivity;
import com.hemanth.cabshare.MyRidesInfo;
import com.hemanth.cabshare.R;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


public class YourRidesFragment extends Fragment {
    private ListView listView;
    private TextView textView;
    private TextView nope;
    SimpleArcLoader simpleArcLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_your_rides, container, false);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").
                child(mAuth.getCurrentUser().getUid()).child("myRides");
        listView = view.findViewById(R.id.listView);
        textView = view.findViewById(R.id.text);
        nope = view.findViewById(R.id.nope);

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.TRANSPARENT);
        gd.setCornerRadius(10);
        gd.setStroke(2, Color.YELLOW);

        simpleArcLoader = view.findViewById(R.id.loader);
        simpleArcLoader.start();

        final ArrayList<String> dest = new ArrayList<>();
        final ArrayList<String> date = new ArrayList<>();
        final ArrayList<String> time = new ArrayList<>();
        final CustomAdapter customAdapter = new CustomAdapter(getContext(),dest, date, time);
        listView.setAdapter(customAdapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dest.clear();
                date.clear();
                time.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    MyRidesInfo Info = snapshot.getValue(MyRidesInfo.class);
                    String des = Info.getDest().toString();
                    String dat = Info.getDate().toString();
                    String tim = Info.getTime().toString();
                    dest.add(des);
                    date.add(dat);
                    time.add(tim);
                }
                if(dest.size()==0){
                    nope.setVisibility(View.VISIBLE);
                }
                customAdapter.notifyDataSetChanged();
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<String> ar = (ArrayList<String>) adapterView.getItemAtPosition(i);
                String dest = ar.get(0);
                String date = ar.get(1);
                String time = ar.get(2);
                Intent intent = new Intent(getActivity(), EditActivity.class);
                intent.putExtra("dest",dest);
                intent.putExtra("date",date);
                intent.putExtra("time",time);
                startActivity(intent);
            }
        });

//        final ArrayList<String> list = new ArrayList<>();
//        final ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_myrides, list);
//        listView.setAdapter(adapter);
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                list.clear();
//                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
//                    MyRidesInfo Info = snapshot.getValue(MyRidesInfo.class);
//                    String txt = Info.getDest() + "\n" + Info.getDate() + "\n" + Info.getTime();
//                    list.add(txt);
//                }
//                Collections.reverse(list);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });



        return view;
    }
}