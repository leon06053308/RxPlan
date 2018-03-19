package com.example.caoyouqiang.rxplan;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by caoyouqiang on 18-3-19.
 */

public class MyApplication extends Application {
	private static MyApplication mInstance;
	private RefWatcher mRefWatcher;

	public static MyApplication get(){
		return mInstance;
	}

	public static RefWatcher getRefWatcher(){
		return MyApplication.get().mRefWatcher;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		mInstance = (MyApplication)getApplicationContext();
		mRefWatcher = LeakCanary.install(this);
	}
}
