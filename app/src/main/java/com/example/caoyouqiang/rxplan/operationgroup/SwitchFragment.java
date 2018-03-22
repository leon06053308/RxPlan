package com.example.caoyouqiang.rxplan.operationgroup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
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
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by caoyouqiang on 18-3-19.
 */

public class SwitchFragment extends BaseFragment {
	@BindView(R.id.textView)
	TextView mTv;
	@BindView(R.id.btn_start)
	Button mStartBtn;
	Unbinder mUnbinder;
	private Observable<String> mObservable;
	private Observer<String> mObserver;

	public SwitchFragment(){

	}

	public static SwitchFragment newInstance() {
		SwitchFragment fragment = new SwitchFragment();
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mObservable = Observable.switchOnNext(Observable.create(new ObservableOnSubscribe<ObservableSource<? extends String>>() {
			@Override
			public void subscribe(ObservableEmitter<ObservableSource<? extends String>> emitter) throws Exception {
				for (int i = 0; i<3; i++){
					emitter.onNext(createObserver(i));

					/*try {
						Thread.sleep(2000);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}*/
				}
			}
		}));

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

	private Observable<String> createObserver(final int index) {
		return Observable.create(new ObservableOnSubscribe<String>() {
			@Override
			public void subscribe(ObservableEmitter<String> e) throws Exception {
				for (int i = 1; i < 5; i++) {
					e.onNext(index + "-" + i);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.creater_fragment_layout, container, false);
		mUnbinder = ButterKnife.bind(this, view);
		mStartBtn.setText("Switch");
		mTv.setMovementMethod(ScrollingMovementMethod.getInstance());

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
		mObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(mObserver);
	}
}
