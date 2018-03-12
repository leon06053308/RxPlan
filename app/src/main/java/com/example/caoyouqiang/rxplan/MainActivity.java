package com.example.caoyouqiang.rxplan;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
	private GridView mGridView;
	private List<String> mDatas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mGridView = findViewById(R.id.main_grid_view);
		mDatas = Arrays.asList(getResources().getStringArray(R.array.category));
		mGridView.setAdapter(new GridAdapter(getApplication(), mDatas));
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
		switch (position){
			case 0:
				startActivity(new Intent(this, ObCreaterActivity.class));
				break;
			case 1:
				startActivity(new Intent(this, OpChangeActivity.class));
				break;
			case 2:
				startActivity(new Intent(this, OpFilterActivity.class));
				break;
			case 3:
				startActivity(new Intent(this, OpGroupActivity.class));
				break;
			case 4:
				startActivity(new Intent(this, ErrorHandleActivity.class));
				break;
			case 5:
				startActivity(new Intent(this, OpAssistActivity.class));
				break;
			case 6:
				startActivity(new Intent(this, OpConditionActivity.class));
				break;
			case 7:
				startActivity(new Intent(this, OpMathActivity.class));
				break;
			case 8:
				startActivity(new Intent(this, OpConnectionActivity.class));
				break;
			case 9:
				startActivity(new Intent(this, OpTransferActivity.class));
				break;
			default:
				break;
		}
	}
}
