package com.example.pecodetest.Fragment;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.pecodetest.MainActivity;
import com.example.pecodetest.R;


public class ExampleFragment extends Fragment {


    TextView fragmentCounter;
    int fragmentNameCounter;
    Button minusButton, plusButton, notificationButton;

    public ExampleFragment() {
    }

    public static ExampleFragment newInstance(int fragmentNumber){
        ExampleFragment exampleFragment = new ExampleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("sectionNumber", fragmentNumber);
        exampleFragment.setArguments(bundle);
        return exampleFragment;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dynamic, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            fragmentNameCounter = bundle.getInt("sectionNumber");
            displayValuew(view, fragmentNameCounter);
        }

        return view;
    }

    private void displayValuew(View view, int fragmentNameCounter) {
        notificationButton = view.findViewById(R.id.fragment_text);
        fragmentCounter = view.findViewById(R.id.fragment_counter);
        minusButton = view.findViewById(R.id.minus_button);
        plusButton = view.findViewById(R.id.plus_button);

        fragmentCounter.setText(String.valueOf(fragmentNameCounter));
        if (fragmentNameCounter != 1) {
            minusButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextSectionNumber = ((MainActivity) getActivity()).getNextSectionNumber();
                ((MainActivity) getActivity()).addFragment(ExampleFragment.newInstance(nextSectionNumber));
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = ((MainActivity) getActivity()).getCurentIndex();
                ((MainActivity) getActivity()).removeFragment(index - 1);


            }
        });

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = ((MainActivity) getActivity()).getCurentIndex();
                ((MainActivity) getActivity()).showNotification(index);
            }
        });
    }



}
