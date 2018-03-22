package com.example.caoyouqiang.rxplan.errorhandle;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by caoyouqiang on 18-3-19.
 */

public class CatchFragment extends BaseFragment {
	@BindView(R.id.textView)
	TextView mTv;
	@BindView(R.id.btn_start)
	Button mStartBtn;
	Unbinder mUnbinder;
	private Observable<String> mObservable;
	private Observer<String> mObserver;
	private static final String TAG = "CatchFragment";

	public CatchFragment(){

	}

	public static CatchFragment newInstance() {
		CatchFragment fragment = new CatchFragment();
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mObservable = Observable.create(new ObservableOnSubscribe<String>() {
			@Override
			public void subscribe(ObservableEmitter<String> emitter) throws Exception {
				emitter.onError(new NullPointerException("fk null"));
			}
		}).onErrorReturn(new Function<Throwable, String>() {
			@Override
			public String apply(Throwable throwable) throws Exception {
				if (throwable instanceof  NullPointerException) {
					return throwable.getMessage();
				}

				return "nothing...";
			}
		});

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
		mStartBtn.setText("Catch");
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

	/*注意onErrorResumeNext拦截的错误是Throwable，不能拦截Exception。 不然它会将错误传递给观察者的onError方法。要拦截Exception请用onExceptionResumeNext*/
	public void testOnErrorResumeReturn() {
		Observable.create(new ObservableOnSubscribe<String>() {
			@Override
			public void subscribe(ObservableEmitter<String> e) throws Exception {
				for(int i = 0; i<= 3 ;i++){
					if(i == 2){
						//这里是Throwable
						e.onError(new Throwable("出现错误了"));
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
				.onErrorResumeNext(new Function<Throwable, ObservableSource<? extends String>>() {
					@Override
					public ObservableSource<? extends String> apply(@NonNull Throwable throwable) throws Exception {
						//拦截到错误之后，重新定义了被观察者
						return Observable.just("重新定义了被观察者");
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

	/*注意onExceptionResumeNext拦截的错误是Exception,不能拦截Throwable。 不然它会将错误传递给观察者的onError方法。要拦截Throwable请用onErrorResumeNext*/
	public void testOnExceptionResumeReturn() {
		Observable.create(new ObservableOnSubscribe<String>() {
			@Override
			public void subscribe(ObservableEmitter<String> e) throws Exception {
				for(int i = 0; i<= 3 ;i++){
					if(i == 2){
						//注意这里是Exception
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
				.onExceptionResumeNext(new Observable<String>() {
					@Override
					protected void subscribeActual(Observer<? super String> observer) {
						observer.onNext("错误替换的消息");
						observer.onComplete();
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
