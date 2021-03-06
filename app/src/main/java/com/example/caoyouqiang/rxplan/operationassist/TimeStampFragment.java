package com.example.caoyouqiang.rxplan.operationassist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.caoyouqiang.rxplan.BaseFragment;
import com.example.caoyouqiang.rxplan.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Timed;

/**
 * Created by caoyouqiang on 18-3-19.
 */

public class TimeStampFragment extends BaseFragment {
	@BindView(R.id.textView)
	TextView mTv;
	@BindView(R.id.btn_start)
	Button mStartBtn;
	Unbinder mUnbinder;
	private Observable<Timed<Integer>> mObservable;
	private Consumer<Timed<Integer>> mObserver;

	public TimeStampFragment(){

	}

	public static TimeStampFragment newInstance() {
		TimeStampFragment fragment = new TimeStampFragment();
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mObservable = Observable.just(1,2,3,4).timestamp();
		mObserver = new Consumer<Timed<Integer>>() {
			@Override
			public void accept(Timed<Integer> integerTimed) throws Exception {
				StringBuilder stringBuilder = new StringBuilder(mTv.getText());
				String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA)
						.format(new Date(integerTimed.time()));
				stringBuilder.append("accept--" + integerTimed.value() + " time--" + date + "\n");
				mTv.setText(stringBuilder);
			}

		};
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.creater_fragment_layout, container, false);
		mUnbinder = ButterKnife.bind(this, view);
		mStartBtn.setText("TimeStamp");
		mTv.setMovementMethod(ScrollingMovementMethod.getInstance());
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
		mObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(mObserver);
	}
}
