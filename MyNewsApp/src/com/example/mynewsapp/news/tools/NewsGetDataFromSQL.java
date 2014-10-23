package com.example.mynewsapp.news.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NewsGetDataFromSQL {
	private static final String NEWS_TABLE = "News";

	/**
	 * 向数据库中插入NEWS_TABLE
	 * 
	 * @param context
	 * @param values
	 */
	public synchronized static void insertNews(Context context,
			ContentValues values) {
		SQLiteDatabase db = HandleDatabase.openDatabase(context);
		db.insert(NEWS_TABLE, null, values);
		HandleDatabase.closeCusorAndDB(null, db);
	}

	/**
	 * 清空NEWS_TABLE 下载列表
	 * 
	 * @param context
	 */
	public synchronized static void deleteNews(Context context) {
		SQLiteDatabase db = HandleDatabase.openDatabase(context);
		db.delete(NEWS_TABLE, null, null);
		HandleDatabase.closeCusorAndDB(null, db);
	}

	/**
	 * 根据newsNo查询news
	 * 
	 * @param context
	 * @param NewsNo
	 * @return
	 */
	public synchronized static News getNewsByNewsNo(Context context,
			String NewsNo) {
		News n = new News();
		if (NewsNo != null && !"".equals(NewsNo)) {
			SQLiteDatabase db = HandleDatabase.openDatabase(context);
			Cursor cursor = db.rawQuery("select * from News Where NewsID = ?",
					new String[] { NewsNo });
			if (cursor.moveToFirst()) {
				for (int i = 0; i < cursor.getCount(); i++) {
					n.NewsID = (cursor.getString(cursor
							.getColumnIndex("NewsID")));
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
					cursor.moveToNext();
				}
				HandleDatabase.closeCusorAndDB(cursor, db);
			}
		}
		return n;
	}
}