package com.insta.livraison_app;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ClientAppDataSource {
	
	private SQLiteDatabase bdd;
	private LivraisonAppBaseOpenHelper baseSQLite;

	private static final String CLIENT_TABLE_NAME = "client";
	private static final String CLIENT_COLUMN_ID = "id";
	private static final String CLIENT_COLUMN_NOM = "nom";
	private static final String CLIENT_COLUMN_PRENOM = "prenom";
	private static final String CLIENT_COLUMN_TELEPHONE = "telephone";
	private static final String CLIENT_COLUMN_EMAIL = "email";
	
	public ClientAppDataSource(Context applicationContext) {
		// TODO Auto-generated constructor stub
		baseSQLite = new LivraisonAppBaseOpenHelper(applicationContext);
	}
	
	public ArrayList<Object> getAll() {
		ArrayList<Object> livraisons = new ArrayList<Object>();
		String selectQuery = "SELECT * FROM " + ClientAppDataSource.CLIENT_TABLE_NAME;
		Cursor cursor = bdd.rawQuery(selectQuery, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			livraisons.add(cursor); 
			cursor.moveToNext();
		}
		cursor.close();
		
		return livraisons;
	}
	
	public long insertClient(int id, String nom, String prenom, String email, String telephone) {
		
		ContentValues values = new ContentValues(); 
		values.put(ClientAppDataSource.CLIENT_COLUMN_ID, id);
		values.put(ClientAppDataSource.CLIENT_COLUMN_NOM, nom);
		values.put(ClientAppDataSource.CLIENT_COLUMN_PRENOM, prenom);
		values.put(ClientAppDataSource.CLIENT_COLUMN_EMAIL, email);
		values.put(ClientAppDataSource.CLIENT_COLUMN_TELEPHONE, telephone);
		
		return bdd.insert(ClientAppDataSource.CLIENT_TABLE_NAME, null, values);
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
