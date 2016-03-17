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
		mHeaderLayout.setDefaultTitle("关于AW", null);

		tv = (TextView) findViewById(R.id.tv);
		String about = "<body><br><&nbsp;><&nbsp;>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;基于android的多功能及时通信移动平台总体功能部分包含了四个模块，资料、聊天、视频、个人中心，每个模块可能采用的技术手段不一样，但完全符合根据程序及应用的思想，详细内容请查看该毕业设计论文。</br><br><&nbsp;><&nbsp;>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;基于android的多功能即时通讯平台的功能并不仅仅限制在通信的这一个模块，同时该产品旨在进一步明确定位更好的开发拓展市场以及提高这个系统的的用户体验度，我们暂且就把这个系统昵称为（AW系统），aw系统集成了很多的其他功能像，百度地图，定位，以及通讯录，音乐，源码服务，这些都是为了提高用户的体验度，并且有完整的联系地址，方便大家交流；但是这些都不是这个四个模块的主要功能。</br></br><br><&nbsp;><&nbsp;>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;备注，开发者可能会采集您的一些手机使用的信息，如果你安装该软件则表示您同意我们采用这些信息的声明，我们向您保证我们会严格保护您的数据，不会被盗用。如果不放心建议卸载该软件</body>";
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
