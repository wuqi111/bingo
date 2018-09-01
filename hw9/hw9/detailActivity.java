package com.example.wuqi.hw9;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;

public class detailActivity extends AppCompatActivity {

    private static final String TAG="MainActivity";

    private SectionPageAdapter mSectionPageAdapter;
    private ViewPager mViewPager;

   String twitter_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Toolbar toolbar=(Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        Intent intent= getIntent();
        String namefortitle=intent.getStringExtra("name_pup");
        String addressinfo=intent.getStringExtra("formatted_address");
        String website=intent.getStringExtra("website");
        toolbar.setTitle(namefortitle);

        twitter_url="https://twitter.com/intent/tweet?text=Check out"+namefortitle+"located at"+addressinfo+"Website:"+website+"&hashtags=TravelAndEntertainmentSearch";


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });



        mSectionPageAdapter=new SectionPageAdapter(getSupportFragmentManager());

        mViewPager=(ViewPager) findViewById(R.id.containerdetail);
        setupViewPager(mViewPager);

        TabLayout tabLayout=(TabLayout) findViewById(R.id.tabsdetail);
        tabLayout.setupWithViewPager(mViewPager);
////
                tabLayout.getTabAt(0).setIcon(R.drawable.info_outline);
                tabLayout.getTabAt(1).setIcon(R.drawable.photos);
                tabLayout.getTabAt(2).setIcon(R.drawable.maps);
                tabLayout.getTabAt(3).setIcon(R.drawable.review);



    }

    private void setupViewPager(ViewPager viewPager){
        SectionPageAdapter adapter=new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new INFO(),"INFO");
        adapter.addFragment(new PHOTOS(),"PHOTOS");
        adapter.addFragment(new MAP(),"MAP");
        adapter.addFragment(new REVIEWS(),"REVIEWS");


        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        switch (id){
            case R.id.twitter:

                Uri uri = Uri.parse(twitter_url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                //startAction(new Intent(Intent.ACTION_VIEW, Uri.parse("www.baidu.com")));
                break;

            case R.id.heart11:
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}
