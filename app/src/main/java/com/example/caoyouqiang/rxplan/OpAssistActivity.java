package com.example.caoyouqiang.rxplan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.caoyouqiang.rxplan.operationassist.DefaultFragment;
import com.example.caoyouqiang.rxplan.operationassist.DelayFragment;
import com.example.caoyouqiang.rxplan.operationassist.DoFragment;
import com.example.caoyouqiang.rxplan.operationassist.MDFragment;
import com.example.caoyouqiang.rxplan.operationassist.SerializeFragment;
import com.example.caoyouqiang.rxplan.operationassist.TimeIntervalFragment;
import com.example.caoyouqiang.rxplan.operationassist.TimeOutFragment;
import com.example.caoyouqiang.rxplan.operationassist.TimeStampFragment;
import com.example.caoyouqiang.rxplan.operationassist.UsingFragment;
import com.example.caoyouqiang.rxplan.operationgroup.ATWFragment;
import com.example.caoyouqiang.rxplan.operationgroup.CombineLatestFragment;
import com.example.caoyouqiang.rxplan.operationgroup.JoinFragment;
import com.example.caoyouqiang.rxplan.operationgroup.MergeFragment;
import com.example.caoyouqiang.rxplan.operationgroup.StartWithFragment;
import com.example.caoyouqiang.rxplan.operationgroup.SwitchFragment;
import com.example.caoyouqiang.rxplan.operationgroup.ZipFragment;

public class OpAssistActivity extends BaseActivity {

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
				mInnerFragment = DelayFragment.newInstance();
				break;
			case 1:
				mInnerFragment = DoFragment.newInstance();
				break;
			case 2:
				mInnerFragment = MDFragment.newInstance();
				break;
			case 3:
				mInnerFragment = DefaultFragment.newInstance();
				break;
			case 4:
				mInnerFragment = SerializeFragment.newInstance();
				break;
			case 5:
				mInnerFragment = DefaultFragment.newInstance();
				break;
			case 6:
				mInnerFragment = DefaultFragment.newInstance();
				break;
			case 7:
				mInnerFragment = TimeIntervalFragment.newInstance();
				break;
			case 8:
				mInnerFragment = TimeOutFragment.newInstance();
				break;
			case 9:
				mInnerFragment = TimeStampFragment.newInstance();
				break;
			case 10:
				mInnerFragment = UsingFragment.newInstance();
				break;
			default:
				break;
		}

		pushFragment(mInnerFragment);
	}
}
