package com.example.caoyouqiang.rxplan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.caoyouqiang.rxplan.operationchange.BufferFragment;
import com.example.caoyouqiang.rxplan.operationchange.FlatMapFragment;
import com.example.caoyouqiang.rxplan.operationchange.GroupByFragment;
import com.example.caoyouqiang.rxplan.operationchange.MapFragment;
import com.example.caoyouqiang.rxplan.operationchange.ScanFragment;
import com.example.caoyouqiang.rxplan.operationchange.WindowFragment;
import com.example.caoyouqiang.rxplan.operationgroup.ATWFragment;
import com.example.caoyouqiang.rxplan.operationgroup.CombineLatestFragment;
import com.example.caoyouqiang.rxplan.operationgroup.JoinFragment;
import com.example.caoyouqiang.rxplan.operationgroup.MergeFragment;
import com.example.caoyouqiang.rxplan.operationgroup.StartWithFragment;
import com.example.caoyouqiang.rxplan.operationgroup.SwitchFragment;
import com.example.caoyouqiang.rxplan.operationgroup.ZipFragment;

public class OpGroupActivity extends BaseActivity {

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
				mInnerFragment = ATWFragment.newInstance();
				break;
			case 1:
				mInnerFragment = CombineLatestFragment.newInstance();
				break;
			case 2:
				mInnerFragment = JoinFragment.newInstance();
				break;
			case 3:
				mInnerFragment = MergeFragment.newInstance();
				break;
			case 4:
				mInnerFragment = StartWithFragment.newInstance();
				break;
			case 5:
				mInnerFragment = SwitchFragment.newInstance();
				break;
			case 6:
				mInnerFragment = ZipFragment.newInstance();
				break;
			default:
				break;
		}

		pushFragment(mInnerFragment);
	}
}
