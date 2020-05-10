package com.example.securitysystem;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment2 extends Fragment {

    ArrayList<String> lablist=new ArrayList<String>();
    ArrayList<String> packlist=new ArrayList<String>();

    public BlankFragment2() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_blank_fragment2, container, false);
        PackageManager packageManager=getContext().getApplicationContext().getPackageManager();
        Intent int1=new Intent(Intent.ACTION_MAIN,null);

        int1.addCategory(Intent.CATEGORY_LAUNCHER);
        int1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        List<ResolveInfo> resolveInfoList=packageManager.queryIntentActivities(int1,0);
        for(ResolveInfo resolveInfo:resolveInfoList)
        {
            ActivityInfo activityInfo=resolveInfo.activityInfo;
            String packageName=activityInfo.applicationInfo.packageName;
            String label=(String)packageManager.getApplicationLabel(activityInfo.applicationInfo);
            lablist.add(label);
            packlist.add(packageName);
        }
           RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.rv);
           //LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext().getApplicationContext(),LinearLayoutManager.VERTICAL,true);
           //recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));

        customadapter ca=new customadapter(getContext().getApplicationContext(),lablist,packlist, (AppCompatActivity) getActivity());
           recyclerView.setAdapter(ca);

return view;

    }

}
