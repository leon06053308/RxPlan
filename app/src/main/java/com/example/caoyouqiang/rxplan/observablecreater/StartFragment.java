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

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by caoyouqiang on 18-3-19.
 * 注意,此处start事例并非Start原意,暂时未在rxjava中找到相关操作符
 * 原意:返回一个Observable，它发射一个类似于函数声明的值
 */
public class StartFragment extends BaseFragment {
	@BindView(R.id.textView)
	TextView mTv;
	@BindView(R.id.btn_start)
	Button mStartBtn;
	Unbinder mUnbinder;
	private Observable<String> mStartObservable;
	private Observer<String> mObserver;

	public StartFragment(){

	}

	public static StartFragment newInstance() {
		StartFragment fragment = new StartFragment();
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//先发送a,然后再依次发送aa, ba, acc
		mStartObservable = Observable.fromIterable(Arrays.asList(new String[]{"aa", "ba", "acc"})).startWith("a");

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
		mStartBtn.setText("Start");
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
		mStartObservable.subscribe(mObserver);
	}
}
