package com.sendbird.android.sample.user;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sendbird.android.sample.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmFragment extends Fragment {
    Button btnMedAlarm;

    public AlarmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_alarm, container, false);
        btnMedAlarm = rootView.findViewById(R.id.btnMedAlarm);

        btnMedAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AlarmActivity.class));
            }
        });

        return rootView;
    }

}
