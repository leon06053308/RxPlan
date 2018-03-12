package com.example.caoyouqiang.rxplan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class BaseActivity extends AppCompatActivity {
	protected RecyclerView mBaseRv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);

		mBaseRv = findViewById(R.id.base_rv);
	}
}
