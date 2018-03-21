package com.example.caoyouqiang.rxplan.operationchange;

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
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;

/**
 * Created by caoyouqiang on 18-3-19.
 * 根据发射的数据转换成相应key保存,然后再发送相应的value
 */

public class GroupByFragment extends BaseFragment {
	@BindView(R.id.textView)
	TextView mTv;
	@BindView(R.id.btn_start)
	Button mStartBtn;
	Unbinder mUnbinder;
	private Observable<GroupedObservable<String, Integer>> mObservable;
	private Consumer<GroupedObservable<String,Integer>> mObserver;

	public GroupByFragment(){

	}

	public static GroupByFragment newInstance() {
		GroupByFragment fragment = new GroupByFragment();
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mObservable = Observable.just(1, 2, 3,4,5).groupBy(new Function<Integer, String>() {
			@Override
			public String apply(Integer integer) throws Exception {
				return integer % 2 == 0 ? "偶数" : "奇数";
			}
		}, new Function<Integer, Integer>() {
			@Override
			public Integer apply(Integer integer) throws Exception {
				return integer + 10;
			}
		});

		mObserver = new Consumer<GroupedObservable<String, Integer>>() {
			@Override
			public void accept(final GroupedObservable<String, Integer> stringIntegerGroupedObservable) throws Exception {
				stringIntegerGroupedObservable.subscribe(new Consumer<Integer>() {
					@Override
					public void accept(Integer integer) throws Exception {
						StringBuilder stringBuilder = new StringBuilder(mTv.getText());
						stringBuilder.append(stringIntegerGroupedObservable.getKey() + ": " + integer + "\n");
						mTv.setText(stringBuilder);

					}
				});
			}
		};

		/*Observable.just(1, 2, 3,4,5).groupBy(new Function<Integer, String>() {
			@Override
			public String apply(Integer integer) throws Exception {
				return integer%2==0?"偶数":"奇数";
			}
		}).subscribe(new Consumer<GroupedObservable<String, Integer>>() {
			@Override
			public void accept(final GroupedObservable<String, Integer> stringIntegerGroupedObservable) throws Exception {
				stringIntegerGroupedObservable.subscribe(new Consumer<Integer>() {
					@Override
					public void accept(Integer integer) throws Exception {
						Log.i("Leo", "groupby:" +stringIntegerGroupedObservable.getKey()+"----"+ integer);
					}
				});
			}
		});*/
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.creater_fragment_layout, container, false);
		mUnbinder = ButterKnife.bind(this, view);
		mStartBtn.setText("GroupBy");
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
