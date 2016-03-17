package com.bgw.an.app.activity.me.musicdemon;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bgw.an.R;
import com.bgw.an.app.activity.me.musicdemon.entity.Music;

public class RecentActivity extends Activity {
	public static ArrayList<Music> listMusic = new ArrayList<Music>();
	private ListView songsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.musicrecent);
		songsList = (ListView) this.findViewById(R.id.songs_list);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		MusicItemAdapter adapter = new MusicItemAdapter();
		songsList.setAdapter(adapter);

		songsList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Intent intent = new Intent(RecentActivity.this,
						MusicActivity.class);
				intent.putExtra("id", arg2);
				startActivity(intent);
			}
		});

		super.onStart();
	}

	private class MusicItemAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listMusic.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return listMusic.get(arg0);
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.musiclist, null);
			}
			Music m = listMusic.get(position);
			TextView textName = (TextView) convertView
					.findViewById(R.id.music_list_singer);
			textName.setText(m.getSinger());
			TextView music_singer = (TextView) convertView
					.findViewById(R.id.music_list_name);
			music_singer.setText(m.getTitle());

			ImageView img = (ImageView) convertView
					.findViewById(R.id.item_image);
			img.setBackgroundResource(R.drawable.item);
			return convertView;
		}

	}
}
