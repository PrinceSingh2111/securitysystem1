package com.example.securitysystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

//import com.squareup.picasso.Picasso;

public class getvuldetailsbypackagename extends AsyncTask<String , Void , String> {
    Context context;
    TextView tv;
    ImageView iv1;
    String s;
    ListView lv;
    AppCompatActivity appCompatActivity;
    ProgressDialog progressDialog;
    ArrayList<String> idlist = new ArrayList<String>();
    ArrayList<String> applist = new ArrayList<String>();
    ArrayList<String> vulidlist = new ArrayList<String>();
    ArrayList<String> sumlist = new ArrayList<String>();
    ArrayList<String> severitylist = new ArrayList<String>();
    ArrayList<String> datelist = new ArrayList<String>();

    RecyclerView recyclerView;
    public getvuldetailsbypackagename(Context context, String s, TextView tv, ListView lv, AppCompatActivity appCompatActivity,ProgressDialog progressDialog) {
        this.context = context;
        this.s=s;
        this.progressDialog=progressDialog;
        this.tv=tv;
        this.lv=lv;
        this.appCompatActivity = appCompatActivity;


    }


    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... arg0) {

            String link = "https://zsupsecurity.000webhostapp.com/secure/admin/returnvulnerabilitybypackagename.php";
            try {
                String data = URLEncoder.encode("pname", "UTF-8") + "=" +
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
        if(result.equals("Credentials not  ok"))
        {
            this.tv.setText("App Not Available");

            progressDialog.dismiss();
        }
        else {

            StringTokenizer st = new StringTokenizer(result, "%");
            int z = st.countTokens();
            int i;
            for (i = 0; i < z; i++) {
                StringTokenizer st1 = new StringTokenizer(st.nextToken(), "&");

                idlist.add(st1.nextToken());
                applist.add(st1.nextToken());
                vulidlist.add(st1.nextToken());
                sumlist.add(st1.nextToken());
                severitylist.add(st1.nextToken());
                datelist.add(st1.nextToken());


            }

        }
            List<HashMap<String,String>> al1= new ArrayList<HashMap<String,String>>();

            for(int j=0;j<applist.size();j++){

                HashMap<String,String> hm = new HashMap<String,String>();
                hm.put("id" ,"Id : "+idlist.get(j)+"");
                hm.put("app" ,"App Name : "+applist.get(j)+"");
                hm.put("vul" ,"Vul Id : "+vulidlist.get(j)+"");
                hm.put("sum" ,"Summary : "+sumlist.get(j)+"");
                hm.put("sev" ,"Severity : "+severitylist.get(j)+"");
                hm.put("date" ,"Date : "+datelist.get(j)+"");

                al1.add(hm);

            }
            this.tv.setText(applist.get(0)+"");
            progressDialog.dismiss();

            String[] from = {"app","vul","sum","sev","date" };
            int[] to = {R.id.tv2,R.id.tv3,R.id.tv4,R.id.tv5,R.id.tv6};
            SimpleAdapter adapter = new SimpleAdapter(context, al1, R.layout.vul_layout,from , to){
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {

                    View view1=super.getView(position, convertView, parent);
                    LinearLayout ll=(LinearLayout)view1.findViewById(R.id.ll);
                    ll.setPadding(0,40,0,0);
                    TextView text2 = (TextView) view1.findViewById(R.id.tv2);
                    TextView text3 = (TextView) view1.findViewById(R.id.tv3);
                    TextView text4 = (TextView) view1.findViewById(R.id.tv4);
                    TextView text5 = (TextView) view1.findViewById(R.id.tv5);
                    TextView text6 = (TextView) view1.findViewById(R.id.tv6);

                    text2.setTextColor(Color.WHITE);
                    text3.setTextColor(Color.WHITE);
                    text4.setTextColor(Color.WHITE);
                    text5.setTextColor(Color.WHITE);
                    text6.setTextColor(Color.WHITE);


                    return view1;
                }
            };

            lv.setAdapter(adapter);



        }



}
