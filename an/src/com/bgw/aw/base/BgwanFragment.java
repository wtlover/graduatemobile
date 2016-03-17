package com.bgw.aw.base;

import android.widget.Toast;

import com.bg.base.BaseFragment;

public abstract class BgwanFragment extends BaseFragment{

	protected void showToast(final String msg) {
		this.getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT)
						.show();
//				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
			}
		});
	}
	
	protected void runOnUiThread(Runnable r) {
		this.getActivity().runOnUiThread(r);
	}
}
