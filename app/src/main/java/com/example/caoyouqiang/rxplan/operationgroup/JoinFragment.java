package com.example.caoyouqiang.rxplan.operationgroup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by caoyouqiang on 18-3-19.
 */

public class JoinFragment extends BaseFragment {
	@BindView(R.id.textView)
	TextView mTv;
	@BindView(R.id.btn_start)
	Button mStartBtn;
	Unbinder mUnbinder;
	private Observable<Long> mObservable;
	private Observer<Long> mObserver;

	public JoinFragment(){

	}

	public static JoinFragment newInstance() {
		JoinFragment fragment = new JoinFragment();
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Observable<Integer> o1 = Observable.just(1, 2, 3);
		Observable<Integer> o2 = Observable.just(4, 5, 6);
		Observable<Integer> o3 = Observable.just(7, 8, 9);

		o1.join(o2, new Function<Integer, ObservableSource<String>>() {
			@Override
			public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
				Log.d("Leo", "001: " + integer);
				return Observable.just(integer + "-o1").delay(200, TimeUnit.MILLISECONDS);
			}
		}, new Function<Integer, ObservableSource<String>>() {
			@Override
			public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
				Log.d("Leo", "002: " + integer);

				return Observable.just(integer + "-o2").delay(200, TimeUnit.MILLISECONDS);
			}
		}, new BiFunction<Integer, Integer, String>() {
			@Override
			public String apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
				Log.d("Leo", "003: " + integer + "***" + integer2);

				return integer + " - " + integer2;
			}
		}).subscribe(new Consumer<String>() {
			@Override
			public void accept(String s) throws Exception {
				Log.d("Leo", "res: " + s);
				System.out.println("onNext" + s);
			}
		});

	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.creater_fragment_layout, container, false);
		mUnbinder = ButterKnife.bind(this, view);
		mStartBtn.setText("Join");
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
	}
}
