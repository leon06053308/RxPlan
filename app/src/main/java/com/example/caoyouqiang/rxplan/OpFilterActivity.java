package com.example.caoyouqiang.rxplan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.example.caoyouqiang.rxplan.operationfilter.DebounceFragment;
import com.example.caoyouqiang.rxplan.operationfilter.DistinctFragment;
import com.example.caoyouqiang.rxplan.operationfilter.ElementAtFragment;
import com.example.caoyouqiang.rxplan.operationfilter.FilterFragment;
import com.example.caoyouqiang.rxplan.operationfilter.FirstFragment;
import com.example.caoyouqiang.rxplan.operationfilter.IgnoreElementsFragment;
import com.example.caoyouqiang.rxplan.operationfilter.LastFragment;
import com.example.caoyouqiang.rxplan.operationfilter.SampleFragment;
import com.example.caoyouqiang.rxplan.operationfilter.SkipFragment;
import com.example.caoyouqiang.rxplan.operationfilter.SkipLastFragment;
import com.example.caoyouqiang.rxplan.operationfilter.TakeFragment;
import com.example.caoyouqiang.rxplan.operationfilter.TakeLastFragment;

public class OpFilterActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_op_assist);
	}

	@Override
	public void onItemClick(View view, int position) {
		super.onItemClick(view, position);

		switch (position){
			case 0:
				mInnerFragment = DebounceFragment.newInstance();
				break;
			case 1:
				mInnerFragment = DistinctFragment.newInstance();
				break;
			case 2:
				mInnerFragment = ElementAtFragment.newInstance();
				break;
			case 3:
				mInnerFragment = FilterFragment.newInstance();
				break;
			case 4:
				mInnerFragment = FirstFragment.newInstance();
				break;
			case 5:
				mInnerFragment = IgnoreElementsFragment.newInstance();
				break;
			case 6:
				mInnerFragment = LastFragment.newInstance();
				break;
			case 7:
				mInnerFragment = SampleFragment.newInstance();
				break;
			case 8:
				mInnerFragment = SkipFragment.newInstance();
				break;
			case 9:
				mInnerFragment = SkipLastFragment.newInstance();
				break;
			case 10:
				mInnerFragment = TakeFragment.newInstance();
				break;
			case 11:
				mInnerFragment = TakeLastFragment.newInstance();
				break;
			default:
				break;
		}

		pushFragment(mInnerFragment);
	}
}
