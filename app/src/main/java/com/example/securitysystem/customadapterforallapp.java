package com.example.securitysystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class customadapterforallapp extends RecyclerView.Adapter<customadapterforallapp.MyViewHolder>
{
    ArrayList<String> AppName;
    ArrayList<String> AppDesc;

    ArrayList<String> Iconlist;
    ProgressDialog progressDialog;
    Context context;
    AppCompatActivity ac;
    Drawable dw;
    String ans;
    public customadapterforallapp(Context context, ArrayList<String> AppName, ArrayList<String> Appdesc,ArrayList<String> Iconlist,String ans, AppCompatActivity ac,ProgressDialog progressDialog)
    {
        this.ans=ans;
        this.context=context;
        this.AppName=AppName;
        this.AppDesc=Appdesc;
        this.Iconlist=Iconlist;
        this.progressDialog=progressDialog;
        this.ac = ac;

    }

    @Override
    public  MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.allapprowlayout,viewGroup,false);
        MyViewHolder vh=new MyViewHolder(v);
        return vh;


    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int i) {

        myViewHolder.tv.setText(AppDesc.get(i));


        myViewHolder.tv2.setText(AppName.get(i));
        String link2 = "https://zsupsecurity.000webhostapp.com/secure/admin/" + Iconlist.get(i);


        new DownloadImageFromInternet(myViewHolder.iv)
                .execute(link2);
            if(progressDialog!=null&&myViewHolder.iv!=null)
            {
                progressDialog.dismiss();
            }
        myViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment frag=new BlankFragment9();
                Bundle bundle = new Bundle();
                bundle.putString("key",AppName.get(i));
                frag.setArguments(bundle);

                FragmentTransaction fm=ac.getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frame,frag);
                fm.commit();


            }
        });
        /* File imgFile = new  File("C:/wamp/www/secure/admin/image/fbicon.png");
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        myViewHolder.iv.setImageBitmap(myBitmap);
*/
    }

    @Override
    public int getItemCount() {
        return AppName.size();
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





    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
            Toast.makeText(context, "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {

            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }


}
