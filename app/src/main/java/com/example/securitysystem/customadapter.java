package com.example.securitysystem;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class customadapter extends RecyclerView.Adapter<customadapter.MyViewHolder>
{
    ArrayList<String> labName;
    ArrayList<String> packName;
    Context context;
    AppCompatActivity ac;
    Drawable dw;
    public customadapter(Context context, ArrayList<String> labName, ArrayList<String> packName, AppCompatActivity ac)
    {
        this.context=context;
        this.labName=labName;
        this.packName=packName;
        this.ac = ac;

    }

    @Override
    public  MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rowlayout,viewGroup,false);
        MyViewHolder vh=new MyViewHolder(v);
        return vh;


    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int i) {
        myViewHolder.tv.setText(labName.get(i));
       // myViewHolder.tv2.setText(packName.get(i));
        Drawable dw = null;

        try {
            dw=context.getPackageManager().getApplicationIcon(packName.get(i));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        myViewHolder.iv.setImageDrawable(dw);
        final Drawable finalDw = dw;
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment frag=new permissions();
                Bundle bundle = new Bundle();
                bundle.putString("key",packName.get(i));
                bundle.putString("appname",labName.get(i));
                frag.setArguments(bundle);
                FragmentTransaction fm= ac.getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frame,frag);
                fm.commit();
                }
        });


    }

    @Override
    public int getItemCount() {
        return labName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        CardView cv;
        TextView tv,tv2;
        ImageView iv;
        public MyViewHolder(View itemview)
        {
            super(itemview);
            cv=(CardView)itemview.findViewById(R.id.cv);
            cv.setRadius(20F);
            tv=(TextView)itemview.findViewById(R.id.tv);
            tv2=(TextView)itemview.findViewById(R.id.tv2);

            iv=(ImageView)itemview.findViewById(R.id.iv);
        }

    }

}
