package com.example.caoyouqiang.rxplan.operationassist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.caoyouqiang.rxplan.BaseFragment;
import com.example.caoyouqiang.rxplan.R;

import java.sql.Time;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;

/**
 * Created by caoyouqiang on 18-3-19.
 */
/*
* 转换为时间Timed，默认时间单位为毫秒
* 将一个发射数据的Observable转换为发射那些数据发射时间间隔的Observable
* */
public class TimeIntervalFragment extends BaseFragment {
	@BindView(R.id.textView)
	TextView mTv;
	@BindView(R.id.btn_start)
	Button mStartBtn;
	Unbinder mUnbinder;
	private Observable<Timed<Integer>> mObservable;
	private Consumer<Timed<Integer>> mObserver;

	public TimeIntervalFragment(){

	}

	public static TimeIntervalFragment newInstance() {
		TimeIntervalFragment fragment = new TimeIntervalFragment();
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
			@Override
			public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
				for (int i=0; i<3; i++){
					emitter.onNext(i);
					Thread.sleep(1000);
				}
				emitter.onComplete();
			}
		}).timeInterval();

		mObserver = new Consumer<Timed<Integer>>() {
			@Override
			public void accept(Timed<Integer> integerTimed) throws Exception {
				StringBuilder stringBuilder = new StringBuilder(mTv.getText());
				stringBuilder.append("accept--" + integerTimed.value() + " time--" + integerTimed.time() + "\n");
				mTv.setText(stringBuilder);
			}

		};
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.creater_fragment_layout, container, false);
		mUnbinder = ButterKnife.bind(this, view);
		mStartBtn.setText("TimeInterval");
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
		mObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(mObserver);
	}
}
