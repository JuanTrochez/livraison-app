package com.insta.livraison_app;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LivraisonAppDataSource {
	
	private SQLiteDatabase bdd;
	private LivraisonAppBaseOpenHelper baseSQLite;
	
	public LivraisonAppDataSource(Context context){
		baseSQLite = new LivraisonAppBaseOpenHelper(context);
	}
	
	public void open () throws SQLException
	{
		bdd = baseSQLite.getWritableDatabase();
	}
	
	public void close()
	{
		baseSQLite.close();
	}
}
