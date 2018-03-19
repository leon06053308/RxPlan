package com.example.caoyouqiang.rxplan.observablecreater;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.caoyouqiang.rxplan.BaseFragment;
import com.example.caoyouqiang.rxplan.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/*
* defer延迟创建obervable
*相当与当substribe的时候返回call创建的observable,可以确保Observable包含最新的数据。
* */
public class DeferFragment extends BaseFragment implements View.OnClickListener{
	private Observable<String> mObservable;
	private Observer<String> mObserver;
	TextView mTv;
	Button mBtnSub1;
	Button mBtnSub2;

	public DeferFragment() {
		// Required empty public constructor
	}

	public static DeferFragment newInstance() {
		DeferFragment fragment = new DeferFragment();

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mObservable = Observable.defer(new Callable<ObservableSource<? extends String>>() {
			@Override
			public ObservableSource<? extends String> call() throws Exception {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:mm:hh:ss");
				Date date = new Date(System.currentTimeMillis());
				final String now = simpleDateFormat.format(date);

				return Observable.create(new ObservableOnSubscribe<String>() {
					@Override
					public void subscribe(ObservableEmitter<String> emitter) throws Exception {
						emitter.onNext(now);
					}
				});
			}
		});

		mObserver = new Observer<String>() {
			@Override
			public void onSubscribe(Disposable d) {

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
				stringBuilder.append("onError: " + e.getMessage() + "\n");
				mTv.setText(stringBuilder);
			}

			@Override
			public void onComplete() {

			}
		};
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.defer_fragment_layout, container, false);
		mTv = view.findViewById(R.id.result_tv);
		mTv.setMovementMethod(ScrollingMovementMethod.getInstance());
		mBtnSub1 = view.findViewById(R.id.btn_sub1);
		mBtnSub2 = view.findViewById(R.id.btn_sub2);
		mBtnSub1.setOnClickListener(this);
		mBtnSub2.setOnClickListener(this);
		return  view;
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
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_sub1:
				mObservable.subscribe(mObserver);
				break;
			case R.id.btn_sub2:
				mObservable.subscribe(mObserver);
				break;
			default:
				break;
		}
	}
}
