/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.securitysystem;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.securitysystem.dao.FileScanMetaData;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author BVR vigneshb1210@gmail.com
 */
public class FileScanner extends AppCompatActivity {
TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ipreport);
        tv=(TextView)findViewById(R.id.tv);
        FileScanner.AsyncTaskExample asyncTask = new AsyncTaskExample();
        String res= null;
 try {
            res = asyncTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tv.setText(res);


    }
    class AsyncTaskExample extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String s="";
            File file = new File("c://pattern1.java");
            VirusTotalAPI virusTotal = VirusTotalAPI.configure("YOUR API KEY");
            FileScanMetaData scanFile = null;
            try {
                scanFile = virusTotal.scanFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("---SCAN META DATA---");
            System.out.println("");
            System.out.println("MD5 : " + scanFile.getMD5());
            System.out.println("SH-1 : " + scanFile.getSHA1());
            System.out.println("SHA-256 : " + scanFile.getSHA256());
            System.out.println("Permalink : " + scanFile.getPermalink());
            System.out.println("Resource : " + scanFile.getResource());
            System.out.println("Scan Id : " + scanFile.getScanId());
            System.out.println("Response Code : " + scanFile.getResponseCode());
            System.out.println("Verbose Message : " + scanFile.getVerboseMessage());
            s=s+scanFile.getMD5()+" "+"/n"+scanFile.getSHA1()+" "+"/n"+scanFile.getSHA256()+" "+"/n"+scanFile.getPermalink()+" "+"/n"+scanFile.getResource()+" "+"/n"+scanFile.getScanId()+" "+"/n"+scanFile.getResponseCode()+" "+"/n"+scanFile.getVerboseMessage();
            return s;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }


}
