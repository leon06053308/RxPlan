package com.example.caoyouqiang.rxplan;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.caoyouqiang.rxplan.constant.Constants;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
	private GridView mGridView;
	private List<String> mDatas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("RxPlan");
		setSupportActionBar(toolbar);

		mGridView = findViewById(R.id.main_grid_view);
		mDatas = Arrays.asList(getResources().getStringArray(R.array.category));
		mGridView.setAdapter(new GridAdapter(getApplication(), mDatas));
		mGridView.setOnItemClickListener(this);
	}

	class GridAdapter extends BaseAdapter{
		private List<String> mDatas;
		private LayoutInflater mInflater;

		public GridAdapter(Context context, List<String> datas) {
			mDatas = datas;
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return mDatas.size();
		}

		@Override
		public Object getItem(int position) {
			return mDatas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ItemViewHolder itemViewHolder;

			if (convertView == null){
				convertView = mInflater.inflate(R.layout.main_grid_item, parent, false);
				itemViewHolder = new ItemViewHolder(convertView);
				convertView.setTag(itemViewHolder);
			}else {
				itemViewHolder = (ItemViewHolder) convertView.getTag();
			}

			itemViewHolder.textView.setText(mDatas.get(position));
			return convertView;
		}

		class ItemViewHolder{
			private TextView textView;

			public ItemViewHolder(View item){
				textView = item.findViewById(R.id.main_item_title);
			}
		}
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent();
		intent.putExtra(Constants.OP_NAME, mDatas.get(position));

		switch (position){
			case 0:
				intent.setClass(this, ObCreaterActivity.class);
				intent.putExtra(Constants.OP_TAG, Constants.OpEnum.OP_CREATER);
				startActivity(intent);
				break;
			case 1:
				intent.setClass(this, OpChangeActivity.class);
				intent.putExtra(Constants.OP_TAG, Constants.OpEnum.OP_CHANGE);
				startActivity(intent);
				break;
			case 2:
				intent.setClass(this, OpFilterActivity.class);
				intent.putExtra(Constants.OP_TAG, Constants.OpEnum.OP_FILTER);
				startActivity(intent);
				break;
			case 3:
				intent.setClass(this, OpGroupActivity.class);
				intent.putExtra(Constants.OP_TAG, Constants.OpEnum.OP_GROUP);
				startActivity(intent);
				break;
			case 4:
				intent.setClass(this, ErrorHandleActivity.class);
				intent.putExtra(Constants.OP_TAG, Constants.OpEnum.OP_ERROE);
				startActivity(intent);
				break;
			case 5:
				intent.setClass(this, OpAssistActivity.class);
				intent.putExtra(Constants.OP_TAG, Constants.OpEnum.OP_ASSIST);
				startActivity(intent);
				break;
			case 6:
				intent.setClass(this, OpConditionActivity.class);
				intent.putExtra(Constants.OP_TAG, Constants.OpEnum.OP_CONDITION);
				startActivity(intent);
				break;
			case 7:
				intent.setClass(this, OpMathActivity.class);
				intent.putExtra(Constants.OP_TAG, Constants.OpEnum.OP_MATH);
				startActivity(intent);
				break;
			case 8:
				intent.setClass(this, OpConnectionActivity.class);
				intent.putExtra(Constants.OP_TAG, Constants.OpEnum.OP_CONNECT);
				startActivity(intent);
				break;
			case 9:
				intent.setClass(this, OpTransferActivity.class);
				intent.putExtra(Constants.OP_TAG, Constants.OpEnum.OP_TRANSFER);
				startActivity(intent);
				break;
			default:
				break;
		}
	}
}
