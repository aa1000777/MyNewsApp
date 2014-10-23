package com.example.mynewsapp.news;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mynewsapp.R;
import com.example.mynewsapp.news.tools.News;
import com.example.mynewsapp.news.tools.NewsGlobal;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 *****************************************************
 * <hr>
 * <dt><span class="strong">类功能简介:</span></dt>
 * <dd>news适配器</dd>
 * <dt><span class="strong">创建时间:</span></dt>
 * <dd>2014-8-6 下午2:33:59</dd>
 * <dt><span class="strong">公司:</span></dt>
 * <dd>CorpIt</dd>
 * @author aa1000777 - Email:aa1000777@qq.com
 *****************************************************
 */
public class NewsAdapter extends BaseAdapter {
	private List<List<News>> news;
	private List<News> lnews;
	private Context mContext;
	private DisplayImageOptions options;
	private ViewHolder holder;
	public ImageLoader imageLoader = null;
	NewsListActivity newsListActivity;

	public NewsAdapter(Context mContext, List<List<News>> news,
			DisplayImageOptions options, NewsListActivity newsListActivity) {
		this.mContext = mContext;
		this.news = news;
		this.options = options;
		this.imageLoader = ImageLoader.getInstance();
		this.newsListActivity = newsListActivity;
	}

	@Override
	public int getCount() {
		return news == null ? 0 : news.size();
	}

	@Override
	public Object getItem(int position) {
		return news == null ? null : news.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup arg2) {
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.news_item, null);
			// 取得四个布局
			holder.new_item_1 = (LinearLayout) convertView
					.findViewById(R.id.new_item_1);
			holder.new_item_s = (LinearLayout) convertView
					.findViewById(R.id.new_item_s);
			holder.new_item_2 = (LinearLayout) convertView
					.findViewById(R.id.new_item_2);
			holder.new_item_3 = (LinearLayout) convertView
					.findViewById(R.id.new_item_3);
			holder.newsContent1_IV = (ImageView) convertView
					.findViewById(R.id.newsContent1_IV);
			holder.newsTitle1_TV = (TextView) convertView
					.findViewById(R.id.newsTitle1_TV);
			holder.newsAuthor1_TV = (TextView) convertView
					.findViewById(R.id.newsAuthor1_TV);
			holder.newsDate1_TV = (TextView) convertView
					.findViewById(R.id.newsDate1_TV);
			holder.newsContent1_TV = (TextView) convertView
					.findViewById(R.id.newsContent1_TV);
			holder.newsContent2_IV = (ImageView) convertView
					.findViewById(R.id.newsContent2_IV);
			holder.newsTitle2_TV = (TextView) convertView
					.findViewById(R.id.newsTitle2_TV);
			holder.newsAuthor2_TV = (TextView) convertView
					.findViewById(R.id.newsAuthor2_TV);
			holder.newsDate2_TV = (TextView) convertView
					.findViewById(R.id.newsDate2_TV);
			holder.newsContent2_TV = (TextView) convertView
					.findViewById(R.id.newsContent2_TV);
			holder.newsContent3_IV = (ImageView) convertView
					.findViewById(R.id.newsContent3_IV);
			holder.newsTitle3_TV = (TextView) convertView
					.findViewById(R.id.newsTitle3_TV);
			holder.newsAuthor3_TV = (TextView) convertView
					.findViewById(R.id.newsAuthor3_TV);
			holder.newsDate3_TV = (TextView) convertView
					.findViewById(R.id.newsDate3_TV);
			holder.newsContent3_TV = (TextView) convertView
					.findViewById(R.id.newsContent3_TV);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.new_item_1.setOnClickListener(new onClick(position));
		holder.new_item_2.setOnClickListener(new onClick(position));
		holder.new_item_3.setOnClickListener(new onClick(position));
		lnews = news.get(position);
		if (lnews.size() == 1) {
			holder.new_item_s.setVisibility(View.GONE);
			listnews1();
		} else if (lnews.size() == 2) {
			holder.new_item_s.setVisibility(View.VISIBLE);
			holder.new_item_2.setVisibility(View.VISIBLE);
			holder.new_item_3.setVisibility(View.GONE);
			listnews1();
			listnews2();
		} else if (lnews.size() == 3) {
			holder.new_item_s.setVisibility(View.VISIBLE);
			holder.new_item_2.setVisibility(View.VISIBLE);
			holder.new_item_3.setVisibility(View.VISIBLE);
			listnews1();
			listnews2();
			listnews3();
		}
		return convertView;

	}

	private void listnews1() {
		if (lnews.get(0).NewsImage != null
				&& !"".equals(lnews.get(0).NewsImage)) {
			try {
				String imgStr = lnews.get(0).NewsImage;
				if (imgStr != null && !"".equals(imgStr)) {
					String imgURL = NewsGlobal.IMAGEURI + imgStr;
					imageLoader.displayImage(imgURL, holder.newsContent1_IV,
							options);
				}
				holder.newsContent1_TV.setVisibility(View.GONE);
				holder.newsContent1_IV.setVisibility(View.VISIBLE);
			} catch (Exception e) {
			}
		} else {
			holder.newsContent1_IV.setVisibility(View.GONE);
			holder.newsContent1_TV.setVisibility(View.VISIBLE);
			holder.newsContent1_TV.setText(lnews.get(0).NewsAbstract);
		}
		holder.newsTitle1_TV.setText(lnews.get(0).NewsTitle);
		holder.newsAuthor1_TV.setText(lnews.get(0).NewsTypeId + "");
		holder.newsDate1_TV.setText(lnews.get(0).NewsAddtime);
	}

	private void listnews2() {
		if (lnews.get(1).NewsImage != null
				&& !"".equals(lnews.get(1).NewsImage)) {
			try {
				String imgStr = lnews.get(1).NewsImage;
				if (imgStr != null && !"".equals(imgStr)) {
					String imgURL = NewsGlobal.IMAGEURI + imgStr;
					imageLoader.displayImage(imgURL, holder.newsContent2_IV,
							options);
				}
				holder.newsContent2_TV.setVisibility(View.GONE);
				holder.newsContent2_IV.setVisibility(View.VISIBLE);
			} catch (Exception e) {
			}
		} else {
			holder.newsContent2_IV.setVisibility(View.GONE);
			holder.newsContent2_TV.setVisibility(View.VISIBLE);
			holder.newsContent2_TV.setText(lnews.get(1).NewsAbstract);
		}
		holder.newsTitle2_TV.setText(lnews.get(1).NewsTitle);
		holder.newsAuthor2_TV.setText(lnews.get(1).NewsTypeId + "");
		holder.newsDate2_TV.setText(lnews.get(1).NewsAddtime);

	}

	private void listnews3() {
		if (lnews.get(2).NewsImage != null
				&& !"".equals(lnews.get(2).NewsImage)) {
			try {
				String imgStr = lnews.get(2).NewsImage;
				if (imgStr != null && !"".equals(imgStr)) {
					String imgURL = NewsGlobal.IMAGEURI + imgStr;
					imageLoader.displayImage(imgURL, holder.newsContent3_IV,
							options);
				}
				holder.newsContent3_TV.setVisibility(View.GONE);
				holder.newsContent3_IV.setVisibility(View.VISIBLE);
			} catch (Exception e) {
			}
		} else {
			holder.newsContent3_IV.setVisibility(View.GONE);
			holder.newsContent3_TV.setVisibility(View.VISIBLE);
			holder.newsContent3_TV.setText(lnews.get(2).NewsAbstract);

		}
		holder.newsTitle3_TV.setText(lnews.get(2).NewsTitle);
		holder.newsAuthor3_TV.setText(lnews.get(2).NewsTypeId + "");
		holder.newsDate3_TV.setText(lnews.get(2).NewsAddtime);
	}

	class onClick implements OnClickListener {
		List<News> onlnews;
		int position;

		public onClick(int position) {
			// TODO Auto-generated constructor stub
			this.position = position;
			this.onlnews = news.get(position);
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.new_item_1:
				newsonclick(onlnews, 0);
				NewsGlobal.NEWSNO = onlnews.get(0).NewsID;
				break;
			case R.id.new_item_2:
				newsonclick(onlnews, 1);
				NewsGlobal.NEWSNO = onlnews.get(1).NewsID;
				break;
			case R.id.new_item_3:
				newsonclick(onlnews, 2);
				NewsGlobal.NEWSNO = onlnews.get(2).NewsID;
				break;
			default:
				break;
			}
		}
	}

	private void newsonclick(List<News> lnews2, int i) {
		FragmentTransaction ft = newsListActivity.getFragmentManager()
				.beginTransaction();
		ft.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out,
				R.anim.push_right_in, R.anim.push_right_out);
		Bundle b = new Bundle();
		b.putString("Author", lnews2.get(i).NewsTypeId);
		b.putString("Date", lnews2.get(i).NewsAddtime);
		b.putString("title", lnews2.get(i).NewsTitle);
		b.putString("NewsNO", lnews2.get(i).NewsID);
		b.putString("NewsDetail", lnews2.get(i).NewsDetail);
		try {
			String imgStr = lnews2.get(i).NewsImage;
			if (imgStr != null && !"".equals(imgStr)) {
				String imgURL = NewsGlobal.IMAGEURI + imgStr;
				b.putString("Img", imgURL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		News_detail_Activity ndf = new News_detail_Activity();
		ndf.setArguments(b);
		ft.addToBackStack(null).replace(R.id.content, ndf).commit();
	}

	final static class ViewHolder {
		TextView newsTitle1_TV;
		TextView newsAuthor1_TV;
		TextView newsDate1_TV;
		TextView newsContent1_TV;
		ImageView newsContent1_IV;
		TextView newsTitle2_TV;
		TextView newsAuthor2_TV;
		TextView newsDate2_TV;
		TextView newsContent2_TV;
		ImageView newsContent2_IV;

		TextView newsTitle3_TV;
		TextView newsAuthor3_TV;
		TextView newsDate3_TV;
		TextView newsContent3_TV;
		ImageView newsContent3_IV;

		LinearLayout new_item_1;
		LinearLayout new_item_s;
		LinearLayout new_item_2;
		LinearLayout new_item_3;
	}
}