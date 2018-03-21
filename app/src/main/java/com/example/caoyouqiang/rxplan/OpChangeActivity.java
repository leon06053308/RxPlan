package com.example.caoyouqiang.rxplan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.caoyouqiang.rxplan.operationchange.BufferFragment;
import com.example.caoyouqiang.rxplan.operationchange.FlatMapFragment;
import com.example.caoyouqiang.rxplan.operationchange.GroupByFragment;
import com.example.caoyouqiang.rxplan.operationchange.MapFragment;
import com.example.caoyouqiang.rxplan.operationchange.ScanFragment;
import com.example.caoyouqiang.rxplan.operationchange.WindowFragment;

import java.util.concurrent.FutureTask;

public class OpChangeActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_op_assist);
		Log.d("Leo", "OpChangeActivity onCreate...");

	}

	@Override
	public void onItemClick(View view, int position) {
		super.onItemClick(view, position);

		switch (position){
			case 0:
				mInnerFragment = BufferFragment.newInstance();
				break;
			case 1:
				mInnerFragment = FlatMapFragment.newInstance();
				break;
			case 2:
				mInnerFragment = GroupByFragment.newInstance();
				break;
			case 3:
				mInnerFragment = MapFragment.newInstance();
				break;
			case 4:
				mInnerFragment = ScanFragment.newInstance();
				break;
			case 5:
				mInnerFragment = WindowFragment.newInstance();
				break;
			default:
				break;
		}

		pushFragment(mInnerFragment);
	}
}
