package com.example.securitysystem;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment11 extends Fragment {
TextView tv1,tv2,tv3;
ImageView iv;

    public BlankFragment11() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_blank_fragment11, container, false);
        tv1=(TextView)view.findViewById(R.id.tv1);
        tv2=(TextView)view.findViewById(R.id.tv2);
        tv3=(TextView)view.findViewById(R.id.tv3);
        iv=(ImageView)view.findViewById(R.id.iv);
        Bundle b = this.getArguments();

        String s = b.getString("key");
        ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        new getattacksdetails(tv1,tv2,tv3,iv,getActivity().getApplicationContext(),s,progressDialog,(AppCompatActivity) getActivity()).execute();



        return view;

    }

}
