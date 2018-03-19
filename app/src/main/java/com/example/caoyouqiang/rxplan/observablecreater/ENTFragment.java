package com.example.caoyouqiang.rxplan.observablecreater;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.observers.EmptyCompletableObserver;

/**
 * Created by caoyouqiang on 18-3-19.
 */

public class ENTFragment extends BaseFragment {
	private Observable<String> mEmptyObservable;
	private Observable<String> mNeverObservable;
	private Observer<String> mObserver;
	private Unbinder mUnbinder;

	/*@BindView(R.id.btn_empty) Button mBtnEmpty;
	@BindView(R.id.btn_never) Button mBtnNever;*/
	@BindView(R.id.result_tv) TextView mTv;

	public ENTFragment(){

	}

	public static ENTFragment newInstance() {
		ENTFragment fragment = new ENTFragment();
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mEmptyObservable = Observable.empty();
		mNeverObservable = Observable.never();
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
		View view = inflater.inflate(R.layout.ent_fragment_layout, container, false);
		mUnbinder = ButterKnife.bind(this,view);
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

	@OnClick(R.id.btn_empty)
	void emptyClick(){
		mEmptyObservable.subscribe(mObserver);
	}

	@OnClick(R.id.btn_never)
	void neverClick(){
		mNeverObservable.subscribe(mObserver);
	}
}
