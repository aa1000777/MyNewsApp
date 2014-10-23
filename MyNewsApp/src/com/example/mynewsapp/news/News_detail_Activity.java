package com.example.mynewsapp.news;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.mynewsapp.R;
import com.example.mynewsapp.news.PullToRefresh.PullToRefreshBase.OnRefreshListener;
import com.example.mynewsapp.news.PullToRefresh.PullToRefreshScrollView;
import com.example.mynewsapp.news.tools.BaseFragment;
import com.example.mynewsapp.news.tools.News;
import com.example.mynewsapp.news.tools.NewsGetDataFromSQL;
import com.example.mynewsapp.news.tools.NewsGlobal;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * 
 *****************************************************
 * <hr>
 * <dt><span class="strong">类功能简介:</span></dt>
 * <dd>news详细页面</dd>
 * <dt><span class="strong">创建时间:</span></dt>
 * <dd>2014-8-6 下午2:33:47</dd>
 * <dt><span class="strong">公司:</span></dt>
 * <dd>CorpIt</dd>
 * @author aa1000777 - Email:aa1000777@qq.com
 *****************************************************
 */
public class News_detail_Activity extends BaseFragment {
	PullToRefreshScrollView mPullRefreshScrollView;
	ScrollView mScrollView;
	LayoutInflater inflater;
	View view;
	int displayHeight;
	int displayWidth;
	private TextView newsTitleTV;
	private TextView newsDetailTV;
	private TextView newsAuthorTV;
	private TextView newsDateTV;
	private ImageView img;
	private String newsTitle;
	private String html;

	DisplayImageOptions options;
	public ImageLoader imageLoader = null;
	/**
	 * Handler
	 */
	Handler mHandler = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.news_detail_activity, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.news_loading)
				.showImageForEmptyUri(R.drawable.error)
				.showImageOnFail(R.drawable.error).cacheOnDisc(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
				.displayer(new FadeInBitmapDisplayer(300)).build();
		this.imageLoader = ImageLoader.getInstance();
		View parent = this.getView();
		this.displayHeight = this.getActivity().getWindowManager()
				.getDefaultDisplay().getHeight();
		this.displayWidth = this.getActivity().getWindowManager()
				.getDefaultDisplay().getWidth();
		inflater = LayoutInflater.from(context);
		mPullRefreshScrollView = (PullToRefreshScrollView) parent
				.findViewById(R.id.pull_refresh_scrollview);
		System.out.println("空异常：" + mPullRefreshScrollView);
		mScrollView = mPullRefreshScrollView.getRefreshableView();
		mPullRefreshScrollView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// Do work to refresh the list here.
				new GetDataTask().execute();
			}
		});
		view = inflater.inflate(R.layout.news_detail_content, null);
		// 设置最小宽度
		view.setMinimumHeight(this.displayHeight);
		view.setMinimumWidth(this.displayWidth);
		initViews();
		mScrollView.addView(view);
		mHandler = new Handler() {
			@Override
			public void dispatchMessage(Message msg) {
				switch (msg.what) {
				case 123:
					// mScrollView.smoothScrollTo(0, 0);
					RefreshScroll(NewsGlobal.NEWSNO, false);
					break;
				case 456:
					// mScrollView.smoothScrollTo(0, 0);
					RefreshScroll(NewsGlobal.NEWSNO, true);
					break;
				}
				super.dispatchMessage(msg);
			}
		};
	}

	/**
	 * 更新数据
	 * 
	 * @param newsno
	 * @param b
	 */
	private void RefreshScroll(String newsno, boolean flag) {
		News news = NewsGetDataFromSQL.getNewsByNewsNo(context, newsno);
		FragmentTransaction ft = this.getFragmentManager().beginTransaction();
		if (flag) {
			// 上一页
			ft.setCustomAnimations(R.anim.news_bottom_s_in,
					R.anim.news_bottom_s_out, R.anim.news_up_s_in,
					R.anim.news_up_s_out);
		} else {
			// 下一页
			ft.setCustomAnimations(R.anim.news_up_in, R.anim.news_up_out,
					R.anim.news_bottom_in, R.anim.news_bottom_out);
		}
		Bundle b = new Bundle();
		b.putString("Author", news.NewsTypeId);
		b.putString("Date", news.NewsAddtime);
		b.putString("title", news.NewsTitle);
		b.putString("NewsNO", news.NewsID);
		b.putString("NewsDetail", news.NewsDetail);
		try {
			String imgStr = news.NewsImage;
			if (imgStr != null && !"".equals(imgStr)) {
				String imgURL = NewsGlobal.IMAGEURI + imgStr;
				b.putString("Img", imgURL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		News_detail_Activity ndf = new News_detail_Activity();
		ndf.setArguments(b);
		ft.replace(R.id.content, ndf).commit();
	}

	private void initViews() {
		newsAuthorTV = (TextView) view.findViewById(R.id.newsAuthorTV);
		newsDateTV = (TextView) view.findViewById(R.id.newsDateTV);
		newsTitleTV = (TextView) view.findViewById(R.id.newsTitleTV);
		newsDetailTV = (TextView) view.findViewById(R.id.newsDetailTV);
		img = (ImageView) view.findViewById(R.id.newsImageIV);
		newsAuthorTV.setText(getArguments().getString("Author"));
		newsDateTV.setText(getArguments().getString("Date"));
		newsTitle = getArguments().getString("title");
		html = getArguments().getString("NewsDetail");
		String imgURL = getArguments().getString("Img");
		if (NewsGlobal.NEWSNO.equals(imgURL) || imgURL == null) {
			img.setVisibility(View.GONE);
		} else {
			imageLoader.displayImage(imgURL, img, options);
			img.setVisibility(View.VISIBLE);
		}
		newsTitleTV.setText(newsTitle);
		if (html != null && !"".equals(html.trim())) {
			html = html.replaceAll("src=\"upfiles/",
					"src=\"file:///data/data/com.corpit.essilordubai/image/");
		} else {
			html = " ";
		}
		newsDetailTV.setText(Html.fromHtml(html));
	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {
		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			if (NewsGlobal.scrollFlag) {
				// 下拉
				for (int i = 0; i < NewsGlobal.newsNO.length; i++) {
					if (NewsGlobal.NEWSNO.equals(NewsGlobal.newsNO[i])) {
						if (i > 0) {
							NewsGlobal.NEWSNO = NewsGlobal.newsNO[i - 1];
							mHandler.sendEmptyMessage(456);
							break;
						} else {
							NewsGlobal.newsPageFlag = "-1";
						}
					}
				}
			} else {
				// 上拉
				for (int i = 0; i < NewsGlobal.newsNO.length; i++) {
					if (NewsGlobal.NEWSNO.equals(NewsGlobal.newsNO[i])) {
						if (i < NewsGlobal.newsNO.length - 1) {
							NewsGlobal.NEWSNO = NewsGlobal.newsNO[i + 1];
							mHandler.sendEmptyMessage(123);
							break;
						} else {
							NewsGlobal.newsPageFlag = "1";
						}
					}
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			super.onPostExecute(result);
			mPullRefreshScrollView.onRefreshComplete();
		}
	}
}
