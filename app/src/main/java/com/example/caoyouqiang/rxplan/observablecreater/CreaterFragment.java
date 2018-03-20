package com.example.caoyouqiang.rxplan.observablecreater;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.caoyouqiang.rxplan.BaseFragment;
import com.example.caoyouqiang.rxplan.R;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CreaterFragment extends BaseFragment implements View.OnClickListener{
	private Observable<Integer> mObservable;
	private Observer<Integer> mObserver;
	TextView mTv;
	Button mStart;
	CompositeDisposable mComDisposable;

	public CreaterFragment() {
	}

	public static CreaterFragment newInstance() {
		CreaterFragment fragment = new CreaterFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.creater_fragment_layout, container, false);
		mTv = view.findViewById(R.id.textView);
		mStart = view.findViewById(R.id.btn_start);
		mStart.setOnClickListener(this);
		mStart.setText("Create");
		mComDisposable = new CompositeDisposable();
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();

		mObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
			@Override
			public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
				emitter.onNext(1);
				SystemClock.sleep(2000);
				emitter.onNext(2);
				SystemClock.sleep(3000);
				emitter.onNext(3);
				SystemClock.sleep(5000);
				emitter.onComplete();
			}
		});

		mObserver = new Observer<Integer>() {
			@Override
			public void onSubscribe(Disposable d) {
				mComDisposable.add(d);
			}

			@Override
			public void onNext(Integer integer) {
				StringBuilder stringBuilder = new StringBuilder(mTv.getText());
				stringBuilder.append("onNext--" + integer + "\n");
				mTv.setText(stringBuilder);
			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onComplete() {
				StringBuilder stringBuilder = new StringBuilder(mTv.getText());
				stringBuilder.append("onComplete..." + "\n");
				mTv.setText(stringBuilder);
			}
		};

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mComDisposable.dispose();
	}

	@Override
	public void onClick(View v) {
		mObservable.subscribeOn(Schedulers.computation())
				.observeOn(AndroidSchedulers.mainThread())
				.onTerminateDetach()
				.subscribe(mObserver);
	}
}
