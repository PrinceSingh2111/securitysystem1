package com.example.securitysystem;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class permissions extends Fragment {
    ListView lv3;
    ArrayList<String> al;
    Button bt1;
    List<HashMap<String,String>> al1;
    TextView tv,tv2,tv3,tv4;
    ImageView iv,iv2;
    String s;
    int arrr1[];
    int arrr2[];
    String cv,pv;


    public permissions() {

        // Required empty public constructor
    }
    public String getpackagename()
    {

return this.s;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       final View view=inflater.inflate(R.layout.fragment_permissions, container, false);
        // Inflate the layout for this fragment

        bt1=(Button)view.findViewById(R.id.checkvuln);
        tv=(TextView)view.findViewById(R.id.appname);
        tv2=(TextView)view.findViewById(R.id.appversion);
        tv3=(TextView)view.findViewById(R.id.source);

        lv3=(ListView)view.findViewById(R.id.permissnoncr);

        iv=(ImageView)view.findViewById(R.id.iv);
final Bundle b = this.getArguments();

        String appname="";

        Drawable dw = null;


            if(b!=null)
        {
            s = b.getString("key");

            appname = b.getString("appname");
        }

        try {
            dw=getContext().getApplicationContext().getPackageManager().getApplicationIcon(s);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        final String finalAppname = appname;
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment frag=new BlankFragment10();
                Bundle bundle = new Bundle();
                bundle.putString("key", b.getString("key"));
                frag.setArguments(bundle);

                FragmentTransaction fm=getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frame,frag);
                fm.commit();


                //   new getvuldetails(getActivity().getApplicationContext(),s,et,lv,(AppCompatActivity) getActivity()).execute();


            }
        });

        if(!appname.equals(""))
        {

            tv.setText(appname);
        }


        try {
            PackageInfo packageInfo=getContext().getApplicationContext().getPackageManager().getPackageInfo(s,0);

            tv2.setText(packageInfo.versionName);
            GetVersionCode gv=new GetVersionCode(s);

            cv=packageInfo.versionName;
            pv=gv.execute().get();
            if(cv.equals(pv))
            {
                tv2.setText("Latest Version");


            }
            else
            {
                tv2.setText("Update Needed");
                tv2.setTextColor(Color.RED);
                Drawable d=getResources().getDrawable(R.drawable.buttonbackborderwhite);
                tv2.setBackground(d);
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        List<String> validinst=new ArrayList<>(Arrays.asList("com.android.vending","com.google.android.feedback"));
        String installer=getContext().getApplicationContext().getPackageManager().getInstallerPackageName(s);
        if(validinst.contains(installer))
        {  //tv4.setBackgroundResource(R.drawable.playstorelogo);
           tv3.setText("Installed From Play Store");
        }
        else
        {
            tv3.setText("Not Installed From Play Store");

        }
        iv.setImageDrawable(dw);
        arrr1= new int[]{R.drawable.ic_error_outline_black_24dp};
        arrr2= new int[]{R.drawable.ic_done_black_24dp};
        al=new ArrayList<>();
        al1 = new ArrayList<HashMap<String,String>>();

        getPermissionsByPackageName(s);
        for(int i=0;i<al.size();i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("name" ,al.get(i));
            if(al.get(i).equals("android.permission.INTERNET")|al.get(i).equals("android.permission.CAMERA")|al.get(i).equals("android.permission.READ_CONTACTS")|al.get(i).equals("android.permission.WRITE_CONTACTS")|al.get(i).equals("android.permission.RECORD_AUDIO")|al.get(i).equals("android.permission.READ_EXTERNAL_STORAGE")|al.get(i).equals("android.permission.WRITE_EXTERNAL_STORAGE")|al.get(i).equals("android.permission.SEND_SMS")|al.get(i).equals("android.permission.READ_SMS"))
            {

                hm.put("image" , arrr1[0]+"");
            }
            else
            {
                hm.put("image" , arrr2[0]+"");

            }

            al1.add(hm);

           }
        String[] from = { "name","image" };
        int[] to = {R.id.txt,R.id.flag};

        SimpleAdapter adapter = new SimpleAdapter(getContext().getApplicationContext(), al1, R.layout.listview_layout,from , to){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view1=super.getView(position, convertView, parent);
                TextView text = (TextView) view1.findViewById(R.id.txt);
                text.setTextColor(Color.WHITE);
                String t=text.getText()+"";
                int ind=t.indexOf("permission");
                if(t.length()>=40)
                {
                    int ind1=t.lastIndexOf(".");
                    text.setText(t.substring(ind1+1,t.length()));

                }
                if(ind!=-1) {
                    int ind1 = ind + 11;
                    String t1 = t.substring(ind1, t.length());
                    text.setText(t1);
                }

                return view1;
            }
        };

        lv3.setAdapter(adapter);


        lv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(al.get(position).equals("android.permission.INTERNET")|al.get(position).equals("android.permission.CAMERA")|al.get(position).equals("android.permission.READ_CONTACTS")|al.get(position).equals("android.permission.WRITE_CONTACTS")|al.get(position).equals("android.permission.RECORD_AUDIO")|al.get(position).equals("android.permission.READ_EXTERNAL_STORAGE")|al.get(position).equals("android.permission.WRITE_EXTERNAL_STORAGE")|al.get(position).equals("android.permission.SEND_SMS")|al.get(position).equals("android.permission.READ_SMS"))
                {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", s, null);
                    intent.setData(uri);
                    startActivity(intent);

                }

            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tv2.getText().equals("Update Needed")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + s)));

                }
                }
        });
        return view;
    }


    public void getPermissionsByPackageName(String packageName)
    {

        StringBuilder builder=new StringBuilder();
        try{
            PackageInfo packageInfo=getContext().getApplicationContext().getPackageManager().getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            for (int i=0;i<packageInfo.requestedPermissions.length;i++)
            {
                if((packageInfo.requestedPermissionsFlags[i] & PackageInfo.REQUESTED_PERMISSION_GRANTED)!=0) {

                    if(packageInfo.requestedPermissions[i].equals("android.permission.INTERNET")|packageInfo.requestedPermissions[i].equals("android.permission.CAMERA")|packageInfo.requestedPermissions[i].equals("android.permission.READ_CONTACTS")|packageInfo.requestedPermissions[i].equals("android.permission.WRITE_CONTACTS")|packageInfo.requestedPermissions[i].equals("android.permission.RECORD_AUDIO")|packageInfo.requestedPermissions[i].equals("android.permission.READ_EXTERNAL_STORAGE")|packageInfo.requestedPermissions[i].equals("android.permission.WRITE_EXTERNAL_STORAGE")|packageInfo.requestedPermissions[i].equals("android.permission.SEND_SMS")|packageInfo.requestedPermissions[i].equals("android.permission.READ_SMS"))
                    {

                        al.add(packageInfo.requestedPermissions[i]);
                    }

                }
            }
            for (int i=0;i<packageInfo.requestedPermissions.length;i++)
            {
                if((packageInfo.requestedPermissionsFlags[i] & PackageInfo.REQUESTED_PERMISSION_GRANTED)!=0) {

                    if(packageInfo.requestedPermissions[i].equals("android.permission.INTERNET")|packageInfo.requestedPermissions[i].equals("android.permission.CAMERA")|packageInfo.requestedPermissions[i].equals("android.permission.READ_CONTACTS")|packageInfo.requestedPermissions[i].equals("android.permission.WRITE_CONTACTS")|packageInfo.requestedPermissions[i].equals("android.permission.RECORD_AUDIO")|packageInfo.requestedPermissions[i].equals("android.permission.READ_EXTERNAL_STORAGE")|packageInfo.requestedPermissions[i].equals("android.permission.WRITE_EXTERNAL_STORAGE")|packageInfo.requestedPermissions[i].equals("android.permission.SEND_SMS")|packageInfo.requestedPermissions[i].equals("android.permission.READ_SMS"))
                    {

                        }
                    else
                    {
                        al.add(packageInfo.requestedPermissions[i]);

                    }

                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
class GetVersionCode extends AsyncTask<Void, String, String> {
    String pn;
    public GetVersionCode(String pnn) {
        this.pn=pnn;
    }
    public String playStoreVersion = "0.0.0.0";

    OkHttpClient client = new OkHttpClient();

    private String execute(String url) throws IOException {
        okhttp3.Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {
            String html = execute("https://play.google.com/store/apps/details?id=" + pn + "&hl=en");
            Pattern blockPattern = Pattern.compile("Current Version.*([0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+)</span>");
            Matcher blockMatch = blockPattern.matcher(html);
            if(blockMatch.find()) {
                Pattern versionPattern = Pattern.compile("[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+");
                Matcher versionMatch = versionPattern.matcher(blockMatch.group(0));
                if(versionMatch.find()) {
                    playStoreVersion = versionMatch.group(0);
                }

            }
            if(playStoreVersion.equals("0.0.0.0"))
            {

                String html1 = execute("https://play.google.com/store/apps/details?id=" + pn + "&hl=en");
                Pattern blockPattern1 = Pattern.compile("Current Version.*([0-9]+\\.[0-9]+\\.[0-9]+)</span>");
                Matcher blockMatch1 = blockPattern1.matcher(html1);
                if(blockMatch1.find()) {
                    Pattern versionPattern1 = Pattern.compile("[0-9]+\\.[0-9]+\\.[0-9]+");
                    Matcher versionMatch1 = versionPattern1.matcher(blockMatch1.group(0));
                    if(versionMatch1.find()) {
                        playStoreVersion = versionMatch1.group(0);
                    }

                }



            }
            if(playStoreVersion.equals("0.0.0"))
            {

                String html2 = execute("https://play.google.com/store/apps/details?id=" + pn + "&hl=en");
                Pattern blockPattern2 = Pattern.compile("Current Version.*([0-9]+\\.[0-9]+)</span>");
                Matcher blockMatch2 = blockPattern2.matcher(html2);
                if(blockMatch2.find()) {
                    Pattern versionPattern2 = Pattern.compile("[0-9]+\\.[0-9]+");
                    Matcher versionMatch2 = versionPattern2.matcher(blockMatch2.group(0));
                    if(versionMatch2.find()) {
                        playStoreVersion = versionMatch2.group(0);
                    }

                }



            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return playStoreVersion;


    }
    }

