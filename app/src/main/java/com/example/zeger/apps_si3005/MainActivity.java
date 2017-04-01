package com.example.zeger.apps_si3005;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.zeger.apps_si3005.adapter.AdapterListView;
import com.example.zeger.apps_si3005.database.MyDBHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView)findViewById(R.id.contact_listview);

        AdapterListView adapterListView = new AdapterListView(this);

        listView.setAdapter(adapterListView);
    }
}
