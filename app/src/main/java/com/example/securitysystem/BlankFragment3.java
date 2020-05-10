package com.example.securitysystem;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.securitysystem.dao.URLScan;
import com.example.securitysystem.dao.URLScanMetaData;
import com.example.securitysystem.dao.URLScanReport;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment3 extends Fragment {

TextView tv,tv2;
EditText et;
    ProgressDialog p;

    public BlankFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_blank_fragment3, container, false);
        tv=(TextView)view.findViewById(R.id.search);
        tv2=(TextView)view.findViewById(R.id.tv);
        et=(EditText)view.findViewById(R.id.et);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = et.getText().toString();
                AsyncTaskExample asyncTask = new AsyncTaskExample();
                String res= null;
                try {
                    res = asyncTask.execute(url).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                tv2.setText(res);



            }
        });


        return view;
    }


    class AsyncTaskExample extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(getActivity());
            p.setMessage("Please wait...We are Scanning");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            StringBuilder result1 =new StringBuilder();
            String result="";
            VirusTotalAPI virusTotal = VirusTotalAPI.configure("fb2e05c8648e12ee171b3091b86a56875376656546ee2dfe010251727568e9cc");
            try {



                URLScanMetaData scanURL = virusTotal.scanURL(new URL(url));
                result += "URL : " + scanURL.getUrl();
                result += "Resource : " + scanURL.getResource();
                result += "Scan Date : " + scanURL.getScanDate();
                result += "Scan Id : " + scanURL.getScanId();
                result += "Response Code : " + scanURL.getResponseCode();
                result += "Permalink : " + scanURL.getPermalink();
                result += "VerboseMessage : " + scanURL.getVerboseMsg();
                URLScanReport urlReport = virusTotal.getURLReport(url);
                result +="---SCAN META DATA---";

                result +="Response Code : " + urlReport.getResponseCode();
                result +="Resource : " + urlReport.getResource();
                result +="Scan ID : " + urlReport.getScanId();
                result +="Permalink : " + urlReport.getPermalink();
                result +="Scan Date : " + urlReport.getScanDate();
                result +="Positives : " + urlReport.getPositives();
                result +="Total : " + urlReport.getTotal();
                result +="File Scan Id : " + urlReport.getFilescanId();
                Map<String, URLScan> scans = urlReport.getScans();
                result +="---URL REPORT---";

                for (String scan : scans.keySet()) {
                    URLScan report = scans.get(scan);
                    String s = scan + "\t:" + report.getReport();
                    result = result+s+"\n";
                }

            }catch (Exception ex)
            {
                ex.printStackTrace();
            }



            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null) {
                p.hide();
                } else {
                p.show();
            }

        }
    }

}
