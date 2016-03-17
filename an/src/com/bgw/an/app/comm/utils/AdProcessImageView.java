package com.bgw.an.app.comm.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.bitmap.core.BitmapDisplayConfig;
import net.tsz.afinal.bitmap.display.Displayer;
import net.tsz.afinal.bitmap.download.Downloader;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bgw.an.R;

public class AdProcessImageView extends ImageView implements Downloader,
		Displayer {
	private static final String TAG = "ProcessImageView";
	private int process = 0;
	private boolean isloading = false;

	private String rememberURL = "";

	public AdProcessImageView(Context context) {
		super(context);
		this.setScaleType(ScaleType.FIT_XY);
	}

	public AdProcessImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AdProcessImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int w = getWidth();
		int h = getHeight();
		String msg = "";
		if (isloading) {
			msg = process + "%";
		} else {
			return;
		}
		if (process < 0) {
			msg = "Í¼Æ¬¼ÓÔØÊ§°Ü";
		}

		Paint tpaint = new Paint();
		tpaint.setTextSize(40);
		tpaint.setColor(Color.BLACK);
		// »æÖÆ°Ù·Ö±È
		canvas.drawText(msg, w / 2 - getPaddingLeft() / 2, h / 2, tpaint);
		invalidate();
	}

	/**
	 * ¼ÓÔØÍ¼Æ¬
	 * 
	 * @param uri
	 */
	public void load(String uri) {
		rememberURL = uri;
		process = 0;

		// if (uri == null || uri.equals("")) {
		// uri = "http://www.idaqi-ss.com/nopic";
		// }

		if (uri == null || uri.equals("")) {
			this.setImageResource(R.drawable.bg_nopic);
		} else {
			FinalBitmap fb = FinalBitmap.create(getContext());
			fb.configDownlader(this);
			fb.configLoadfailImage(R.drawable.bg_nopic);
			// fb.configLoadingImage(R.drawable.bg_nopic);
			fb.display(this, uri);
		}

	}

	/**
	 * ¼ÓÔØÉÏ´ÎÍ¼Æ¬
	 */
	public void loadPrv() {
		if (rememberURL.equals(""))
			return;
		load(rememberURL);
	}

	public byte[] download(String uri) {
		isloading = true;
		HttpClient hc = new DefaultHttpClient();
		HttpGet request = new HttpGet();
		ByteArrayOutputStream output = null;
		try {
			request.setURI(new URI(uri));
			HttpResponse response = hc.execute(request);

			Header[] headers = response.getAllHeaders();
			for (int i = 0; i < headers.length; i++) {
				Header header = headers[i];
				Log.w(TAG, header.getName() + " = " + header.getValue());
			}

			int count = Integer.parseInt(response.getFirstHeader(
					"Content-Length").getValue());
			Log.w(TAG, "count = " + count);

			HttpEntity entity = response.getEntity();
			InputStream input = entity.getContent();
			int LEN = 8196;
			byte[] buffer = new byte[LEN];
			output = new ByteArrayOutputStream();
			int sum = 0;
			while (true) {
				int len = input.read(buffer, 0, LEN);
				if (len <= 0)
					break;
				output.write(buffer, 0, len);

				sum += len;

				process = (int) ((float) sum / (float) count * 100);
			}
			output.flush();
			return output.toByteArray();
		} catch (Exception e) {
			Log.e(TAG, "Í¼Æ¬ÏÂÔØÊ§°Ü", e);
			process = -1;
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
				}
			}
			isloading = false;
		}

		return null;
	}

	public void loadCompletedisplay(View arg0, Bitmap arg1,
			BitmapDisplayConfig arg2) {
		process = 100;
		isloading = false;
	}

	public void loadFailDisplay(View arg0, Bitmap arg1) {
		process = -1;
		isloading = false;
		invalidate();
	}

	@Override
	public void loadCompletedisplay(ImageView arg0, Bitmap arg1,
			BitmapDisplayConfig arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadFailDisplay(ImageView arg0, Bitmap arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean downloadToLocalStreamByUrl(String arg0, OutputStream arg1) {
		// TODO Auto-generated method stub
		return false;
	}

}
