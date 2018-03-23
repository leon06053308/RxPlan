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

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Timed;

/**
 * Created by caoyouqiang on 18-3-19.
 *
 * 给发射的每个数据添加时间，转换了为Timed，和timeInterval的参数一致，但是timestamp获取到的time是时间戳，需要自己转换。
 */

public class TimeOutFragment extends BaseFragment {
	@BindView(R.id.textView)
	TextView mTv;
	@BindView(R.id.btn_start)
	Button mStartBtn;
	Unbinder mUnbinder;
	private Observable<Integer> mObservable;
	private Consumer<Integer> mObserver;

	public TimeOutFragment(){

	}

	public static TimeOutFragment newInstance() {
		TimeOutFragment fragment = new TimeOutFragment();
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
		}).timeout(3, TimeUnit.SECONDS);

		mObserver = new Consumer<Integer>() {
			@Override
			public void accept(Integer integerTimed) throws Exception {
				StringBuilder stringBuilder = new StringBuilder(mTv.getText());
				stringBuilder.append("accept--" + integerTimed + "\n");
				mTv.setText(stringBuilder);
			}

		};
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.creater_fragment_layout, container, false);
		mUnbinder = ButterKnife.bind(this, view);
		mStartBtn.setText("TimeOut");
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
		mObservable.subscribe(mObserver, new Consumer<Throwable>() {
			@Override
			public void accept(Throwable throwable) throws Exception {
				StringBuilder stringBuilder = new StringBuilder(mTv.getText());
				stringBuilder.append("accept error--" + throwable.getMessage() + "\n");
				mTv.setText(stringBuilder);
			}
		}, new Action() {
			@Override
			public void run() throws Exception {
				StringBuilder stringBuilder = new StringBuilder(mTv.getText());
				stringBuilder.append("complete..." + "\n");
				mTv.setText(stringBuilder);
			}
		});
	}
}
