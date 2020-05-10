package com.example.securitysystem;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment10 extends Fragment {

    TextView tv;
    EditText tv1;
    ListView lv;

    public BlankFragment10() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_blank_fragment10, container, false);
        lv=(ListView)view.findViewById(R.id.permissnoncr);

        Bundle b = this.getArguments();
        tv=(TextView)view.findViewById(R.id.mytext);

        String s = b.getString("key");

        ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

/*        ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.rv);
        iv1=(ImageView)view.findViewById(R.id.iv1);
*/        new getvuldetailsbypackagename(getActivity().getApplicationContext(),s,tv,lv,(AppCompatActivity) getActivity(),progressDialog).execute();

return view;
    }


}
