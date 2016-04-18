package com.insta.livraison_app;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LivraisonAppDataSource {
	
	private SQLiteDatabase bdd;
	private LivraisonAppBaseOpenHelper baseSQLite;

	private static final String LIVRAISON_TABLE_NAME = "livraison";
	private static final String LIVRAISON_COLUMN_ID = "id";
	private static final String LIVRAISON_COLUMN_ID_WEBSERVICE = "id_webservice";
	private static final String LIVRAISON_COLUMN_ADRESSE = "adresse";
	private static final String LIVRAISON_COLUMN_NUMERO = "numero";
	private static final String LIVRAISON_COLUMN_POSTAL = "code_postal";
	private static final String LIVRAISON_COLUMN_VILLE = "ville";
	private static final String LIVRAISON_COLUMN_LATITUDE = "latitude";
	private static final String LIVRAISON_COLUMN_LONGITUDE = "longitude";
	private static final String LIVRAISON_COLUMN_DATE = "date";
	private static final String LIVRAISON_COLUMN_DURATION = "duration";
	private static final String LIVRAISON_COLUMN_DISTANCE = "distance";
	private static final String LIVRAISON_COLUMN_STATUT = "statut";
	private static final String LIVRAISON_COLUMN_CLIENT_ID = "client_id";
	private static final String LIVRAISON_COLUMN_LIVREUR_ID = "livreur_id";
	
	public LivraisonAppDataSource(Context context){
		baseSQLite = new LivraisonAppBaseOpenHelper(context);
	}
	
	public ArrayList<Object> getDailyLivraisons() {
		ArrayList<Object> livraisons = new ArrayList<Object>();
		String selectQuery = "SELECT * FROM " + LivraisonAppDataSource.LIVRAISON_TABLE_NAME 
				+ " WHERE  satut = 0"
				+ " ORDER BY date ASC, distance ASC";
		Cursor cursor = bdd.rawQuery(selectQuery, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			livraisons.add(cursor); 
			cursor.moveToNext();
		}
		cursor.close();
		
		return livraisons;
	}
	
	public List<String> getAll() {
		List<String> users = new ArrayList<String>(); 
		String selectQuery = "SELECT * FROM " + LivraisonAppDataSource.LIVRAISON_TABLE_NAME;
		Cursor cursor = bdd.rawQuery(selectQuery, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) { 
			users.add(cursor.getString(0)); 
			cursor.moveToNext();
		}
		cursor.close();
		return users;
	}
	
	public int isLivraisonInDb(int id) {
		String selectQuery = "SELECT * FROM " + LivraisonAppDataSource.LIVRAISON_TABLE_NAME
				+ " WHERE " + LivraisonAppDataSource.LIVRAISON_COLUMN_ID_WEBSERVICE + " = " + id;
		Cursor cursor = bdd.rawQuery(selectQuery, null);
		
		return cursor.getCount();		
	}
	
	public long insertLivraison(int id_webservice, String adresse, String numero, String postal, 
			String ville, String latitude, String longitude, String date, String duration, 
			String distance, int statut, int client_id, int livreur_id) {
		
		ContentValues values = new ContentValues(); 
		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_ID_WEBSERVICE, id_webservice);
		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_ADRESSE, adresse);
		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_NUMERO, numero);
		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_POSTAL, postal);
		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_VILLE, ville);
		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_LATITUDE, latitude);
		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_LONGITUDE, longitude);
		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_DATE, date);
		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_DURATION, duration);
		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_DISTANCE, distance);
		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_STATUT, statut);
		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_CLIENT_ID, client_id);
		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_LIVREUR_ID, livreur_id);
		
		return bdd.insert(LivraisonAppDataSource.LIVRAISON_TABLE_NAME, null, values);
	}
	
	public void deleteAll() {
		bdd.delete(LivraisonAppDataSource.LIVRAISON_TABLE_NAME, "1=1", null);
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
