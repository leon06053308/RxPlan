package com.example.caoyouqiang.rxplan.operationassist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
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
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by caoyouqiang on 18-3-19.
 */

public class DoFragment extends BaseFragment {
	@BindView(R.id.textView)
	TextView mTv;
	@BindView(R.id.btn_start)
	Button mStartBtn;
	Unbinder mUnbinder;
	private Observable<Integer> mObservable;
	private Consumer<Integer> mObserver;
	private static final String TAG = "Leo";

	public DoFragment(){

	}

	public static DoFragment newInstance() {
		DoFragment fragment = new DoFragment();
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mObservable = Observable.just(1,2,3,4,5)
				.doOnNext(new Consumer<Integer>() {
			@Override
			public void accept(@NonNull Integer s) throws Exception {
				Log.e(TAG, "doOnNext: " + s + "---" + Thread.currentThread().getId());
				StringBuilder stringBuilder = new StringBuilder(mTv.getText());
				stringBuilder.append("doOnNext: " + s + "\n");
				mTv.setText(stringBuilder);
			}
		})
				.doAfterNext(new Consumer<Integer>() {
					@Override
					public void accept(Integer integer) throws Exception {
						Log.e(TAG, "doAfterNext: " + integer + "---" + Thread.currentThread().getId());
						StringBuilder stringBuilder = new StringBuilder(mTv.getText());
						stringBuilder.append("doAfterNext: " + integer + "\n");
						mTv.setText(stringBuilder);
					}
				})
				.doOnComplete(new Action() {
					@Override
					public void run() throws Exception {
						Log.e(TAG, "doOnComplete..." + Thread.currentThread().getId());

						StringBuilder stringBuilder = new StringBuilder(mTv.getText());
						stringBuilder.append("doOnComplete..." + "\n");
						mTv.setText(stringBuilder);
					}
				})
				//订阅之后回调的方法
				.doOnSubscribe(new Consumer<Disposable>() {
					@Override
					public void accept(@NonNull Disposable disposable) throws Exception {
						Log.e(TAG, "doOnSubscribe..." + Thread.currentThread().getId());

						StringBuilder stringBuilder = new StringBuilder(mTv.getText());
						stringBuilder.append("doOnSubscribe..." + "\n");
						mTv.setText(stringBuilder);
					}
				})
				.doAfterTerminate(new Action() {
					@Override
					public void run() throws Exception {
						Log.e(TAG, "doAfterTerminate..." + Thread.currentThread().getId());

						StringBuilder stringBuilder = new StringBuilder(mTv.getText());
						stringBuilder.append("doAfterTerminate..." + "\n");
						mTv.setText(stringBuilder);
					}
				})
				.doFinally(new Action() {
					@Override
					public void run() throws Exception {
						Log.e(TAG, "doFinally..." + Thread.currentThread().getId());


						StringBuilder stringBuilder = new StringBuilder(mTv.getText());
						stringBuilder.append("doFinally..." + "\n");
						mTv.setText(stringBuilder);
					}
				})
				//Observable每发射一个数据的时候就会触发这个回调，不仅包括onNext还包括onError和onCompleted
				.doOnEach(new Consumer<Notification<Integer>>() {
					@Override
					public void accept(@NonNull Notification<Integer> stringNotification) throws Exception {
						String res = stringNotification.isOnNext()?"onNext":stringNotification.isOnComplete()?"onComplete":"onError";
						Log.e("Leo", "doOnEach: "+ res);

						StringBuilder stringBuilder = new StringBuilder(mTv.getText());
						stringBuilder.append("doOnEach: " + res + "\n");
						mTv.setText(stringBuilder);

					}
				})
				//订阅后可以进行取消订阅
				.doOnLifecycle(new Consumer<Disposable>() {
					@Override
					public void accept(@NonNull Disposable disposable) throws Exception {
						//disposable.dispose();
						StringBuilder stringBuilder = new StringBuilder(mTv.getText());
						stringBuilder.append("doOnLifecycle001: " + "\n");
						mTv.setText(stringBuilder);
					}
				}, new Action() {
					@Override
					public void run() throws Exception {
						StringBuilder stringBuilder = new StringBuilder(mTv.getText());
						stringBuilder.append("doOnLifecycle002: " + "\n");
						mTv.setText(stringBuilder);
					}
				});

		mObserver = new Consumer<Integer>() {
			@Override
			public void accept(Integer integer) throws Exception {
				StringBuilder stringBuilder = new StringBuilder(mTv.getText());
				stringBuilder.append("receive msg..." + "\n");
				mTv.setText(stringBuilder);
			}
		};

		/*Observable.just("1","2")
				.doOnNext(new Consumer<String>() {
					@Override
					public void accept(@NonNull String s) throws Exception {
						Log.e(TAG, "doOnNext: " + s);

					}
				})
				.doAfterNext(new Consumer<String>() {
					@Override
					public void accept(@NonNull String s) throws Exception {
						Log.e(TAG, "doAfterNext: " + s);
					}
				})
				.doOnComplete(new Action() {
					@Override
					public void run() throws Exception {
						Log.e(TAG, "doOnComplete: ");
					}
				})
				//订阅之后回调的方法
				.doOnSubscribe(new Consumer<Disposable>() {
					@Override
					public void accept(@NonNull Disposable disposable) throws Exception {
						Log.e(TAG, "doOnSubscribe: ");
					}
				})
				.doAfterTerminate(new Action() {
					@Override
					public void run() throws Exception {
						Log.e(TAG, "doAfterTerminate: ");
					}
				})
				.doFinally(new Action() {
					@Override
					public void run() throws Exception {
						Log.e(TAG, "doFinally: ");
					}
				})
				//Observable每发射一个数据的时候就会触发这个回调，不仅包括onNext还包括onError和onCompleted
				.doOnEach(new Consumer<Notification<String>>() {
					@Override
					public void accept(@NonNull Notification<String> stringNotification) throws Exception {
						Log.e(TAG, "doOnEach: "+(stringNotification.isOnNext()?"onNext":stringNotification.isOnComplete()?"onComplete":"onError"));

					}
				})
				//订阅后可以进行取消订阅
				.doOnLifecycle(new Consumer<Disposable>() {
					@Override
					public void accept(@NonNull Disposable disposable) throws Exception {
						Log.e(TAG, "doOnLifecycle: "+disposable.isDisposed());
						//disposable.dispose();
					}
				}, new Action() {
					@Override
					public void run() throws Exception {
						Log.e(TAG, "doOnLifecycle run: ");

					}
				})
				.subscribe(new Consumer<String>() {
					@Override
					public void accept(@NonNull String s) throws Exception {
						Log.e(TAG, "收到消息: " + s);
					}
				});*/
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.creater_fragment_layout, container, false);
		mUnbinder = ButterKnife.bind(this, view);
		mStartBtn.setText("Do");
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
		mObservable.subscribe(mObserver);
	}
}
