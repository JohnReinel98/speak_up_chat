package com.sendbird.android.sample.user;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sendbird.android.sample.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DepressionFragment extends Fragment {
    private ProgressBar depMeter;
    private TextView depStatus, reTake;
    private String depTest;
    private FirebaseAuth firebaseAuth;
    private String id;
    private TextView resContainer;
    public DepressionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_depression, container, false);
        depMeter = v.findViewById(R.id.depMeter);
        depStatus = v.findViewById(R.id.depStatus);
        reTake = v.findViewById(R.id.btnRetake);
        resContainer = v.findViewById(R.id.resultContainer);
        firebaseAuth = FirebaseAuth.getInstance();
//        Answers answers = new Answers();
//        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        //Not at all  0
        //Several Days 25
        //More than half a days 75
        //Nearly Everyday 100
        //all answer divide by 10
        id = firebaseAuth.getCurrentUser().getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference depResult = database.getReference(id).child("depTest");
        depResult.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String user = dataSnapshot.getValue(String.class);
                String finalres = user.toString();

                depMeter.setProgress(Integer.parseInt(finalres));
                int color = 0xFF00FF00;
                if(depMeter.getProgress()<=100 && depMeter.getProgress()>=76){
                    depMeter.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                    depMeter.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                    depStatus.setText("SEVERE");
                }
                if(depMeter.getProgress()<=75 && depMeter.getProgress()>=26){
                    depMeter.getIndeterminateDrawable().setColorFilter(Color.rgb(255,165,0), PorterDuff.Mode.SRC_IN);
                    depMeter.getProgressDrawable().setColorFilter(Color.rgb(255,165,0), PorterDuff.Mode.SRC_IN);
                    depStatus.setText("MODERATE");
                }
                if(depMeter.getProgress()<=25 && depMeter.getProgress()>=1){
                    depMeter.getIndeterminateDrawable().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
                    depMeter.getProgressDrawable().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
                    depStatus.setText("MILD");
                }
                if(depMeter.getProgress()==0){
                    depMeter.getIndeterminateDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
                    depMeter.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
                    depStatus.setText("NO SIGN OF DEPRESSION");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //int finalres = (int) (long) depTest;
        //depMeter.setProgress(finalres);


        reTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UserQuestion.class));
            }
        });

        return v;
    }

}
