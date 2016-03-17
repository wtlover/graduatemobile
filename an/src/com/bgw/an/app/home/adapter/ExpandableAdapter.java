package com.bgw.an.app.home.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bgw.an.R;

/**
 * @author 作者 staryumou@163.com:
 * @version 创建时间：2015年9月4日 下午4:14:43 类说明 :
 */
public class ExpandableAdapter extends BaseExpandableListAdapter {
	private Context context;
	private List<String> group;
	private List<List<String>> child;

	public ExpandableAdapter(Context context, List<String> group,
			List<List<String>> child) {
		super();
		// TODO Auto-generated constructor stub
		this.context = context;
		this.group = group;
		this.child = child;
	}

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
		LinearLayout childLayout = new LinearLayout(context);
		childLayout.setOrientation(LinearLayout.HORIZONTAL);
		String string = child.get(groupPosition).get(childPosition);
		TextView childTextView = getGenericView(string);
		childTextView.setGravity(Gravity.CENTER_VERTICAL);
		childTextView.setPadding(20, 0, 0, 0);
		childLayout.addView(childTextView);
		return childLayout;

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
		LinearLayout groupLayout = new LinearLayout(context);
		groupLayout.setOrientation(LinearLayout.VERTICAL);
		// groupLayout.setOrientation(0);o水平
		// TODO Auto-generated method stub通过上面的方法，实例化一个TextView对象
		String string = group.get(groupPosition);
		TextView groupText = getGenericView(string);
		groupText.setTextColor(Color.BLACK);
		// groupText.setBackground(childIcons[groupPosition].toString());
		int wPaddingLeft = (int) ((context.getResources().getDisplayMetrics().widthPixels)*0.1);
		
		groupText.setPadding(wPaddingLeft, 0, 0, 0);
		groupLayout.addView(groupText);

		return groupLayout;
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

	// 创建组/子视图

	@SuppressLint("ResourceAsColor")
	public TextView getGenericView(String s) {
		TextView showText = new TextView(context);
		@SuppressWarnings("deprecation")
		AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, 100);
		showText.setLayoutParams(layoutParams);
		showText.setGravity(Gravity.CENTER_VERTICAL);
		showText.setTextSize(13);
		showText.setTextColor(R.color.red);
		showText.setText(s + "");
		return showText;
	}

}
