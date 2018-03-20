package com.example.caoyouqiang.rxplan;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Range;
import android.view.LayoutInflater;
import android.view.View;

import com.example.caoyouqiang.rxplan.observablecreater.CreaterFragment;
import com.example.caoyouqiang.rxplan.observablecreater.DeferFragment;
import com.example.caoyouqiang.rxplan.observablecreater.ENTFragment;
import com.example.caoyouqiang.rxplan.observablecreater.FromFragment;
import com.example.caoyouqiang.rxplan.observablecreater.IntervalFragment;
import com.example.caoyouqiang.rxplan.observablecreater.JustFragment;
import com.example.caoyouqiang.rxplan.observablecreater.RangeFragment;
import com.example.caoyouqiang.rxplan.observablecreater.RepeatFragment;
import com.example.caoyouqiang.rxplan.observablecreater.StartFragment;
import com.example.caoyouqiang.rxplan.observablecreater.TimerFragment;

import java.util.Timer;

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
				break;
			case 3:
				mInnerFragment = FromFragment.newInstance();
				break;
			case 4:
				mInnerFragment = IntervalFragment.newInstance();
				break;
			case 5:
				mInnerFragment = JustFragment.newInstance();
				break;
			case 6:
				mInnerFragment = RangeFragment.newInstance();
				break;
			case 7:
				mInnerFragment = RepeatFragment.newInstance();
				break;
			case 8:
				mInnerFragment = StartFragment.newInstance();
				break;
			case 9:
				mInnerFragment = TimerFragment.newInstance();
				break;
			default:
				break;
		}

		pushFragment(mInnerFragment);
	}
}
