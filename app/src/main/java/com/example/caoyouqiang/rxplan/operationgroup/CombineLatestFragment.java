package com.example.caoyouqiang.rxplan.operationgroup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.caoyouqiang.rxplan.BaseFragment;
import com.example.caoyouqiang.rxplan.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by caoyouqiang on 18-3-19.
 * 该操作符接受多个Observable以及一个函数作为参数，并且函数的签名为这些Observable发射的数据类型。当以上的任意一个Observable发射数据之后，
 * 会去取其它Observable 最近一次发射的数据，回调到函数当中，但是该函数回调的前提是所有的Observable都至少发射过一个数据项。
 */

public class CombineLatestFragment extends BaseFragment {
	@BindView(R.id.et_user_name)
	EditText mNameEt;
	@BindView(R.id.et_user_password)
	EditText mPasswordEt;
	@BindView(R.id.btn_login)
	Button mLoginBtn;
	Unbinder mUnbinder;
	private PublishSubject mNameSubject;
	private PublishSubject mPasswordSubject;
	private CompositeDisposable mCompositeDisposable;

	public CombineLatestFragment(){

	}

	public static CombineLatestFragment newInstance() {
		CombineLatestFragment fragment = new CombineLatestFragment();
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mNameSubject = PublishSubject.create();
		mPasswordSubject = PublishSubject.create();
		mCompositeDisposable = new CompositeDisposable();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.combinelatest_fragment_layout, container, false);
		mUnbinder = ButterKnife.bind(this, view);

		mNameEt.addTextChangedListener(new EditTextMonitor(mNameSubject));
		mPasswordEt.addTextChangedListener(new EditTextMonitor(mPasswordSubject));

		//当第一次进入页面,并且只有一个editext改变则不会触发apply函数
		Observable<Boolean> observable = Observable.combineLatest(mNameSubject, mPasswordSubject, new BiFunction<String, String, Boolean>() {
			@Override
			public Boolean apply(String o, String o2) throws Exception {
				Log.d("Leo", "name: " + o + "---pws: " + o2);
				return (o.length() > 6 && o2.length() >8);
			}
		});

		DisposableObserver<Boolean> disposableObserver = new DisposableObserver<Boolean>() {
			@Override
			public void onNext(Boolean aBoolean) {
				mLoginBtn.setEnabled(aBoolean);
			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onComplete() {

			}
		};

		observable.subscribe(disposableObserver);

		mCompositeDisposable.add(disposableObserver);

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
		mCompositeDisposable.dispose();
	}

	@OnClick(R.id.btn_login)
	void startClick(){
	}

	class EditTextMonitor implements TextWatcher{
		private PublishSubject mPublishSubject;

		public EditTextMonitor(PublishSubject publishSubject){
			mPublishSubject = publishSubject;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			mPublishSubject.onNext(s.toString());
		}
	}
}
