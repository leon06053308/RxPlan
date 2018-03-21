package com.example.caoyouqiang.rxplan.operationfilter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.caoyouqiang.rxplan.BaseFragment;
import com.example.caoyouqiang.rxplan.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by caoyouqiang on 18-3-19.
 */

public class DebounceFragment extends BaseFragment {
	@BindView(R.id.textView)
	TextView mTv;
	@BindView(R.id.btn_start)
	Button mStartBtn;
	Unbinder mUnbinder;
	private Observable<String> mObservable;
	private Observer<String> mObserver;

	public DebounceFragment(){

	}

	public static DebounceFragment newInstance() {
		DebounceFragment fragment = new DebounceFragment();
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mObservable = Observable.create(new ObservableOnSubscribe<String>() {
			@Override
			public void subscribe(ObservableEmitter<String> emitter) throws Exception {
				emitter.onNext("a");
				Thread.sleep(300);
				emitter.onNext("b");
				Thread.sleep(400);
				emitter.onNext("c");
				Thread.sleep(500);
				emitter.onNext("d");
				Thread.sleep(450);
				emitter.onNext("e");
				Thread.sleep(200);
				emitter.onNext("f");
				Thread.sleep(100);
				emitter.onComplete();
			}
		}).debounce(350, TimeUnit.MILLISECONDS);

		mObserver = new Observer<String>() {
			@Override
			public void onSubscribe(Disposable d) {
				StringBuilder stringBuilder = new StringBuilder(mTv.getText());
				stringBuilder.append("onSubscribe..." + "\n");
				mTv.setText(stringBuilder);
			}

			@Override
			public void onNext(String s) {
				StringBuilder stringBuilder = new StringBuilder(mTv.getText());
				stringBuilder.append("onNext--" + s + "\n");
				mTv.setText(stringBuilder);
			}

			@Override
			public void onError(Throwable e) {
				StringBuilder stringBuilder = new StringBuilder(mTv.getText());
				stringBuilder.append("onError:" + e.getMessage() + "\n");
				mTv.setText(stringBuilder);
			}

			@Override
			public void onComplete() {
				StringBuilder stringBuilder = new StringBuilder(mTv.getText());
				stringBuilder.append("onComplete..." + "\n");
				mTv.setText(stringBuilder);
			}
		};
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.creater_fragment_layout, container, false);
		mUnbinder = ButterKnife.bind(this, view);
		mStartBtn.setText("Debounce");
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
