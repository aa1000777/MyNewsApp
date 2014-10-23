package com.example.mynewsapp.news;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mynewsapp.R;
import com.example.mynewsapp.news.tools.BaseFragment;
import com.example.mynewsapp.news.tools.HandleDatabase;
import com.example.mynewsapp.news.tools.News;
import com.example.mynewsapp.news.tools.NewsGlobal;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * 
 *****************************************************
 * <hr>
 * <dt><span class="strong">类功能简介:</span></dt>
 * <dd>news列表页面</dd>
 * <dt><span class="strong">创建时间:</span></dt>
 * <dd>2014-8-6 下午2:34:07</dd>
 * <dt><span class="strong">公司:</span></dt>
 * <dd>CorpIt</dd>
 * @author aa1000777 - Email:aa1000777@qq.com
 *****************************************************
 */
public class NewsListActivity extends BaseFragment {
	private ListView newsList;
	private List<List<News>> news = new ArrayList<List<News>>();
	private NewsAdapter adapter;
	DisplayImageOptions options;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.news, null);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initViews();
	}

	private void initViews() {
		View parent = this.getView();
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.news_loading)
				.showImageForEmptyUri(R.drawable.error)
				.showImageOnFail(R.drawable.error).cacheOnDisc(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
				.displayer(new FadeInBitmapDisplayer(300)).build();
		newsList = (ListView) parent.findViewById(R.id.newsList);
		adapter = new NewsAdapter(context, news, options, this);
		newsList.setAdapter(adapter);
	}

	private void selectNewsData() {
		news = this.getNewsList(context);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		news.clear();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		selectNewsData();
	}

	/**
	 * 从数据库获得新闻列表
	 * 
	 * @param context
	 * @return
	 */
	public synchronized List<List<News>> getNewsList(Context context) {
		List<News> news = new ArrayList<News>();
		SQLiteDatabase db = HandleDatabase.openDatabase(context);
		Cursor cursor = db.query("News", new String[] { "NewsID", "NewsTitle",
				"NewsTime", "NewsSource", "NewsAbstract", "NewsImage",
				"NewsTypeId", "NewsAddtime", "NewsDetail" }, null, null, null,
				null, null);
		NewsGlobal.newsNO = new String[cursor.getCount()];
		if (cursor.moveToFirst()) {
			for (int i = 0; i < cursor.getCount(); i++) {
				News n = new News();
				n.NewsID = (cursor.getString(cursor.getColumnIndex("NewsID")));
				NewsGlobal.newsNO[i] = n.NewsID;// 初始化翻页的id号
				n.NewsTitle = (cursor.getString(cursor
						.getColumnIndex("NewsTitle")));
				n.NewsTime = (cursor.getString(cursor
						.getColumnIndex("NewsTime")));
				n.NewsSource = (cursor.getString(cursor
						.getColumnIndex("NewsSource")));
				n.NewsAbstract = (cursor.getString(cursor
						.getColumnIndex("NewsAbstract")));
				n.NewsImage = (cursor.getString(cursor
						.getColumnIndex("NewsImage")));
				n.NewsTypeId = (cursor.getString(cursor
						.getColumnIndex("NewsTypeId")));
				n.NewsAddtime = (cursor.getString(cursor
						.getColumnIndex("NewsAddtime")));
				n.NewsDetail = cursor.getString(cursor
						.getColumnIndex("NewsDetail"));
				news.add(n);
				if (i % 3 == 2) {
					List<News> datanews = new ArrayList<News>();
					for (int j = 0; j < news.size(); j++) {
						datanews.add(news.get(j));
					}
					this.news.add(datanews);
					news.clear();
				}
				cursor.moveToNext();
			}
			if (news.size() > 0) {
				this.news.add(news);
			}
		}
		HandleDatabase.closeCusorAndDB(cursor, db);
		return this.news;
	}
}
