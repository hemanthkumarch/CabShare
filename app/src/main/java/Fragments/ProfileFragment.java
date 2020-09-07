package Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hemanth.cabshare.MainActivity;
import com.hemanth.cabshare.R;

import java.util.Objects;


public class ProfileFragment extends Fragment {
    private TextView name;
    private TextView rollNo;
    private TextView phoneNumber;
    private TextView gender;
    private TextView hostel;
    private TextView room;
    ProgressBar pb;

    private DatabaseReference mRootRef;
    private FirebaseAuth mAuth;

    private LinearLayout layout_roll, layout_phone, layout_hostel, layout_room, layout_gender;
    LinearLayout show_roll, show_phone, show_hostel, show_room,show_gender;
    Button cancel_roll, cancel_phone, cancel_hostel, cancel_room, cancel_gender, logout;
    EditText edit_roll, edit_phone, edit_hostel, edit_room, edit_gender;
    Button ok_roll, ok_phone, ok_hostel, ok_room, ok_gender;

    ProgressDialog pd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        layout_roll = view.findViewById(R.id.layout_roll);
        layout_phone = view.findViewById(R.id.layout_phone);
        layout_hostel = view.findViewById(R.id.layout_hostel);
        layout_room = view.findViewById(R.id.layout_room);
        layout_gender = view.findViewById(R.id.layout_gender);

        show_roll = view.findViewById(R.id.show_roll);
        show_phone = view.findViewById(R.id.show_phone);
        show_hostel = view.findViewById(R.id.show_hostel);
        show_room = view.findViewById(R.id.show_room);
        show_gender = view.findViewById(R.id.show_gender);

        name = view.findViewById(R.id.fullName);
        rollNo = view.findViewById(R.id.rollNumber);
        phoneNumber = view.findViewById(R.id.phoneNumber);
        hostel = view.findViewById(R.id.hostel);
        room = view.findViewById(R.id.room);
        gender = view.findViewById(R.id.gender);

        cancel_roll = view.findViewById(R.id.cancel_roll);
        cancel_phone = view.findViewById(R.id.cancel_phone);
        cancel_hostel = view.findViewById(R.id.cancel_hostel);
        cancel_room = view.findViewById(R.id.cancel_room);
        cancel_gender = view.findViewById(R.id.cancel_gender);

        edit_roll = view.findViewById(R.id.edit_roll);
        edit_phone = view.findViewById(R.id.edit_phone);
        edit_hostel = view.findViewById(R.id.edit_hostel);
        edit_room = view.findViewById(R.id.edit_room);
        edit_gender = view.findViewById(R.id.edit_gender);

        ok_roll = view.findViewById(R.id.ok_roll);
        ok_phone = view.findViewById(R.id.ok_phone);
        ok_hostel = view.findViewById(R.id.ok_hostel);
        ok_room = view.findViewById(R.id.ok_room);
        ok_gender = view.findViewById(R.id.ok_gender);

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Please Wait...");
        pd.show();

        mAuth = FirebaseAuth.getInstance();
        mRootRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());

        DatabaseReference refname = mRootRef.child("name");
        refname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String sname = dataSnapshot.getValue().toString();
                String str = sname.toUpperCase();
                name.setText(str);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference refroll = mRootRef.child("roll");
        refroll.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String str = dataSnapshot.getValue().toString();
                rollNo.setText(str);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference refhostel = mRootRef.child("hostel");
        refhostel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String str = dataSnapshot.getValue().toString();
                hostel.setText(str);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        DatabaseReference refroom = mRootRef.child("room");
        refroom.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String str = dataSnapshot.getValue().toString();
                room.setText(str);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        DatabaseReference refgender = mRootRef.child("gender");
        refgender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String str = dataSnapshot.getValue().toString();
                gender.setText(str);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        DatabaseReference refphone = mRootRef.child("phoneNumber");
        refphone.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String sphone = dataSnapshot.getValue().toString();
                phoneNumber.setText(sphone);
                pd.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        ok_roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = edit_roll.getText().toString();
                mRootRef.child("roll").setValue(str);
                edit_roll.setText("");
                layout_roll.setVisibility(View.GONE);
            }
        });

        ok_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = edit_phone.getText().toString();
                mRootRef.child("phoneNumber").setValue(str);
                edit_phone.setText("");
                layout_phone.setVisibility(View.GONE);
            }
        });

        ok_hostel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = edit_hostel.getText().toString();
                mRootRef.child("hostel").setValue(str);
                edit_hostel.setText("");
                layout_hostel.setVisibility(View.GONE);
            }
        });

        ok_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = edit_room.getText().toString();
                mRootRef.child("room").setValue(str);
                edit_room.setText("");
                layout_room.setVisibility(View.GONE);
            }
        });

        ok_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = edit_gender.getText().toString();
                mRootRef.child("gender").setValue(str);
                edit_gender.setText("");
                layout_gender.setVisibility(View.GONE);
            }
        });

        show_roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layout_roll.getVisibility()==View.GONE){
                    layout_roll.setVisibility(View.VISIBLE);
                    layout_phone.setVisibility(View.GONE);
                    layout_hostel.setVisibility(View.GONE);
                    layout_room.setVisibility(View.GONE);
                    layout_gender.setVisibility(View.GONE);
                }
                else{
                    layout_roll.setVisibility(View.GONE);
                }
            }
        });

        show_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layout_phone.getVisibility()==View.GONE){
                    layout_roll.setVisibility(View.GONE);
                    layout_phone.setVisibility(View.VISIBLE);
                    layout_hostel.setVisibility(View.GONE);
                    layout_room.setVisibility(View.GONE);
                    layout_gender.setVisibility(View.GONE);
                }
                else {
                    layout_phone.setVisibility(View.GONE);
                }
            }
        });

        show_hostel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layout_hostel.getVisibility()==View.GONE){
                    layout_roll.setVisibility(View.GONE);
                    layout_phone.setVisibility(View.GONE);
                    layout_hostel.setVisibility(View.VISIBLE);
                    layout_room.setVisibility(View.GONE);
                    layout_gender.setVisibility(View.GONE);
                }
                else {
                    layout_hostel.setVisibility(View.GONE);
                }
            }
        });

        show_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layout_room.getVisibility()==View.GONE){
                    layout_roll.setVisibility(View.GONE);
                    layout_phone.setVisibility(View.GONE);
                    layout_hostel.setVisibility(View.GONE);
                    layout_room.setVisibility(View.VISIBLE);
                    layout_gender.setVisibility(View.GONE);
                }
                else {
                    layout_room.setVisibility(View.GONE);
                }
            }
        });

        show_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layout_gender.getVisibility()==View.GONE){
                    layout_roll.setVisibility(View.GONE);
                    layout_phone.setVisibility(View.GONE);
                    layout_hostel.setVisibility(View.GONE);
                    layout_room.setVisibility(View.GONE);
                    layout_gender.setVisibility(View.VISIBLE);
                }
                else {
                    layout_gender.setVisibility(View.GONE);
                }
            }
        });

        cancel_roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_roll.setText("");
                layout_roll.setVisibility(View.GONE);
            }
        });

        cancel_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_phone.setText("");
                layout_phone.setVisibility(View.GONE);
            }
        });
        cancel_hostel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_hostel.setText("");
                layout_hostel.setVisibility(View.GONE);
            }
        });
        cancel_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_room.setText("");
                layout_room.setVisibility(View.GONE);
            }
        });
        cancel_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_gender.setText("");
                layout_gender.setVisibility(View.GONE);
            }
        });

        logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });



        return view;
    }
}