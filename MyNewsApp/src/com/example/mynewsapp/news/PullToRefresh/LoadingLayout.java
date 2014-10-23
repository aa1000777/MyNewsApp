package com.example.mynewsapp.news.PullToRefresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mynewsapp.R;
import com.example.mynewsapp.news.tools.NewsGlobal;

public class LoadingLayout extends FrameLayout {

	static final int DEFAULT_ROTATION_ANIMATION_DURATION = 150;

	private final ImageView headerImage;
	private final TextView headerText;

	private String pullLabel;
	private String refreshingLabel;
	private String releaseLabel;

	private final Animation rotateAnimation, resetRotateAnimation;

	public LoadingLayout(Context context, final int mode) {
		super(context);
		ViewGroup header = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.news_pull_to_refresh_header, this);
		headerText = (TextView) header.findViewById(R.id.pull_to_refresh_text);
		headerImage = (ImageView) header.findViewById(R.id.pull_to_refresh_image);
		final Interpolator interpolator = new LinearInterpolator();
		rotateAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
		        0.5f);
		rotateAnimation.setInterpolator(interpolator);
		rotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
		rotateAnimation.setFillAfter(true);

		resetRotateAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f,
		        Animation.RELATIVE_TO_SELF, 0.5f);
		resetRotateAnimation.setInterpolator(interpolator);
		resetRotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
		resetRotateAnimation.setFillAfter(true);

		this.releaseLabel = NewsGlobal.refreshingLabel;
		this.refreshingLabel = NewsGlobal.refreshingLabel;
		setTextmain(pullLabel);
		switch (mode) {
			case PullToRefreshBase.MODE_PULL_UP_TO_REFRESH:
				headerImage.setImageResource(R.drawable.news_pulltorefresh_up_arrow);
				break;
			case PullToRefreshBase.MODE_PULL_DOWN_TO_REFRESH:
			default:
				headerImage.setImageResource(R.drawable.news_pulltorefresh_down_arrow);
				break;
		}
	}

	public void reset() {
		System.out.println("__reset_----");
		headerText.setText(pullLabel);
		headerImage.setVisibility(View.VISIBLE);
	}
	public void releaseToRefresh(String string) {
		System.out.println("releaseToRefresh----"+string);
		headerText.setText(string);
		if (NewsGlobal.pullLabelUPend.equals(string) || NewsGlobal.pullLabelDOWNend.equals(string)) {
			headerImage.setVisibility(View.INVISIBLE);
		}else{
			headerImage.clearAnimation();
			headerImage.startAnimation(rotateAnimation);
		}

	}

	public void setPullLabel(String pullLabel) {
		System.out.println("setPullLabel----"+pullLabel);
		this.pullLabel = pullLabel;
	}

	public void refreshing() {
		headerText.setText(refreshingLabel);
		headerImage.clearAnimation();
		headerImage.setVisibility(View.INVISIBLE);
	}

	public void setRefreshingLabel(String refreshingLabel) {
		this.refreshingLabel = refreshingLabel;
	}

	public void setReleaseLabel(String releaseLabel) {
		this.releaseLabel = releaseLabel;
	}

	public void pullToRefresh(String string) {
		System.out.println("pullToRefresh----"+string);
		headerText.setText(string);
		if (NewsGlobal.pullLabelUPend.equals(string) || NewsGlobal.pullLabelDOWNend.equals(string)) {
			headerImage.setVisibility(View.INVISIBLE);
		}else{
			headerImage.clearAnimation();
			headerImage.startAnimation(resetRotateAnimation);
		}

	}

	public void setTextColor(int color) {
		headerText.setTextColor(color);
	}
	public void setTextmain(String str) {
		headerText.setText(str);
	}
}
