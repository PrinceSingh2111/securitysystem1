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

import com.example.securitysystem.dao.IPAddressReport;
import com.example.securitysystem.dao.IPAddressResolution;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author BVR vigneshb1210@gmail.com
 */

public class ipreport extends AppCompatActivity {
TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ipreport);
         tv=(TextView)findViewById(R.id.tv);
 ipreport.AsyncTaskExample asyncTask = new AsyncTaskExample();
        String res= null;

        String ip = "188.40.238.250";
        try {
            res = asyncTask.execute(ip).get();
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
            String url = strings[0];
            String s="";

            VirusTotalAPI virusTotal = VirusTotalAPI.configure("fb2e05c8648e12ee171b3091b86a56875376656546ee2dfe010251727568e9cc");
            IPAddressReport ipAddressReport = null;
            try {
                ipAddressReport = virusTotal.getIPAddressReport((Inet4Address) Inet4Address.getByName(url));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            IPAddressResolution[] resolutions = ipAddressReport.getResolutions();
            System.out.println("---IP ADDRESS REPORT---");

            System.out.println("");
            for (IPAddressResolution resolution : resolutions) {

                System.out.println("Hostname : " + resolution.getHostName());
                System.out.println("Resolved : " + resolution.getLastResolved());
                s=s+resolution.getHostName()+" "+resolution.getLastResolved()+" ";
            }

            System.out.println("Verbose Message : " + ipAddressReport.getVerboseMessage());
            System.out.println("Response Code : " + ipAddressReport.getResponseCode());
            System.out.println("Detected Communicating Samples : " + Arrays.toString(ipAddressReport.getDetectedCommunicatingSamples()));
            System.out.println("Detected Downloading Samples : " + Arrays.toString(ipAddressReport.getDetectedDownloadedSamples()));
            System.out.println("Undetected Communicating Samples : " + Arrays.toString(ipAddressReport.getUndetectedCommunicatingSamples()));
            System.out.println("Undetected Communicating Samples : " + Arrays.toString(ipAddressReport.getUndetectedDownloadedSamples()));
            System.out.println("Detected URLS : " + Arrays.toString(ipAddressReport.getDetectedUrls()));
            s=s+"/n"+ipAddressReport.getVerboseMessage()+" "+"/n"+ipAddressReport.getResponseCode()+" "+"/n"+ipAddressReport.getDetectedCommunicatingSamples()+" "+"/n"+ipAddressReport.getDetectedDownloadedSamples()+" "+"/n"+ipAddressReport.getUndetectedCommunicatingSamples()+" "+"/n"+ipAddressReport.getUndetectedDownloadedSamples()+" "+"/n"+ipAddressReport.getDetectedUrls();


            return s;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            }
    }


    }
