package com.example.mynewsapp.news.PullToRefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.example.mynewsapp.R;
import com.example.mynewsapp.news.tools.NewsGlobal;

public class PullToRefreshScrollView extends PullToRefreshBase<ScrollView> {

	public PullToRefreshScrollView(Context context) {
		super(context);
	}

	public PullToRefreshScrollView(Context context, int mode) {
		super(context, mode);
	}

	public PullToRefreshScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected ScrollView createRefreshableView(Context context,
			AttributeSet attrs) {
		ScrollView scrollView = new ScrollView(context, attrs);
		scrollView.setId(R.id.webview);
		return scrollView;
	}

	@Override
	protected boolean isReadyForPullDown() {
		return refreshableView.getScrollY() == 0;
	}

	@Override
	protected boolean isReadyForPullUp() {
		ScrollView view = getRefreshableView();
		int off = view.getScrollY() + view.getHeight()
				- view.getChildAt(0).getHeight();
		if (off == 0) {
			NewsGlobal.scrollFlag = false;
			return true;
		} else {
			NewsGlobal.scrollFlag = true;
			return false;
		}
	}
}
