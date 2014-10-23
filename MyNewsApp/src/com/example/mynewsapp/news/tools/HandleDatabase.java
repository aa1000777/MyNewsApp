package com.example.mynewsapp.news.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class HandleDatabase {
	// 数据库存储路径
	static String filePath = "data/data/com.example.mynewsapp/databases/CorpitApp.db";
	// 数据库存放的文件夹 data/data/com.corpit.corpitapp/CorpitApp.db 下面
	static String pathStr = "data/data/com.example.mynewsapp/databases";

	/**
	 * 创建和打开数据库操作
	 * 
	 * @param context
	 * @return
	 */
	public static SQLiteDatabase openDatabase(Context context) {
		File jhPath = new File(filePath);
		// 查看数据库文件是否存在
		if (jhPath.exists()) {
			// 存在则直接返回打开的数据库
			System.out.println("数据库存在！！！！！！！！");
			SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(jhPath,
					null);
			return db;
		} else {
			// 不存在先创建文件夹
			File path = new File(pathStr);
			if (path.mkdir()) {
				System.out.println("创建成功");
			} else {
				System.out.println("创建失败");
			}
			try {
				// 得到资源
				AssetManager am = context.getAssets();
				// 得到数据库的输入流
				InputStream is = am.open("CorpitApp.db");
				// 用输出流写到SDcard上面
				FileOutputStream fos = new FileOutputStream(jhPath);
				// 创建byte数组 用于1KB写一次
				byte[] buffer = new byte[1024];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				// 最后关闭就可以了
				fos.flush();
				fos.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			// 如果没有这个数据库 我们已经把他写到SD卡上了，然后在执行一次这个方法 就可以返回数据库了
			return openDatabase(context);
		}
	}

	/**
	 * 关闭游标和数据库链接
	 * 
	 * @param cursor
	 * @param db
	 */
	public synchronized static void closeCusorAndDB(Cursor cursor,
			SQLiteDatabase db) {
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		if (db != null && db.isOpen()) {
			db.close();
			db = null;
		}
	}
}