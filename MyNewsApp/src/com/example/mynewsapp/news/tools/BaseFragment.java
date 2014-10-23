package com.example.mynewsapp.news.tools;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;

/**
 * Fragment页面父类
 * 
 * @author 
 * 
 */
public class BaseFragment extends Fragment {
	public static FragmentManager fragmentManager;
	public static Context context;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.context = this.getActivity();
		fragmentManager = this.getFragmentManager();
//		int managerCount=fragmentManager.getBackStackEntryCount();
//		System.out.println("看下有几个："+fragmentManager.getBackStackEntryCount());
//		for (int i = 0; i < managerCount; i++) {
//			Fragment f=fragmentManager.findFragmentById(fragmentManager.getBackStackEntryAt(0).getId());
//			System.out.println("-----------FragmentTag:"+f.getTag());
//		}
	}

	public void exit() {
		this.getActivity().finish();
	}
}
