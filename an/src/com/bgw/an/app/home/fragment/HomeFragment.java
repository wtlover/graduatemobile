package com.bgw.an.app.home.fragment;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anaw.HessianHello;
import com.bgw.an.Global;
import com.bgw.an.R;
import com.bgw.an.api.ApiBgwAN;
import com.bgw.an.app.activity.home.AddInfoAcitivity;
import com.bgw.an.app.activity.home.UpdateInfoAcitivity;
import com.bgw.an.app.comm.utils.AdImageScroll;
import com.bgw.an.app.comm.utils.AdProcessImageView;
import com.bgw.an.app.home.adapter.ExpandableAdapter;
import com.bgw.an.app.home.adapter.StudyListViewAdapter;
import com.bgw.an.app.home.entity.GroupChildMode;
import com.bgw.an.app.home.entity.NameMode;
import com.bgw.an.app.model.RespondModeArr;
import com.bgw.an.app.model.RespondModeInteger;
import com.bgw.an.app.model.ResponseStudyMode;
import com.bgw.aw.base.BgwanFragment;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * TODO<>
 * 
 * @author 孙顺涛
 * @data: 2015年9月3日 下午3:27:00
 * @version: V1.0
 */
public class HomeFragment extends BgwanFragment implements OnClickListener,
		OnItemLongClickListener, OnItemClickListener {
	@ViewInject(R.id.fragment_imagescroll)
	private AdImageScroll adimagescroll;
	@ViewInject(R.id.expandableListview)
	private ExpandableListView expandableListView;
	private ExpandableAdapter expandableAdapter;

	@ViewInject(R.id.listView)
	private ListView listView;
	private List<ResponseStudyMode> list = new ArrayList<ResponseStudyMode>();

	private List<String> group; // 组列表
	private List<List<String>> child; // 子列表
	private List<List<String>> childUri; // 子URL表
	@ViewInject(R.id.tvShow)
	private TextView tvShow;

	private StudyListViewAdapter adapter;

	private OnExpandableItemSelectedListener mExpandableListener;

	@ViewInject(R.id.baidu_llayout)
	private LinearLayout baidu_llayout;

	private AlertDialog dialog;
	private String url;
	private FinalHttp fp;
	private int _id;

	@Override
	public RequestParams onParams(int paramFlag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onSuccess(String result, int callbackflag) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_home;
	}

	@SuppressLint("NewApi")
	@Override
	public void init() {
		baidu_llayout.setBackgroundColor(0xff42ab82);
		listView.setBackgroundColor(0xff42ab82);
		expandableListView.setBackgroundColor(0xff42ab82);
		initAdFromHttp();

		group = new ArrayList<String>();
		child = new ArrayList<List<String>>();
		childUri = new ArrayList<List<String>>();

		// 模拟添加一组数据
		addInfo("想你",
				new String[] { "想你韩剧介绍", "下初雪的时候你要做什么", "20年后", "她的声音" },
				new String[] {
						"http://www.staryumou.icoc.cc/pd.jsp?id=18&_pp=0_304_3_-1",
						"http://www.staryumou.icoc.cc/pd.jsp?id=18&_pp=0_304_3_-1",
						"http://www.staryumou.icoc.cc/pd.jsp?id=18&_pp=0_304_3_-1",
						"http://www.staryumou.icoc.cc/pd.jsp?id=6&_pp=3_13" });

		// Json数据；
		try {
			initExpandableListViewData();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		expandableAdapter = new ExpandableAdapter(getActivity(), group, child);
		expandableAdapter.notifyDataSetChanged();

		expandableListView.setAdapter(expandableAdapter);
		expandableListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				/*
				 * Intent it = new Intent(Intent.ACTION_VIEW,
				 * Uri.parse("http://www.baidu.com"));
				 * it.setClassName("com.android.browser",
				 * "com.android.browser.BrowserActivity");
				 * getActivity().startActivity(it);
				 */
				String title = (String) expandableAdapter.getChild(
						groupPosition, childPosition);

				String uri = childUri.get(groupPosition).get(childPosition);

				if (mExpandableListener != null) {
					mExpandableListener.onExpandableItemSelected(uri, title);
				}

				return false;
			}
		});

		tvShow.setOnClickListener(this);
		adapter = new StudyListViewAdapter(mContext, list);
		listView.setAdapter(adapter);

		_loadData();

		listView.setOnItemLongClickListener(this);
		listView.setOnItemClickListener(this);

	}

	private void _loadData() {
		// 为了防止重复加载数据，这里做清空处理
		list.clear();

		url = ApiBgwAN.user();
		fp = Global.newInstance().buildHTTP();
		AjaxParams params = Global.newInstance().getParams();
		params.put("cmd", "study");
		fp.post(url, params, new AjaxCallBack<String>() {
			@Override
			public void onFailure(Throwable t, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, strMsg);
				showToast("网络错误");
			}

			@Override
			public void onSuccess(String ret) {
				// TODO Auto-generated method stub
				super.onSuccess(ret);
				if (ret.equals("") || ret == null) {
					return;
				} else {
					RespondModeArr mod = new RespondModeArr();
					Gson gson = new Gson();
					mod = gson.fromJson(ret, RespondModeArr.class);
					if (mod.isSuccess()) {

						for (int i = 0; i < mod.getBody().size(); i++) {
							JsonObject data = mod.getBody().get(i)
									.getAsJsonObject();
							ResponseStudyMode rb = gson.fromJson(
									data.toString(), ResponseStudyMode.class);
							list.add(rb);
						}

					} else {
						showToast(mod.getMsg());
					}
				}
				adapter.notifyDataSetChanged();
			}
		});

	}

	private void android18UIStandard() {
		// Android 4.0后主线程默认只能运行UI
		StrictMode.setThreadPolicy(new

		StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites

		().detectNetwork().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects

				().detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
				.build());
	}

	private void initExpandableListViewData() throws MalformedURLException {
		// TODO Auto-generated method stub
		android18UIStandard();

		// tvShow.setVisibility(View.GONE);
		HessianHello service = new HessianHello();
		String listViewJsonData3 = service.seeHello();
		if (listViewJsonData3 == null || listViewJsonData3.equals("")) {
			// tvShow.setText("服务器连接错误,本地数据" + service.seeHello());
			// Toast.makeText(getActivity(), "服务器连接错误,本地数据",
			// Toast.LENGTH_LONG).show();
			tvShow.setMovementMethod(ScrollingMovementMethod.getInstance());
			listViewJsonData3 = "{msg:right,success:true,"
					+ "body:[{groupTitle:软件工程1,content:[{name:软件工程概述,uri:\"http://baike.baidu.com/link?url=aXfC_4b7E6ZXnNjp_kGlhp-8YT1IyVUoVTxVKAEcK5ZXvjqWaFhm72-HTMPb_1QXhPdprqdzLfOTm_OkhrHzxb0NIHHVrnmIcPFqFtzplf7\"},{name:需求分析,uri:\"http://baike.baidu.com/link?url=Fc-zA3rWewRySdp8SGpL6gRAK0os68xwes0GsKnM9XXLpH4FLSqT8XDcgN3qG6BZUYObSqQzgEG27HMUDek_V_\"}]},"
					+ "{groupTitle:Android,content:[{name:谷歌开放手机联盟,uri:\"http://baike.baidu.com/link?url=OGwdrfhbWY0aivdEmTUhts-b_7AtYJRnBKaz4-zT9RsUmtAzRz1Bx7A_a-wltNVjsCgS1iDA9QJM8JGftn3qS_\"},{name:android,uri:\"http://baike.baidu.com/link?url=uhbFT9DjPPnS2Du1A1J8AO7TweGx2L5ZeSCS2bG1zrBydQI7tVd1JQiw2DL6DRUKS8lXSrpI54j4hWdDbGNbMc2NpepUipeqFBDSfEha547\"}]},"
					+ "{groupTitle:IOS,content:[{name:C语言,uri:\"http://baike.baidu.com/link?url=Exg8TmTduFQXFkAvjo0Tp8c8BizT374qi66TRCF-Hu7DiZaYgCGdgfYTHtLl3j_xEmPbCmCddqkF2Ai1NH1Zxq\"},{name:Objective-C,uri:\"http://baike.baidu.com/link?url=bBml6YKG1MCMdSGHWA0xDshjx0kLXIb3wjEZ2bhLROoTDlpmfs74chyR1T_sqvegvBmjXshW__3iIX6_kJDQLK\"}]},"
					+ "{groupTitle:PHP,content:[{name:PHP介绍,uri:\"http://baike.baidu.com/link?url=DA1RPJuOVv90EUyjjTmqhRfWzcHrplYENKQnyDtyj4Nqwh0IMdTMQ33YajrUht7G2gDTSTcd6_gLddTwzSN7twfN9F4POEWZA33oqbKsSQy\"},{name:语言基础,uri:\"http://baike.baidu.com/link?url=fArBSJwH3WAHmM0BR4240fumyAeDO64R0kJQqPQ6ZrMkpziX2ll2XgbOIb8agiiLLZWdJkW7hkuVmBcx_aBdhq\"}]},"
					+ "{groupTitle:计算机网络,content:[{name:计算机网络概述,uri:\"http://baike.baidu.com/link?url=J3OVbI_eI67KkjPsLBU39oFCJWzc9J0iVggIPqJRvQWW4TClhsqIppgGR11VFNl7h9u-xd9OQl_tlVbnb5t--PIYOGg8aT1GfuJ4FI0WRzS\"},{name:网络协议,uri:\"http://baike.baidu.com/link?url=fLi7_usLCNREiepUxWqn7QVi_h1N6jNFUdKG2AVLTa5W7EcB9oHVM_JIAyeI7wtmTStmvFxWiJehPkw2bUl6qq\"}]},"
					+ "{groupTitle:信息技术与安全,content:[{name:信安概述,uri:\"http://baike.baidu.com/link?url=RY4QR3Esm7Pth_JUP2x6y07BHJejVWP22RDt9YmA10U3OSeRcCeMigYabtTCZX1PyB_Tc4LiMd5lv88aR5YDQHQglQ0ftV4P_KOTZ1YpDSihth4NYQOaWShWAoSPC-BPgt8QgmaW5DBuaiPv25dOMZbq35ryJLwlsKKh37AU4F_j5GVMuzlbuuP3OPXve9da__QE_i0UsMUa0Fsp-x__Ha\"},{name:国家信息安全,uri:\"http://staryumou.icoc.cc/\"}]},"
					+ "{groupTitle:JavaWeb,content:[{name:JavaWeb介绍,uri:\"http://baike.baidu.com/link?url=56e-LRKBxaUZkwUMY6wukhVktGraPbSBO0JJbd5sXjHJAayKf4nFFavW_UmMP9b6N8AFWbxa6dkCzvqzTwgTp_\"},{name:HTML,uri:\"http://bgwan.blog.163.com/blog/static/239301016201572010013222/\"}]}]}";
		} else {
			Toast.makeText(getActivity(), "服务器连接成功,服务器端数据", Toast.LENGTH_LONG)
					.show();
			// tvShow.setText("服务器连接成功,服务器端数据");
			listViewJsonData3.toString().trim();
			tvShow.setMovementMethod(ScrollingMovementMethod.getInstance());
			String jsonStr1 = "{msg:right,success:true,body:[{groupTitle:软件工程,content:[{name:美丽的,uri:\"http://bgwan.blog.163.com/blog/static/239301016201542811544840/\"},{name:白江湾,uri:\"http://bgwan.blog.163.com/blog/static/2393010162015728111244178/\"},{name:好人好报,uri:\"http://bgwan.blog.163.com/blog/static/239301016201542811544840/\"}]},{groupTitle:Ios,content:[{name:我爱的人,uri:\"http://bgwan.blog.163.com/blog/static/239301016201542811544840/\"},{name:湾湾,uri:\"http://bgwan.blog.163.com/blog/static/239301016201542811544840/\"},{name:孙顺涛,uri:\"http://bgwan.blog.163.com/blog/static/239301016201542811544840/\"}]}]}";

			String listViewJsonData = "{msg:right,success:true,"
					+ "body:[{groupTitle:软件工程1,content:[{name:软件工程概述1},{name:需求分析},{name:概要设计},{name:详细设计},{name:编写代码},{name:软件测试},{name:软件交付},{name:用户验收},{name:软件维护}]},"
					+ "{groupTitle:Android,content:[{name:谷歌android概述},{name:开发环境搭建},{name:hello-world},{name:android四大组件介绍},{name:Activity使用},{name:Brodcast-Receceiver},{name:Content-Provider},{name:Service服务},{name:多线程与并发},{name:NFC技术},{name:android数据通信}]},"
					+ "{groupTitle:IOS,content:[{name:C语言},{name:Objective-C},{name:IOS开发环境},{name:IOS基础},{name:Swift语言},{name:UIkit框架UI设计},{name:MVC代理模式},{name:IOS网络},{name:IOS高级}]},"
					+ "{groupTitle:PHP,content:[{name:PHP介绍},{name:语言基础},{name:函数},{name:Web技术},{name:数据库},{name:图像}]},"
					+ "{groupTitle:计算机网络,content:[{name:计算机网络概述},{name:网络协议}]},"
					+ "{groupTitle:信息技术与安全,content:[{name:信安概述},{name:国家信息安全}]},"
					+ "{groupTitle:JavaWeb,content:[{name:JavaWeb介绍},{name:开发环境搭建},{name:HTML技术},{name:HTML5介绍},{name:CSS},{name:JAVASCRIPT}]}]}";
		}

		JSONObject jsonObj1;

		try {
			// 如果不可以解析则用这个方法。
			jsonObj1 = new JSONObject(listViewJsonData3);
			String jsonstr2 = jsonObj1.getString("body");
			/*
			 * RespondMode mode = new RespondMode(); Gson gson = new Gson();
			 * mode = gson.fromJson(listViewJsonData3, RespondMode.class);
			 * jsonstr2 = mode.getBody();
			 */
			JSONArray jsonjar = new JSONArray(jsonstr2);
			List<GroupChildMode> list = new ArrayList<GroupChildMode>();

			for (int i = 0; i < jsonjar.length(); i++) {
				JSONObject jsonObj2 = new JSONObject();
				jsonObj2 = jsonjar.getJSONObject(i);
				GroupChildMode gm = new GroupChildMode();
				gm.setGroupTitle(jsonObj2.getString("groupTitle"));
				gm.setContent(jsonObj2.getString("content"));
				list.add(gm);

				String content = list.get(i).getContent();
				JSONArray jsonjar2 = new JSONArray(content);
				// System.out.println("服务器端Json数据-----》》》" + content + "\n"
				// + jsonjar2);

				List<NameMode> listContent = new ArrayList<NameMode>();
				List<String> newStr = new ArrayList<String>();
				List<String> newUri = new ArrayList<String>();

				for (int j = 0; j < jsonjar2.length(); j++) {
					JSONObject jsonObj3 = new JSONObject();
					jsonObj3 = jsonjar2.getJSONObject(j);
					NameMode name = new NameMode();
					name.setUri(jsonObj3.getString("uri"));
					name.setName(jsonObj3.getString("name"));
					listContent.add(name);
					newStr.add(listContent.get(j).getName());
					newUri.add(listContent.get(j).getUri());
				}
				child.add(newStr);
				childUri.add(newUri);
				group.add(list.get(i).getGroupTitle());
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addInfo(String g, String[] c, String[] cu) {

		group.add(g);
		List<String> childitem = new ArrayList<String>();
		List<String> childitemuri = new ArrayList<String>();
		for (int i = 0; i < c.length; i++) {
			childitem.add(c[i]);
			childitemuri.add(cu[i]);
		}
		child.add(childitem);
		childUri.add(childitemuri);
	}

	private void initAdFromHttp() {
		AdProcessImageView pi = new AdProcessImageView(mContext);
		pi.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		pi.load("http://n.sinaimg.cn/transform/20150409/MmUK-awzuney2862336.jpg");

		AdProcessImageView pi1 = new AdProcessImageView(mContext);
		pi1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		pi1.load("http://img2.imgtn.bdimg.com/it/u=3551775816,1384678260&fm=11&gp=0.jpg");

		AdProcessImageView pi2 = new AdProcessImageView(mContext);
		pi2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		pi2.load("http://n1.itc.cn/img8/wb/recom/2015/12/18/145042054836865343.JPEG");

		AdProcessImageView pi3 = new AdProcessImageView(mContext);
		pi3.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		pi3.load("http://img5.pcpop.com/ArticleImages/fnw/2015/1202/8d624684-ec4e-4fd0-8d68-5cdc6b134eac.jpg");

		pi.setOnClickListener(new MyOnClickListnener(0));
		pi1.setOnClickListener(new MyOnClickListnener(1));
		pi2.setOnClickListener(new MyOnClickListnener(2));
		pi3.setOnClickListener(new MyOnClickListnener(3));

		List<AdProcessImageView> pl = new ArrayList<AdProcessImageView>();
		pl.add(pi);
		pl.add(pi1);
		pl.add(pi2);
		pl.add(pi3);

		List<String> lt = new ArrayList<String>();
		lt.add("中国好声音第三季-《年轮》-张碧晨");
		lt.add("中国中央电视台-《时间都去哪儿了》-张碧晨献唱");
		lt.add("好声音韩文歌曲-《爱你的宿命》-张碧晨");
		lt.add("青春医生电视原声-《一吻之间》-张碧晨");

		adimagescroll.setViewPager(pl, lt);
	}

	private class MyOnClickListnener implements OnClickListener {
		int num = 0;

		public MyOnClickListnener(int num) {
			this.num = num;
		}

		@Override
		public void onClick(View v) {
			Toast.makeText(mContext, "" + num, 0).show();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvShow:
			Intent intent = new Intent(mContext, AddInfoAcitivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}

	}

	public interface OnExpandableItemSelectedListener {
		public void onExpandableItemSelected(String uri, String title);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			mExpandableListener = (OnExpandableItemSelectedListener) activity;

		} catch (ClassCastException e) {
			// TODO: handle exception
			throw new ClassCastException(activity.toString()
					+ "must implement OnExpandableItemSelectedListener");
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			final int position, long id) {
		if (position < 0 || list.size() < 0 || position >= list.size()) {
			return true;
		}
		final int index = position - 1;
		_id = list.get(position).get_id();

		dialog = new android.app.AlertDialog.Builder(mContext).create();
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(R.layout.wt_dialog_itemlongclick);
		TextView tvDialog1 = (TextView) window
				.findViewById(R.id.dialog_lvitem_tv1);
		TextView tvDialog2 = (TextView) window
				.findViewById(R.id.dialog_lvitem_tv2);

		tvDialog1.setText("编辑");
		tvDialog2.setText("删除");
		tvDialog1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String title = list.get(position).getName();
				String url = list.get(position).getUrl();
				int _id = list.get(position).get_id();

				Intent intent = new Intent(mContext, UpdateInfoAcitivity.class);
				Bundle extras = new Bundle();
				extras.putString("title", title);
				extras.putString("url", url);
				extras.putInt("_id", _id);
				intent.putExtras(extras);
				startActivity(intent);
				dialog.dismiss();
			}

		});

		tvDialog2.setOnClickListener(new OnClickListener() {

			private int i = 0;

			@Override
			public void onClick(View arg0) {
				if (i == 1) {
					dialog.dismiss();
					AjaxParams params = Global.newInstance().getParams();
					params.put("cmd", "deletedata");
					params.put("_id", _id + "");
					fp.post(url, params, new AjaxCallBack<String>() {
						@Override
						public void onFailure(Throwable t, String strMsg) {
							super.onFailure(t, strMsg);
							showToast("网络错误");
						}

						@Override
						public void onSuccess(String ret) {
							// TODO Auto-generated method stub
							super.onSuccess(ret);
							if (ret.equals("") || ret == null) {
							} else {
								RespondModeInteger mod = new RespondModeInteger();
								Gson gson = new Gson();
								mod = gson.fromJson(ret,
										RespondModeInteger.class);
								if (mod.isSuccess()) {
									if (mod.getBody() == 1) {
										list.remove(index + 1);
										adapter.notifyDataSetChanged();
									} else {
										showToast("删除失败");
									}
								} else {
									showToast(mod.getMsg());
								}

							}
						}
					});
				} else {
					Toast.makeText(mContext, "你确定删除吗？再点击一次试试",
							Toast.LENGTH_LONG).show();
					i++;
				}

			}
		});

		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		String title = list.get(position).getName();
		String uri = list.get(position).getUrl();

		if (mExpandableListener != null) {
			mExpandableListener.onExpandableItemSelected(uri, title);
		}

	}
}