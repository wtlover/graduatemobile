package com.bgw.test;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.an.ServiceHttp;
import com.bgw.an.R;
import com.bgw.an.app.home.entity.GroupChildMode;
import com.bgw.an.app.home.entity.NameMode;

public class MainActivity extends ExpandableListActivity {

	List<String> group; // ���б�
	List<List<String>> child; // ���б�
	ContactsInfoAdapter adapter; // ����������

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // ����Ϊ�ޱ���
		setContentView(R.layout.test_main);
		getExpandableListView()
				.setBackgroundResource(R.drawable.button_pressed);

		initializeData();
		getExpandableListView().setAdapter(new ContactsInfoAdapter());
		getExpandableListView().setCacheColorHint(0); // �����϶��б��ʱ���ֹ���ֺ�ɫ����
		
	}

	/**
	 * ��ʼ���顢���б�����
	 */
	private void initializeData() {
		group = new ArrayList<String>();
		child = new ArrayList<List<String>>();

		String jsonStr1 = "{msg:right,success:true,body:[{groupTitle:�������,content:[{name:������},{name:�׽���},{name:���˺ñ�}]},{groupTitle:Ios,content:[{name:�Ұ�����},{name:����},{name:��˳��}]}]}";
		JSONObject jsonObj1;
		try {
			jsonObj1 = new JSONObject(jsonStr1);
			String jsonstr2 = jsonObj1.getString("body");
			JSONArray jsonjar = new JSONArray(jsonstr2);
			List<GroupChildMode> list = new ArrayList<GroupChildMode>();

			for (int i = 0; i < jsonjar.length(); i++) {
				JSONObject jsonObj2 = new JSONObject();
				jsonObj2 = jsonjar.getJSONObject(i);
				GroupChildMode gm = new GroupChildMode();
				gm.setGroupTitle(jsonObj2.getString("groupTitle"));
				gm.setContent(jsonObj2.getString("content"));
				list.add(gm);
				System.out.println("��������Ϊ��" + list.get(i).getGroupTitle());

				String content = list.get(i).getContent();
				JSONArray jsonjar2 = new JSONArray(content);
				System.out.println("sssss" + content + "\n" + jsonjar2);

				List<NameMode> listContent = new ArrayList<NameMode>();
				List<String> newStr = new ArrayList<String>();
				
				for (int j = 0; j < jsonjar2.length(); j++) {
					JSONObject jsonObj3 = new JSONObject();
					jsonObj3 = jsonjar2.getJSONObject(j);
					NameMode name = new NameMode();
					name.setName(jsonObj3.getString("name"));
					listContent.add(name);
					String name1 = name.getName();
					System.out.println("listContent ���ݱ�������---��" + name1);
					
					newStr.add(listContent.get(j).getName());

					
				}
				child.add(newStr);
				addInfo(list.get(i).getGroupTitle(),new String[] { "male", "138123***", "GuangZhou", "��˳��" });

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addInfo("Andy",
				new String[] { "male", "138123***", "GuangZhou", "��˳��" });
		addInfo("Fairy", new String[] { "female", "138123***", "GuangZhou" });
		addInfo("Jerry", new String[] { "male", "138123***", "ShenZhen" });
		addInfo("Tom", new String[] { "female", "138123***", "ShangHai" });
		addInfo("Bill", new String[] { "male", "138231***", "ZhanJiang" });

	}

	/**
	 * ģ����顢���б��������
	 * 
	 * @param g
	 *            -group
	 * @param c
	 *            -child
	 */
	private void addInfo(String g, String[] c) {

		group.add(g);
		List<String> childitem = new ArrayList<String>();
		for (int i = 0; i < c.length; i++) {
			childitem.add(c[i]);
		}
		child.add(childitem);
	}

	class ContactsInfoAdapter extends BaseExpandableListAdapter {

		// -----------------Child----------------//
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return child.get(groupPosition).get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return child.get(groupPosition).size();
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			String string = child.get(groupPosition).get(childPosition);
			return getGenericView(string);
		}

		// ----------------Group----------------//
		@Override
		public Object getGroup(int groupPosition) {
			return group.get(groupPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public int getGroupCount() {
			return group.size();
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			String string = group.get(groupPosition);
			return getGenericView(string);
		}

		// ������/����ͼ
		public TextView getGenericView(String s) {
			// Layout parameters for the ExpandableListView
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT, 40);

			TextView text = new TextView(MainActivity.this);
			text.setLayoutParams(lp);
			// Center the text vertically
			text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
			// Set the text starting position
			text.setPadding(36, 0, 0, 0);

			text.setText(s);
			return text;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}

	}
}