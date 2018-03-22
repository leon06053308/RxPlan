package com.example.caoyouqiang.rxplan.operationgroup;

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
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * Created by caoyouqiang on 18-3-19.
 * zip 通过一个函数将多个Observable发送的事件结合到一起，然后发送这些组合到一起的事件. 它按照严格的顺序应用这个函数。它只发射与发射数据项最少的那个Observable一样多的数据。
 */

public class ZipFragment extends BaseFragment {
	@BindView(R.id.textView)
	TextView mTv;
	@BindView(R.id.btn_start)
	Button mStartBtn;
	Unbinder mUnbinder;
	private Observable<String> mObservable;
	private Consumer<String> mObserver;

	public ZipFragment(){

	}

	public static ZipFragment newInstance() {
		ZipFragment fragment = new ZipFragment();
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Observable<Integer> observable1 = Observable.just(1, 2, 3, 4);
		Observable<Integer> observable2 = Observable.just(1, 2, 3);
		mObservable = Observable.zip(observable1, observable2, new BiFunction<Integer, Integer, String>() {
			@Override
			public String apply(Integer integer, Integer integer2) throws Exception {
				return integer + integer2 + "";
			}
		});


		mObserver = new Consumer<String>() {
			@Override
			public void accept(String integer) throws Exception {
				StringBuilder stringBuilder = new StringBuilder(mTv.getText());
				stringBuilder.append("accept--" + integer + "\n");
				mTv.setText(stringBuilder);
			}
		};
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.creater_fragment_layout, container, false);
		mUnbinder = ButterKnife.bind(this, view);
		mStartBtn.setText("Zip");
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
		mObservable.subscribe(mObserver);
	}
}
