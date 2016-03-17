package com.bgw.an.app.activity.me.musicdemon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.bgw.an.R;
import com.bgw.an.app.activity.me.musicdemon.entity.Music;

@SuppressLint("DefaultLocale")
public class MusicActivity extends Activity implements OnSeekBarChangeListener,
		MediaPlayer.OnErrorListener, Runnable, SensorEventListener {
	public static MediaPlayer player = null;
	private SensorManager sensorManager;

	public static int _id = -1;
	private boolean isLoop = false;
	private boolean mRegistedSensor;
	private TextView textName, textSinger, textStartTime, textEndTime;
	private SeekBar seekBar;
	private ImageButton imgBtnLast, imgBtnRewind, imgBtnPlay, imgBtnForward,
			imgBtnNext;
	private ImageButton imgBtnRandom, imgBtnSingle;

	private List<Music> lists;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			int position = msg.what;
			int total = player.getDuration();
			int progress = position * 100 / total;
			textStartTime.setText(toTime(position));
			seekBar.setProgress(progress);
			super.handleMessage(msg);
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.
				SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/*requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
		setContentView(R.layout.music);

		textName = (TextView) this.findViewById(R.id.music_name);
		textSinger = (TextView) this.findViewById(R.id.music_singer);
		textStartTime = (TextView) this.findViewById(R.id.music_start_time);
		textEndTime = (TextView) this.findViewById(R.id.music_end_time);
		seekBar = (SeekBar) this.findViewById(R.id.music_seekBar);

		imgBtnLast = (ImageButton) findViewById(R.id.imgBtnLast);
		imgBtnRewind = (ImageButton) findViewById(R.id.imgBtnRewind);
		imgBtnPlay = (ImageButton) findViewById(R.id.imgBtnPlay);
		imgBtnForward = (ImageButton) findViewById(R.id.imgBtnForward);
		imgBtnNext = (ImageButton) findViewById(R.id.imgBtnNext);

		imgBtnRandom = (ImageButton) findViewById(R.id.imgBtnRandom);
		imgBtnSingle = (ImageButton) findViewById(R.id.imgBtnSingle);

		setListData();
		// 设置监听器
		imgBtnLast.setOnClickListener(new MyListener());
		imgBtnRewind.setOnClickListener(new MyListener());
		imgBtnPlay.setOnClickListener(new MyListener());
		imgBtnForward.setOnClickListener(new MyListener());
		imgBtnNext.setOnClickListener(new MyListener());

		imgBtnRandom.setOnClickListener(new MyListener());
		imgBtnSingle.setOnClickListener(new MyListener());

		seekBar.setOnSeekBarChangeListener(this);
	}

	private class MyListener implements OnClickListener {

		@Override
		public void onClick(View v) {

			if (v == imgBtnLast) {
				// 第一首
				player.release();
				player = null;
				first();

			} else if (v == imgBtnRewind) {
				// 前一首
				player.release();
				player = null;
				rewind();
			} else if (v == imgBtnPlay) {
				// 正在播放
				if (player.isPlaying()) {
					player.stop();
					imgBtnPlay.setImageResource(R.drawable.play1);
				} else {
					try {
						player.prepare();
						player.start();
						imgBtnPlay.setImageResource(R.drawable.pause1);
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else if (v == imgBtnForward) {
				// 下一首
				player.release();
				player = null;
				forward();
			} else if (v == imgBtnNext) {
				// 最后一首
				player.release();
				player = null;
				last();
			} else if (v == imgBtnSingle) {
				if (isLoop == false) {
					// 顺序播放
					imgBtnSingle
							.setBackgroundResource(R.drawable.play_loop_spec);
					isLoop = true;
				} else {
					// 单曲播放
					imgBtnSingle
							.setBackgroundResource(R.drawable.play_loop_sel);
					isLoop = false;
				}
			} else if (v == imgBtnRandom) {
				imgBtnRandom.setBackgroundResource(R.drawable.play_random_sel);
			}
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub

		int id = getIntent().getIntExtra("id", 1);
		// Toast.makeText(this, id + "", 1).show();
		Music m = lists.get(id);
		 RecentActivity.listMusic.add(m);
		textName.setText(m.getTitle());
		textSinger.setText(m.getSinger());
		textEndTime.setText(toTime((int) m.getTime()));

		imgBtnPlay.setImageResource(R.drawable.pause1);

		if (id != _id) {
			_id = id;
			if (player != null) {
				if (player.isPlaying()) {
					player.release();
					player = null;
				}
			}
			String url = m.getUrl();
			Uri myUri = Uri.parse(url);

			player = new MediaPlayer();
			player.setAudioStreamType(AudioManager.STREAM_MUSIC);
			player.setWakeMode(this, PowerManager.PARTIAL_WAKE_LOCK);
			try {
				player.setDataSource(getApplicationContext(), myUri);
				player.prepare();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			player.start();

			String ns = Context.NOTIFICATION_SERVICE;
			NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
			int icon = R.drawable.play1;
			CharSequence tickerText = "正在播放音乐 . . . ";

			long when = System.currentTimeMillis();
			Notification notification = new Notification(icon, tickerText, when);
			Context context = getApplicationContext();
			CharSequence contentTitle = "正在播放音乐 . . . ";
			CharSequence contentText = "";
			Intent notificationIntent = new Intent(this, MainActivity.class);
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
					notificationIntent, 0);

			notification.setLatestEventInfo(context, contentTitle, contentText,
					contentIntent);

			mNotificationManager.notify(1, notification);
		}
		if (player != null) {
			player.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					// 下一首
					player.reset();
					if (isLoop == true) {
						_id = _id - 1;
					}
					forward();
				}
			});
		}
		new Thread(this).start();

		super.onStart();
	}

	private void first() {
		_id = 0;
		Music m = lists.get(0);
	    RecentActivity.listMusic.add(m);
		textName.setText(m.getTitle());
		textSinger.setText(m.getSinger());
		textEndTime.setText(toTime((int) m.getTime()));

		imgBtnPlay.setImageResource(R.drawable.pause1);

		String url = m.getUrl();
		Uri myUri = Uri.parse(url);
		player = new MediaPlayer();
		player.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			player.setDataSource(getApplicationContext(), myUri);
			player.prepare();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		player.start();
		player.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				// 下一首
				player.reset();
				if (isLoop == true) {
					_id = _id - 1;
				}
				forward();
			}
		});

	}

	private void rewind() {
		if (_id <= 0) {
			_id = 0;
			Toast.makeText(this, "已经是第一首", 1).show();
		} else {
			_id = _id - 1;
		}
		Music m = lists.get(_id);
		RecentActivity.listMusic.add(m);
		textName.setText(m.getTitle());
		textSinger.setText(m.getSinger());
		textEndTime.setText(toTime((int) m.getTime()));

		imgBtnPlay.setImageResource(R.drawable.pause1);

		String url = m.getUrl();
		Uri myUri = Uri.parse(url);
		player = new MediaPlayer();
		player.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			player.setDataSource(getApplicationContext(), myUri);
			player.prepare();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		player.start();
		player.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				// 下一首
				player.reset();
				if (isLoop == true) {
					_id = _id - 1;
				}
				forward();
			}
		});

	}

	private void forward() {
		if (_id >= lists.size() - 1) {
			_id = lists.size() - 1;
			Toast.makeText(this, "已经是最后一首", 1).show();
		} else {
			_id = _id + 1;
		}
		Music m = lists.get(_id);
		RecentActivity.listMusic.add(m);
		textName.setText(m.getTitle());
		textSinger.setText(m.getSinger());
		textEndTime.setText(toTime((int) m.getTime()));

		imgBtnPlay.setImageResource(R.drawable.pause1);

		String url = m.getUrl();
		Uri myUri = Uri.parse(url);
		player = new MediaPlayer();
		player.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			player.setDataSource(getApplicationContext(), myUri);
			player.prepare();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		player.start();
		player.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				// 下一首
				player.reset();
				if (isLoop == true) {
					_id = _id - 1;
				}
				forward();
			}
		});

	}

	private void last() {
		_id = lists.size() - 1;
		Music m = lists.get(lists.size() - 1);
		// SongsActivity.listMusic.add(m);
		textName.setText(m.getTitle());
		textSinger.setText(m.getSinger());
		textEndTime.setText(toTime((int) m.getTime()));

		imgBtnPlay.setImageResource(R.drawable.pause1);

		String url = m.getUrl();
		Uri myUri = Uri.parse(url);
		player = new MediaPlayer();
		player.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			player.setDataSource(getApplicationContext(), myUri);
			player.prepare();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		player.start();
		player.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				// 下一首
				player.reset();
				if (isLoop == true) {
					_id = _id - 1;
				}
				forward();
			}
		});

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		player.reset();
		Music m = lists.get(_id);
		// SongsActivity.listMusic.add(m);
		textName.setText(m.getTitle());
		textSinger.setText(m.getSinger());
		textEndTime.setText(toTime((int) m.getTime()));

		imgBtnPlay.setImageResource(R.drawable.pause1);

		String url = m.getUrl();
		Uri myUri = Uri.parse(url);
		player = new MediaPlayer();
		player.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			player.setDataSource(getApplicationContext(), myUri);
			player.prepare();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		player.start();
		return false;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		this.seekBar.setProgress(seekBar.getProgress());
		player.reset();
		Music music = lists.get(_id);
		textName.setText(music.getTitle());
		textSinger.setText(music.getSinger());
		String url = music.getUrl();
		Uri mUri = Uri.parse(url);
		imgBtnPlay.setImageResource(R.drawable.pause1);
		try {
			player.setDataSource(getApplicationContext(), mUri);
			player.prepare();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		player.seekTo(seekBar.getProgress() * player.getDuration() / 100);
		player.start();

	}

	public void run() {

		boolean isTrue = true;
		while (isTrue == true) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (player == null) {
				isTrue = false;

			} else {
				int position = player.getCurrentPosition();
				Message msg = handler.obtainMessage();
				msg.what = position;
				handler.sendEmptyMessage(position);
			}

		}
	}

	/**
	 * 时间格式转换
	 */
	public String toTime(int time) {

		time /= 1000;
		int minute = time / 60;
		int hour = minute / 60;
		int second = time % 60;
		minute %= 60;
		return String.format("%02d:%02d", minute, second);
	}

	private void setListData() {
		lists = new ArrayList<Music>();

		ContentResolver cr = getApplication().getContentResolver();
		if (cr == null) {
			return;
		}
		// 获取所有歌曲（应该封装该部分代码，作者：demon 2015.06.13大连东软信息学院）
		Cursor cursor = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		if (null == cursor) {
			return;
		}
		if (cursor.moveToFirst()) {
			do {
				Music m = new Music();
				String title = cursor.getString(cursor
						.getColumnIndex(MediaStore.Audio.Media.TITLE));
				String singer = cursor.getString(cursor
						.getColumnIndex(MediaStore.Audio.Media.ARTIST));
				if ("<unknown>".equals(singer)) {
					singer = "未知艺术家";
				}
				String album = cursor.getString(cursor
						.getColumnIndex(MediaStore.Audio.Media.ALBUM));
				long size = cursor.getLong(cursor
						.getColumnIndex(MediaStore.Audio.Media.SIZE));
				long time = cursor.getLong(cursor
						.getColumnIndex(MediaStore.Audio.Media.DURATION));
				String url = cursor.getString(cursor
						.getColumnIndex(MediaStore.Audio.Media.DATA));
				m.setTitle(title);
				m.setSinger(singer);
				m.setAlbum(album);
				m.setSize(size);
				m.setTime(time);
				m.setUrl(url);
				lists.add(m);
			} while (cursor.moveToNext());
		}
	}
}
