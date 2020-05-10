package com.example.securitysystem;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment5 extends Fragment {

    public BlankFragment5() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank_fragment5, container, false);
         ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.rv);
        new getfeeds(getActivity().getApplicationContext(),recyclerView,progressDialog,(AppCompatActivity) getActivity()).execute();
        return view;
    }

}
