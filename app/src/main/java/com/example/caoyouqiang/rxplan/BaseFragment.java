package com.example.caoyouqiang.rxplan;

import android.support.v4.app.Fragment;

import com.squareup.leakcanary.RefWatcher;

/**
 * Created by caoyouqiang on 18-3-19.
 */

public class BaseFragment extends Fragment{
	@Override
	public void onDestroy() {
		super.onDestroy();

		RefWatcher refWatcher = MyApplication.getRefWatcher();
		refWatcher.watch(this);
	}
}
