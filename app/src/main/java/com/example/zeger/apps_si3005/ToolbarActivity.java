package com.example.zeger.apps_si3005;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.example.zeger.apps_si3005.R;
import com.example.zeger.apps_si3005.database.MyDBHelper;
import com.example.zeger.apps_si3005.entity.Mahasiswa;

import java.util.List;

/**
 * Created by zeger on 18/03/17.
 */

public class ToolbarActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Nama Aplikasi");
        toolbar.setSubtitle("Ini Subtitle");

        // pake buat icon sebelah kiri
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        MyDBHelper dbHelper = new MyDBHelper(this);

        List<Mahasiswa> listMhs = dbHelper.select();

        for(Mahasiswa mhs:listMhs){
            Log.d("DATABASE","id="+mhs.getId());
            Log.d("DATABASE","nim="+mhs.getNim());
            Log.d("DATABASE","nama="+mhs.getNama());
        }



        //Log.d("DATABASE",""+dbHelper.insert("113060038","Komang"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        /*
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //while enter
                //adapter.search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //while enter
                return false;
            }
        });

        */
        return super.onCreateOptionsMenu(menu);
    }
}
