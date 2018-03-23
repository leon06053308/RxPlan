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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by caoyouqiang on 18-3-19.
 */

public class MDFragment extends BaseFragment {
	@BindView(R.id.result_tv)
	TextView mTv;
	@BindView(R.id.btn_sub1)
	Button mMLBtn;
	@BindView(R.id.btn_sub2)
	Button mDLBtn;
	Unbinder mUnbinder;
	private Observable<Notification<String>> mMLObservable;
	private Observable<Notification<Integer>> mDLObservable;
	private Observer<Notification<String>> mObserver;
	private Observer<Notification<Integer>> mObserver2;


	public MDFragment(){

	}

	public static MDFragment newInstance() {
		MDFragment fragment = new MDFragment();
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mMLObservable = Observable.just("a", "b").materialize();
		mDLObservable = Observable.create(new ObservableOnSubscribe<Notification<Integer>>() {
			@Override
			public void subscribe(@NonNull ObservableEmitter<Notification<Integer>> e) throws Exception {
				e.onNext(Notification.createOnNext(1));
				e.onNext(Notification.<Integer>createOnError(new Throwable("My error!")));
				e.onNext(Notification.<Integer>createOnComplete());

			}
		});

		mObserver = new Observer<Notification<String>>() {
			@Override
			public void onSubscribe(Disposable d) {
				StringBuilder stringBuilder = new StringBuilder(mTv.getText());
				stringBuilder.append("onSubscribe..." + "\n");
				mTv.setText(stringBuilder);
			}

			@Override
			public void onNext(Notification<String> s) {
				String val = s.isOnNext() ? "next" : (s.isOnComplete() ? "complete" : "error");

				StringBuilder stringBuilder = new StringBuilder(mTv.getText());
				stringBuilder.append("onNext--" + val + "\n");
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

		mObserver2 = new Observer<Notification<Integer>>() {
			@Override
			public void onSubscribe(Disposable d) {
				StringBuilder stringBuilder = new StringBuilder(mTv.getText());
				stringBuilder.append("onSubscribe..." + "\n");
				mTv.setText(stringBuilder);
			}

			@Override
			public void onNext(Notification<Integer> o) {
				StringBuilder stringBuilder = new StringBuilder(mTv.getText());
				stringBuilder.append("onNext--" + o.getValue() + "\n");
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
		View view = inflater.inflate(R.layout.defer_fragment_layout, container, false);
		mUnbinder = ButterKnife.bind(this, view);
		mMLBtn.setText("Materialize");
		mDLBtn.setText("Dematerialize");
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

	@OnClick(R.id.btn_sub1)
	void mlClick(){
		mTv.setText("");
		mMLObservable.subscribe(mObserver);
	}

	@OnClick(R.id.btn_sub2)
	void dlClick(){
		mTv.setText("");
		mDLObservable.subscribe(mObserver2);
	}
}
