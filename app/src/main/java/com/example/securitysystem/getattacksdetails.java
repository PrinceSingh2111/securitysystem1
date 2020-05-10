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

public class getattacksdetails extends AsyncTask<String , Void , String> {
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
    TextView tv1, tv2, tv3;
    ImageView iv;

    public getattacksdetails(TextView tv1, TextView tv2, TextView tv3, ImageView iv, Context context, String s, ProgressDialog progressDialog, AppCompatActivity appCompatActivity) {
        this.context = context;
        this.s = s;
        this.tv1 = tv1;
        this.tv2 = tv2;
        this.tv3 = tv3;
        this.iv = iv;
        this.progressDialog = progressDialog;
        this.appCompatActivity = appCompatActivity;


    }


    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... arg0) {
        try {


            String link = "https://zsupsecurity.000webhostapp.com/secure/admin/returnattacksdetails.php";

            String data = URLEncoder.encode("attackname", "UTF-8") + "=" +
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
        tv2.setText(st.nextToken() + "");
        String link2 = "https://zsupsecurity.000webhostapp.com/secure/admin/" + st.nextToken();


        new DownloadImageFromInternet(iv,context)
                .execute(link2);
        if(progressDialog!=null&&iv!=null)
        {
            progressDialog.dismiss();
        }


        tv3.setText(st.nextToken()+"");

    }
}

    class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView,Context context) {
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






