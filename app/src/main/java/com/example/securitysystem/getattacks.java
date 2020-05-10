package com.example.securitysystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.StringTokenizer;

//import com.squareup.picasso.Picasso;

public class getattacks extends AsyncTask<String , Void , String> {
    Context context;
    AppCompatActivity appCompatActivity;
    String ans="";
    ArrayList<String> attacknamelist = new ArrayList<String>();
    ArrayList<String> descriptionlist = new ArrayList<String>();
    ArrayList<String> contentlist = new ArrayList<String>();
    ArrayList<String> imagelist = new ArrayList<String>();

    ArrayList<String> preventionlist = new ArrayList<String>();
    ArrayList<String> datelist = new ArrayList<String>();

    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    public getattacks(Context context, RecyclerView recyclerView, ProgressDialog progressDialog, AppCompatActivity appCompatActivity) {
        this.context = context;
        this.recyclerView = recyclerView;

        this.progressDialog = progressDialog;
        this.appCompatActivity = appCompatActivity;


    }


    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... arg0) {
        try {

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context.getApplicationContext(), LinearLayoutManager.VERTICAL, true);
            recyclerView.setLayoutManager(linearLayoutManager);

            String link = "https://zsupsecurity.000webhostapp.com/secure/admin/returnattacks.php";

            URL url = new URL(link);
            URLConnection conn = url.openConnection();


            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder(10000);
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

        StringTokenizer st = new StringTokenizer(result, "%");
        int z = st.countTokens();
        int i;
        for (i = 0; i < z; i++) {
            StringTokenizer st1 = new StringTokenizer(st.nextToken(), "&");

            attacknamelist.add(st1.nextToken());
            descriptionlist.add(st1.nextToken());
            contentlist.add(st1.nextToken());
            imagelist.add(st1.nextToken());
            preventionlist.add(st1.nextToken());
            datelist.add(st1.nextToken());


        }
        i = i - 1;
        if (imagelist.get(i) != "") {
            progressDialog.dismiss();
        }


        customadapterforallattack ca = new customadapterforallattack(context.getApplicationContext(), attacknamelist,descriptionlist,contentlist,imagelist,preventionlist,datelist, ans, appCompatActivity, progressDialog);
        recyclerView.setAdapter(ca);


    }

}


