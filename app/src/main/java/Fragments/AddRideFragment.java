package Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hemanth.cabshare.AppActivity;
import com.hemanth.cabshare.R;
import com.hemanth.cabshare.RegisterActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class AddRideFragment extends Fragment {

    private RadioGroup rg;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private TextView imp;
    private Spinner day;
    private Spinner month;
    private Spinner year;

    private Spinner hour;
    private Spinner ampm;
    private Button confirm;
    ProgressDialog pb;

    private DatabaseReference mRootRef;
    private FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_ride, container, false);
        rg = view.findViewById(R.id.radioGroup);
        day = view.findViewById(R.id.btn_day);
        month = view.findViewById(R.id.btn_month);
        year = view.findViewById(R.id.btn_year);
        hour = view.findViewById(R.id.btn_hour);
        ampm = view.findViewById(R.id.btn_ampm);
        rb1 = view.findViewById(R.id.campusToAirport);
        rb2 = view.findViewById(R.id.campusToRailwayStation);
        rb3 = view.findViewById(R.id.railwayStationToCampus);
        rb4 = view.findViewById(R.id.airportToCampus);
        confirm = view.findViewById(R.id.confirm);
        imp = view.findViewById(R.id.imp);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        pb= new ProgressDialog(getActivity());

        String [] days = {"--","01","02","03","04","05","06","07","08","09","10","11","12","13",
                "14","15","16","17","18","19","20","21","22","23","24","25", "26", "27", "28", "29", "30", "31"};
        final Spinner spinnerDay = (Spinner) view.findViewById(R.id.btn_day);
        ArrayAdapter<String> adapterDay = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, days);
        adapterDay.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerDay.setAdapter(adapterDay);

        String [] months = {"--","January","February","March","April","May","June","July","August","September","October ","November","December"};
        final Spinner spinnerMonth = (Spinner) view.findViewById(R.id.btn_month);
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, months);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerMonth.setAdapter(adapterMonth);

        String [] years = {"--","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029 ","2030"};
        final Spinner spinnerYear = (Spinner) view.findViewById(R.id.btn_year);
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, years);
        adapterYear.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerYear.setAdapter(adapterYear);

        String [] hours = {"--","00","01","02","03","04","05","06","07","08","09","10","11","12"};
        final Spinner spinner = (Spinner) view.findViewById(R.id.btn_hour);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, hours);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        String [] mins = {"--","00","30"};
        final Spinner spinner2 = (Spinner) view.findViewById(R.id.btn_min);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, mins);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner2.setAdapter(adapter2);

        String [] ampm = {"AM","PM"};
        final Spinner spinner1 = (Spinner) view.findViewById(R.id.btn_ampm);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, ampm);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner1.setAdapter(adapter1);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dest="NOPE";
                if(rb1.isChecked()==true){
                    dest = rb1.getText().toString();
                }
                else if(rb2.isChecked()==true){
                    dest = rb2.getText().toString();
                }
                else if(rb3.isChecked()==true){
                    dest = rb3.getText().toString();
                }
                else if(rb4.isChecked()==true){
                    dest = rb4.getText().toString();
                }
                else{
                    imp.setVisibility(View.VISIBLE);
                    return;
                }
                if(spinnerDay.getSelectedItem().toString().equals("--") || spinnerMonth.getSelectedItem().toString().equals("--") || spinner2.getSelectedItem().toString().equals("--") ||
                        spinnerYear.getSelectedItem().toString().equals("--") || spinner.getSelectedItem().toString().equals("--") || spinner1.getSelectedItem().toString().equals("--")){
                    imp.setVisibility(View.VISIBLE);
                    return;
                }

                String txt_date = spinnerDay.getSelectedItem().toString() + spinnerMonth.getSelectedItem().toString() + spinnerYear.getSelectedItem().toString();
                String time = spinner.getSelectedItem().toString() +":" + spinner2.getSelectedItem().toString() + " " + spinner1.getSelectedItem().toString();
                AddDetails(dest, txt_date, time);
                Snackbar.make(view, "Saved!", Snackbar.LENGTH_LONG)
                        .setAction("action", null).show();

                rb1.setChecked(false);
                rb2.setChecked(false);
                rb3.setChecked(false);
                rb4.setChecked(false);
                spinner.setSelection(0);
                spinner1.setSelection(0);
                spinner2.setSelection(0);
                spinnerDay.setSelection(0);
                spinnerMonth.setSelection(0);
                spinnerYear.setSelection(0);
                imp.setVisibility(View.GONE);

            }
        });
        return view;
    }

    private void AddDetails(String dest, String date, String time) {
        pb.setMessage("Please Wait");
        pb.show();

        HashMap<String, Object> map = new HashMap<>();
        map.put("dest", dest);
        map.put("date", date);
        map.put("time", time);
        String id = mRootRef.child("Users").child(mAuth.getCurrentUser().getUid()).child("myRides").push().getKey();
        String mid = mRootRef.child(dest).child(date).child(time).push().getKey();
        mRootRef.child(dest).child(date).child(time).child(mid).setValue(mAuth.getCurrentUser().getUid());
        mRootRef.child("Users").child(mAuth.getCurrentUser().getUid()).child("myRides").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    pb.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pb.dismiss();
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}