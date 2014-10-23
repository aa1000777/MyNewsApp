package com.example.mynewsapp.news.tools;

public class NewsGlobal {

	public static String NEWSURL = "";// 下载news数据的URL
	public static String NEWIMAGESURL = "";// 下载news图片的URL
	public static String NEWSNO = "";// 当前页面ID
	public static boolean scrollFlag;// 用于判断scroll是上拉还是下拉
	public static String newsNO[];// 用于用于翻页记录
	public static String newsPageFlag = "0";// 用于控制下拉时提示信息的显示：-1为第一页，0为可翻页、1为最后一页
	public static String pullLabelUP = "上拉进入下一页";
	public static String pullLabelDOWN = "下拉进入上一页";
	public static String refreshingLabel = "正在加载...";
	public static String releaseLabel = "松开刷新...";
	public static String pullLabelUPend = "这是最后一页";
	public static String pullLabelDOWNend = "这是第一页";
	
	
	
	/*
	 * 图片路径的类型：
	 * 1、 "assets://路径/文件名"
	 * 2、"https://路径/文件名"
	 * 3、"http://路径/文件名"
	 * 4、"file:///路径/文件名
	 * 5、"drawable://" + R.drawable.ic_launcher
	 */
	public static final String IMAGEURI = "assets://";// 图片路径

	public static class Config {
		public static final boolean DEVELOPER_MODE = false;
	}
}
