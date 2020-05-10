package com.example.securitysystem;

import android.content.Context;
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

import java.io.InputStream;
import java.util.ArrayList;

public class customadapterforallappsearch extends RecyclerView.Adapter<customadapterforallappsearch.MyViewHolder>
{
   String AppName;
   String AppDesc;

    String Iconlist;
    Context context;
    AppCompatActivity ac;
    Drawable dw;
    String ans;
    public customadapterforallappsearch(Context context,String app,String desc,String icon,AppCompatActivity ac)
    {
        this.ans=ans;
        this.context=context;
        this.ac = ac;
        this.AppName=app;
        this.AppDesc=desc;
        this.Iconlist=icon;
    }

    @Override
    public  MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.allapprowlayout,viewGroup,false);
        MyViewHolder vh=new MyViewHolder(v);
        return vh;


    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int i) {

        myViewHolder.tv.setText(AppDesc);


        myViewHolder.tv2.setText(AppName);
         String link2 = "https://zsupsecurity.000webhostapp.com/secure/admin/" + Iconlist;


        new DownloadImageFromInternet(myViewHolder.iv)
                .execute(link2);

        myViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment frag=new BlankFragment9();
                Bundle bundle = new Bundle();
                bundle.putString("key",AppName);
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
        return 1;
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
