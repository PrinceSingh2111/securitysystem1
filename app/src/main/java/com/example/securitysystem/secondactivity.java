package com.example.securitysystem;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.support.v7.widget.Toolbar;

public class secondactivity extends AppCompatActivity {
    DrawerLayout dl;
    FrameLayout fl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondactivity);

        Toolbar  tb1=(Toolbar)findViewById(R.id.tb12);
        setSupportActionBar(tb1);
        fl=(FrameLayout)findViewById(R.id.frame);
        tb1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl.openDrawer(Gravity.LEFT);
            }
        });
        Fragment frag=new homefragment();

        FragmentTransaction fm=getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.frame,frag);
        fm.commit();
        setNavigationDrawer();

    }
    public void setNavigationDrawer()
    {
        dl=(DrawerLayout)findViewById(R.id.dl);
        NavigationView nv=(NavigationView)findViewById(R.id.navigation);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                Fragment frag=null;
                switch(menuItem.getItemId())
                {

                    case R.id.it1:frag=new BlankFragment();
                        break;

                    case R.id.it2:frag=new BlankFragment2();
                        break;
                    case R.id.it3:frag=new BlankFragment3();
                        break;
                    case R.id.it4:frag=new BlankFragment4();
                        break;
                    case R.id.it5:frag=new BlankFragment5();
                        break;
                    case R.id.it6:frag=new BlankFragment6();
                        break;
                    case R.id.it7:frag=new BlankFragment7();
                        break;
                    case R.id.it8:frag=new BlankFragment8();
                        break;

                }
                FragmentTransaction fm=getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frame,frag);
                fm.commit();
                dl.closeDrawers();
                return true;
            }
        });


    }
}
