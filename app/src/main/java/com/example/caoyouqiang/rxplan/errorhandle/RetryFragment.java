package com.example.caoyouqiang.rxplan.errorhandle;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
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
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by caoyouqiang on 18-3-19.
 */

public class RetryFragment extends BaseFragment {
	@BindView(R.id.textView)
	TextView mTv;
	@BindView(R.id.btn_start)
	Button mStartBtn;
	Unbinder mUnbinder;
	private Observable<String> mObservable;
	private Observer<String> mObserver;
	private static final String TAG = "RetryFragment";

	public RetryFragment(){

	}

	public static RetryFragment newInstance() {
		RetryFragment fragment = new RetryFragment();
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mObservable = Observable.create(new ObservableOnSubscribe<String>() {
			int i = 0;
			@Override
			public void subscribe(ObservableEmitter<String> emitter) throws Exception {
				i++;
				emitter.onNext("a");
				emitter.onNext("b");
				emitter.onError(new NullPointerException("fk null"));
			}
		}).retry();

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
		mStartBtn.setText("Retry");
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

	public void testRetry(){
		Observable.create(new ObservableOnSubscribe<String>() {
			@Override
			public void subscribe(ObservableEmitter<String> e) throws Exception {
				for(int i = 0; i<= 3 ;i++){
					if(i == 2){
						e.onError(new Exception("出现错误了"));
					}else{
						e.onNext(i+"");
					}
					try{
						Thread.sleep(1000);
					}catch (Exception ex){
						ex.printStackTrace();
					}
				}

				e.onComplete();
			}
		})
				.subscribeOn(Schedulers.newThread())
                /*.retry(new Predicate<Throwable>() {
                    @Override
                    public boolean test(@NonNull Throwable throwable) throws Exception {
                        Log.e(TAG, "retry错误: "+throwable.toString());

                        //返回假就是不让重新发射数据了，调用观察者的onError就终止了。
                        //返回真就是让被观察者重新发射请求
                        return true;
                    }
                })*/
                /*.retry(new BiPredicate<Integer, Throwable>() {
                    @Override
                    public boolean test(@NonNull Integer integer, @NonNull Throwable throwable) throws Exception {
                        Log.e(TAG, "retry错误: "+integer+" "+throwable.toString());

                        //返回假就是不让重新发射数据了，调用观察者的onError就终止了。
                        //返回真就是让被观察者重新发射请求
                        return true;
                    }
                })*/
//                .retry(3)    //最多让被观察者重新发射数据3次
				.retry(3, new Predicate<Throwable>() {
					@Override
					public boolean test(@NonNull Throwable throwable) throws Exception {
						Log.e(TAG, "retry错误: "+throwable.toString());
						//最多让被观察者重新发射数据3次，但是这里返回值可以进行处理
						//返回假就是不让重新发射数据了，调用观察者的onError就终止了。
						//返回真就是让被观察者重新发射请求
						return true;
					}
				})
				.subscribe(new Consumer<String>() {
					@Override
					public void accept(@NonNull String s) throws Exception {
						Log.e(TAG, "收到消息: " + s);
					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(@NonNull Throwable throwable) throws Exception {
						Log.e(TAG, "结果错误: " + throwable.toString());
					}
				});
	}

	public void testRetryWhen() {
		Observable.create(new ObservableOnSubscribe<String>() {
			@Override
			public void subscribe(ObservableEmitter<String> e) throws Exception {
				for (int i = 0; i <= 3; i++) {
					if (i == 2) {
						e.onError(new Exception("出现错误了"));
					} else {
						e.onNext(i + "");
					}
					try {
						Thread.sleep(1000);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

				e.onComplete();
			}
		})
				.subscribeOn(Schedulers.newThread())
				.retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
					@Override
					public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {

						//这里可以发送新的被观察者 Observable
						return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
							@Override
							public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {

								//如果发射的onError就终止
								return Observable.error(new Throwable("retryWhen终止啦"));
							}
						});

					}
				})

				.subscribe(new Consumer<String>() {
					@Override
					public void accept(@NonNull String s) throws Exception {
						Log.e(TAG, "收到消息: " + s);
					}
				}, new Consumer<Throwable>() {
					@Override
					public void accept(@NonNull Throwable throwable) throws Exception {
						Log.e(TAG, "结果错误: " + throwable.toString());
					}
				});
	}


}
