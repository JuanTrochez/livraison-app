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
	private static final String CLIENT_COLUMN_ID_WEBSERVICE = "id_webservice";
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
	
	public Client getClientById(int id) {
		Client client = new Client();
		
		String selectQuery = "SELECT * FROM " + ClientAppDataSource.CLIENT_TABLE_NAME
							+ " WHERE id = " + id;
		Cursor cursor = bdd.rawQuery(selectQuery, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			client.setId_webService(Integer.parseInt(cursor.getString(1)));
			client.setNom(cursor.getString(2));
			client.setPrenom(cursor.getString(3));
			client.setTelephone(cursor.getString(4));
			client.setEmail(cursor.getString(5));
			cursor.moveToNext();
		}
		cursor.close();
		
		return client;
		
	}
	
	public long insertClient(int id_webservice, String nom, String prenom, String email, String telephone) {
		
		ContentValues values = new ContentValues(); 
		values.put(ClientAppDataSource.CLIENT_COLUMN_ID_WEBSERVICE, id_webservice);
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
