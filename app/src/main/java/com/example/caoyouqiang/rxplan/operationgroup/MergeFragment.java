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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by caoyouqiang on 18-3-19.
 */

public class MergeFragment extends BaseFragment {
	@BindView(R.id.textView)
	TextView mTv;
	@BindView(R.id.btn_start)
	Button mStartBtn;
	Unbinder mUnbinder;
	private Observable<Integer> mObservable;
	private Consumer<Integer> mObserver;

	public MergeFragment(){

	}

	public static MergeFragment newInstance() {
		MergeFragment fragment = new MergeFragment();
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Observable<Integer> o1 = Observable.just(1, 2, 3).subscribeOn(Schedulers.io());
		Observable<Integer> o2 = Observable.just(4, 5, 6).subscribeOn(Schedulers.newThread());
		mObservable = Observable.merge(o1, o2);

		mObserver = new Consumer<Integer>() {
			@Override
			public void accept(Integer integer) throws Exception {
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
		mStartBtn.setText("Merge");
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
		mObservable
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(mObserver);
	}
}
