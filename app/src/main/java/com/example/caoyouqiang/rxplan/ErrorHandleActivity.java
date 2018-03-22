package com.example.caoyouqiang.rxplan;

import android.os.Bundle;
import android.view.View;

import com.example.caoyouqiang.rxplan.errorhandle.CatchFragment;
import com.example.caoyouqiang.rxplan.errorhandle.RetryFragment;
import com.example.caoyouqiang.rxplan.operationgroup.ATWFragment;
import com.example.caoyouqiang.rxplan.operationgroup.CombineLatestFragment;
import com.example.caoyouqiang.rxplan.operationgroup.JoinFragment;
import com.example.caoyouqiang.rxplan.operationgroup.MergeFragment;
import com.example.caoyouqiang.rxplan.operationgroup.StartWithFragment;
import com.example.caoyouqiang.rxplan.operationgroup.SwitchFragment;
import com.example.caoyouqiang.rxplan.operationgroup.ZipFragment;

public class ErrorHandleActivity extends BaseActivity {

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
				mInnerFragment = CatchFragment.newInstance();
				break;
			case 1:
				mInnerFragment = RetryFragment.newInstance();
				break;
			default:
				break;
		}

		pushFragment(mInnerFragment);
	}
}
