package com.hanjx.samplecode.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.hanjx.samplecode.R;
import com.hanjx.samplecode.utils.SystemUtils;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SystemUtils.resetAnimatorDurationScale();
        recyclerView = findViewById(R.id.recycler_view);
        FunctionListAdapter adapter = new FunctionListAdapter();
        adapter.setOnItemClickListener(activityClass -> startActivity(new Intent(MainActivity.this, activityClass)));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

}
