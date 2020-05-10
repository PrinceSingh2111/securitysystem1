package com.example.securitysystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.StringTokenizer;

//import com.squareup.picasso.Picasso;

public class getfeedsdetails extends AsyncTask<String , Void , String> {
    Context context;
    AppCompatActivity appCompatActivity;
    String ans = "";
    ArrayList<String> attacknamelist = new ArrayList<String>();
    ArrayList<String> descriptionlist = new ArrayList<String>();
    ArrayList<String> contentlist = new ArrayList<String>();
    ArrayList<String> imagelist = new ArrayList<String>();

    ArrayList<String> preventionlist = new ArrayList<String>();
    ArrayList<String> datelist = new ArrayList<String>();

    ProgressDialog progressDialog;
    String s;
    TextView tv1, tv2;
    ImageView iv;

    public getfeedsdetails(TextView tv1, TextView tv2, ImageView iv, Context context, String s, ProgressDialog progressDialog, AppCompatActivity appCompatActivity) {
        this.context = context;
        this.s = s;
        this.tv1 = tv1;
        this.tv2 = tv2;
        this.iv = iv;
        this.progressDialog = progressDialog;
        this.appCompatActivity = appCompatActivity;


    }


    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... arg0) {
        try {


            String link = "https://zsupsecurity.000webhostapp.com/secure/admin/returnguidelinesdetails.php";

            String data = URLEncoder.encode("feedtitle", "UTF-8") + "=" +
                    URLEncoder.encode(s, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();


            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;
            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }

            return sb.toString();
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result.equals("Credentials not  ok")) {
            ans = "n";
        } else {
            ans = "y";
        }

        StringTokenizer st = new StringTokenizer(result, "&");
        tv1.setText(st.nextToken() + "");
         String news=st.nextToken();
     /*   int strlen=news.length()/4;

        String s1=news.substring(0,strlen);
        String s2=news.substring(strlen,strlen*2);

        String s3=news.substring(strlen*2,strlen*3);
        String s4=news.substring(strlen*3,news.length());


        tv2.setText(s1+"\n\n\n"+s2+"\n\n\n"+s3+"\n\n\n"+s4);

*/
        int counter=0;
        int ind1=0;
        while(ind1!=-1)
     {
         ind1=news.indexOf("@#$",counter);

        if(ind1!=-1)
        {
            tv2.setText(tv2.getText()+news.substring(counter,ind1)+"\n\n\n");
           //tv2.setText(tv2.getText()+"counter="+counter+"ind1="+ind1);

        }
        else
            break;
         counter=ind1+3;
     }
        tv2.setText(tv2.getText()+news.substring(counter,news.length())+"\n\n\n");


        String link2 = "https://zsupsecurity.000webhostapp.com/secure/admin/" + st.nextToken();


        new DownloadImageFromInternet1(iv,context)
                .execute(link2);
        if(progressDialog!=null&&iv!=null)
        {
            progressDialog.dismiss();
        }


    }
}

 class DownloadImageFromInternet1 extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;

    public DownloadImageFromInternet1(ImageView imageView,Context context) {
        this.imageView = imageView;
        Toast.makeText(context, "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
    }

    protected Bitmap doInBackground(String... urls) {

        String imageURL = urls[0];
        Bitmap bimage = null;
        try {
            InputStream in = new URL(imageURL).openStream();
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






