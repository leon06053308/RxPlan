package com.example.caoyouqiang.rxplan.operationchange;

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

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by caoyouqiang on 18-3-19.
 * buffer(count,skip) 每次间隔skip创建包含count个数据的缓冲区发送出去.此例输出结果为:a b | b c | c d | d e
 */

public class BufferFragment extends BaseFragment {
	@BindView(R.id.textView)
	TextView mTv;
	@BindView(R.id.btn_start)
	Button mStartBtn;
	Unbinder mUnbinder;
	private Observable<List<String>> mBufferObservable;
	private Observer<List<String>> mObserver;

	public BufferFragment(){

	}

	public static BufferFragment newInstance() {
		BufferFragment fragment = new BufferFragment();
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mBufferObservable = Observable.fromArray(new String[]{"a","b","c","d","e"}).buffer(2,1);
		mObserver = new Observer<List<String>>() {
			@Override
			public void onSubscribe(Disposable d) {

			}

			@Override
			public void onNext(List<String> strings) {
				StringBuilder stringBuilder = new StringBuilder(mTv.getText());
				for (String s : strings){
					stringBuilder.append("onNext--" + s + "\n");
				}

				stringBuilder.append("---------------------" + "\n");
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
		View view = inflater.inflate(R.layout.creater_fragment_layout, container, false);
		mUnbinder = ButterKnife.bind(this, view);
		mStartBtn.setText("Buffer");
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
		mBufferObservable.subscribe(mObserver);
	}
}
