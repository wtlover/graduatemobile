package com.bgw.an.app.activity.chat.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.widget.TextView;

import com.bgw.an.R;
import com.bgw.an.app.activity.chat.BaseActivity;
import com.bgw.an.app.activity.chat.view.HeaderLayout;
import com.bgw.an.app.activity.chat.view.HeaderLayout.HeaderStyle;
import com.bgw.aw.utils.FeatureUtils;

public class AboutActivity extends BaseActivity {

	private HeaderLayout mHeaderLayout;
	private TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		FeatureUtils.featureUtils(this, getWindow());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		initViews();
		initEvents();
	}

	@Override
	protected void initViews() {
		mHeaderLayout = (HeaderLayout) findViewById(R.id.aboutus_header);
		mHeaderLayout.init(HeaderStyle.DEFAULT_TITLE);
		mHeaderLayout.setDefaultTitle("����AW", null);

		tv = (TextView) findViewById(R.id.tv);
		String about = "<body><br><&nbsp;><&nbsp;>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����android�Ķ๦�ܼ�ʱͨ���ƶ�ƽ̨���幦�ܲ��ְ������ĸ�ģ�飬���ϡ����졢��Ƶ���������ģ�ÿ��ģ����ܲ��õļ����ֶβ�һ��������ȫ���ϸ��ݳ���Ӧ�õ�˼�룬��ϸ������鿴�ñ�ҵ������ġ�</br><br><&nbsp;><&nbsp;>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����android�Ķ๦�ܼ�ʱͨѶƽ̨�Ĺ��ܲ�������������ͨ�ŵ���һ��ģ�飬ͬʱ�ò�Ʒּ�ڽ�һ����ȷ��λ���õĿ�����չ�г��Լ�������ϵͳ�ĵ��û�����ȣ��������ҾͰ����ϵͳ�ǳ�Ϊ��AWϵͳ����awϵͳ�����˺ܶ�����������񣬰ٶȵ�ͼ����λ���Լ�ͨѶ¼�����֣�Դ�������Щ����Ϊ������û�������ȣ���������������ϵ��ַ�������ҽ�����������Щ����������ĸ�ģ�����Ҫ���ܡ�</br></br><br><&nbsp;><&nbsp;>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ע�������߿��ܻ�ɼ�����һЩ�ֻ�ʹ�õ���Ϣ������㰲װ��������ʾ��ͬ�����ǲ�����Щ��Ϣ������������������֤���ǻ��ϸ񱣻��������ݣ����ᱻ���á���������Ľ���ж�ظ����</body>";
		tv.setText(Html.fromHtml(about));
	}

	@Override
	protected void initEvents() {
	}

	@Override
	public void processMessage(Message msg) {
		// TODO Auto-generated method stub

	}

}
