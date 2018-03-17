package com.example.caoyouqiang.rxplan;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.caoyouqiang.rxplan.constant.Constants;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseActivity extends AppCompatActivity implements OnItemClickListener{
	protected RecyclerView mBaseRv;
	protected Constants.OpEnum mOpValue;
	protected List<String> mOpList = new ArrayList<>();



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);

		mBaseRv = findViewById(R.id.base_rv);
		assignValue(getIntent());

		MyAdapter adapter = new MyAdapter(this, mOpList);
		adapter.setItemClickListener(this);
		mBaseRv.setLayoutManager(new LinearLayoutManager(this));
		mBaseRv.setAdapter(adapter);
	}

	@Override
	public void onItemClick(View view, int position) {
	}

	private void assignValue(Intent intent){
		mOpValue = (Constants.OpEnum) intent.getSerializableExtra(Constants.OP_TAG);
		switch (mOpValue){
			case OP_CREATER:
				mOpList = Arrays.asList(getResources().getStringArray(R.array.observable_creater));
				break;
			case OP_CHANGE:
				mOpList = Arrays.asList(getResources().getStringArray(R.array.change_op));
				break;
			case OP_FILTER:
				mOpList = Arrays.asList(getResources().getStringArray(R.array.filter_op));
				break;
			case OP_GROUP:
				mOpList = Arrays.asList(getResources().getStringArray(R.array.group_op));
				break;
			case OP_ERROE:
				mOpList = Arrays.asList(getResources().getStringArray(R.array.error_op));
				break;
			case OP_ASSIST:
				mOpList = Arrays.asList(getResources().getStringArray(R.array.assist_op));
				break;
			case OP_CONDITION:
				mOpList = Arrays.asList(getResources().getStringArray(R.array.condition_op));
				break;
			case OP_MATH:
				mOpList = Arrays.asList(getResources().getStringArray(R.array.math_op));
				break;
			case OP_CONNECT:
				mOpList = Arrays.asList(getResources().getStringArray(R.array.condition_op));
				break;
			case OP_TRANSFER:
				mOpList = Arrays.asList(getResources().getStringArray(R.array.transfer_op));
				break;
			default:
				break;
		}
	}

	static class MyAdapter extends RecyclerView.Adapter{
		private List<String> mDatas;
		private Context mContext;
		OnItemClickListener mListener;

		public MyAdapter(Context context, List<String> datas) {
			mDatas = datas;
			mContext = context;
		}

		public void setItemClickListener(OnItemClickListener listener){
			mListener = listener;
		}

		@Override
		public long getItemId(int position) {
			return super.getItemId(position);
		}

		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(mContext).inflate(R.layout.op_rv_item, parent, false);
			ViewHolder holder = new ViewHolder(view);
			return holder;
		}

		@Override
		public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
			String val = mDatas.get(position);
			ViewHolder viewHolder = (ViewHolder) holder;
			viewHolder.tvOpName.setText(val);

			if (mListener != null){
				viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						mListener.onItemClick(v, position);
					}
				});
			}
		}

		@Override
		public int getItemCount() {
			return mDatas.size();
		}

		class ViewHolder extends RecyclerView.ViewHolder{
			public TextView tvOpName;

			public ViewHolder(View itemView) {
				super(itemView);
				tvOpName = itemView.findViewById(R.id.tv_op_title);
			}
		}
	}
}
