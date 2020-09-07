package com.hemanth.cabshare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity {
    TextView tv, tvdate, tvdest, tvtime;
    DatabaseReference mRootRef;
    FirebaseAuth mAuth;
    ListView listView;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        tv = findViewById(R.id.tv);
        pb = findViewById(R.id.pb);
        tvdest = findViewById(R.id.dest);
        tvdate = findViewById(R.id.date);
        tvtime = findViewById(R.id.ttime);
        listView = findViewById(R.id.listView);
        Intent intent = getIntent();
        String dest = intent.getStringExtra("dest");
        tvdest.setText(dest);
        String date = intent.getStringExtra("date");
        tvdate.setText(date);
        String time = intent.getStringExtra("time");
        tvtime.setText(time);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        DatabaseReference ref = mRootRef.child(dest).child(date).child(time);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tv.setText("");
                ArrayList<String> arrayList =  new ArrayList<>();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    arrayList.add(snapshot.getValue().toString());
                }

                for(int i=0;i<arrayList.size();i++){
                    String id = arrayList.get(i);
                    DatabaseReference refname =mRootRef.child("Users").child(id).child("name");
                    refname.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String fname = dataSnapshot.getValue(String.class);
                            String str = fname.toUpperCase();
                            tv.append("Name         : " + str + "\n");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    DatabaseReference refroll =mRootRef.child("Users").child(id).child("roll");
                    refroll.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String str = dataSnapshot.getValue(String.class);
                            tv.append("Roll Number   : " + str + "\n");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    DatabaseReference refroom =mRootRef.child("Users").child(id).child("room");
                    refroom.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String str = dataSnapshot.getValue(String.class);
                            tv.append("Details       : " + str + ", ");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    DatabaseReference refhostel =mRootRef.child("Users").child(id).child("hostel");
                    refhostel.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String str = dataSnapshot.getValue(String.class);
                            tv.append(str + " - ");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    DatabaseReference refgender =mRootRef.child("Users").child(id).child("gender");
                    refgender.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String str = dataSnapshot.getValue(String.class);
                            tv.append(str + "\n");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    DatabaseReference refphone =mRootRef.child("Users").child(id).child("phoneNumber");
                    refphone.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String str = dataSnapshot.getValue(String.class);
                            tv.append("Phone Number  : " + str + "\n\n\n");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                pb.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
