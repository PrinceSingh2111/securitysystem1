package com.example.securitysystem;

import android.app.ProgressDialog;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class customadapterforallguideline extends RecyclerView.Adapter<customadapterforallguideline.MyViewHolder>
{
    ArrayList<String> attacktitlelist;
    ArrayList<String> descriptionlist;
    ArrayList<String> contentlist;
    ArrayList<String> imagelist;


    ProgressDialog progressDialog;
    Context context;
    AppCompatActivity ac;
    Drawable dw;
    String ans;
    public customadapterforallguideline(Context context, ArrayList<String> attacktitlelist, ArrayList<String> descriptionlist, ArrayList<String> contentlist, ArrayList<String> imagelist, String ans, AppCompatActivity ac, ProgressDialog progressDialog)
    {
        this.ans=ans;
        this.context=context;
        this.attacktitlelist=attacktitlelist;
        this.descriptionlist=descriptionlist;
        this.contentlist=contentlist;
        this.imagelist=imagelist;
        this.progressDialog=progressDialog;
        this.ac = ac;

    }

    @Override
    public  MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.guidelinessrowlayout,viewGroup,false);
        MyViewHolder vh=new MyViewHolder(v);
        return vh;


    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int i) {

        myViewHolder.tv1.setText(attacktitlelist.get(i));
        myViewHolder.tv2.setText(descriptionlist.get(i));


        String link2 = "https://zsupsecurity.000webhostapp.com/secure/admin/" + imagelist.get(i);
        new DownloadImageFromInternet(myViewHolder.iv)
                .execute(link2);
            if(progressDialog!=null&&myViewHolder.iv!=null)
            {
                progressDialog.dismiss();
            }
        myViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment frag=new BlankFragment12();
                Bundle bundle = new Bundle();
                bundle.putString("key",attacktitlelist.get(i));
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
        return attacktitlelist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        CardView cv;
        TextView tv1,tv2;
        ImageView iv;
        public MyViewHolder(View itemview)
        {
            super(itemview);
            cv=(CardView)itemview.findViewById(R.id.cv);
            cv.setRadius(20F);
            tv1=(TextView)itemview.findViewById(R.id.tv1);
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
