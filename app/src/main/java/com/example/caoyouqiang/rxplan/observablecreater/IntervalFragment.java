package com.example.caoyouqiang.rxplan.observablecreater;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.caoyouqiang.rxplan.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by caoyouqiang on 18-3-19.
 */
/*
* 创建一个按固定时间间隔发射整数序列的Observable
* */
public class IntervalFragment extends Fragment {
	@BindView(R.id.textView)
	TextView mTv;
	@BindView(R.id.btn_start)
	Button mStartBtn;
	Unbinder mUnbinder;
	private Observable<Long> mIntervalObservable;
	private Consumer<Long> mObserver;

	public IntervalFragment(){

	}

	public static IntervalFragment newInstance() {
		IntervalFragment fragment = new IntervalFragment();
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		* 初始值5,依次+1发送10个数字,第一个延迟3秒发送,以后依次间隔2秒发送
		* */
		mIntervalObservable = Observable.intervalRange(5, 10, 3, 2, TimeUnit.SECONDS);
		mObserver = new Consumer<Long>() {
			@Override
			public void accept(Long aLong) throws Exception {
				StringBuilder stringBuilder = new StringBuilder(mTv.getText());
				stringBuilder.append("accept--" + aLong + "\n");
				mTv.setText(stringBuilder);
			}
		};
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.creater_fragment_layout, container, false);
		mUnbinder = ButterKnife.bind(this, view);
		mStartBtn.setText("Interval");
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mUnbinder.unbind();

	}

	@OnClick(R.id.btn_start)
	void startClick(){
		mIntervalObservable.observeOn(AndroidSchedulers.mainThread())
				.subscribe(mObserver);
	}
}
