package com.example.caoyouqiang.rxplan;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.caoyouqiang.rxplan.observablecreater.CreaterFragment;
import com.example.caoyouqiang.rxplan.observablecreater.DeferFragment;
import com.example.caoyouqiang.rxplan.observablecreater.ENTFragment;

public class ObCreaterActivity extends BaseActivity {
	LayoutInflater mInflater;
	Fragment mInnerFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_op_assist);
		mInflater = LayoutInflater.from(this);
	}

	@Override
	public void onItemClick(View view, int position) {
		super.onItemClick(view, position);

		switch (position){
			case 0:
				mInnerFragment = CreaterFragment.newInstance();
				break;
			case 1:
				mInnerFragment = DeferFragment.newInstance();
				break;
			case 2:
				mInnerFragment = ENTFragment.newInstance();
			default:
				break;
		}

		pushFragment(mInnerFragment);
	}
}
