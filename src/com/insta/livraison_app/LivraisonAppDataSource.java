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
	
	public ArrayList<Livraison> getDailyLivraisons() {
		ArrayList<Livraison> livraisons = new ArrayList<Livraison>();
		String selectQuery = "SELECT * FROM " + LivraisonAppDataSource.LIVRAISON_TABLE_NAME 
				+ " WHERE  statut = 0"
				+ " ORDER BY date ASC, distance ASC";
		Cursor cursor = bdd.rawQuery(selectQuery, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Livraison livraison = new Livraison();
			livraison.setId_webService(Integer.parseInt(cursor.getString(1)));
			livraison.setAdresse(cursor.getString(2));
			livraison.setNumero(cursor.getString(3));
			livraison.setCodePostal(cursor.getString(4));
			livraison.setVille(cursor.getString(5));
			livraison.setLatitude(cursor.getString(6));
			livraison.setLongitude(cursor.getString(7));
			livraison.setDate(cursor.getString(8));			
			livraison.setClient_id(Integer.parseInt(cursor.getString(9)));
			livraison.setStatut(Integer.parseInt(cursor.getString(10)));
			livraison.setDuration(cursor.getString(11));
			livraison.setDistance(cursor.getString(12));
			livraison.setLivreur_id(Integer.parseInt(cursor.getString(13)));
			livraisons.add(livraison); 
			cursor.moveToNext();
		}
		cursor.close();
		
		return livraisons;
	}
	
	public int getAll() {
		List<String> users = new ArrayList<String>(); 
		String selectQuery = "SELECT * FROM " + LivraisonAppDataSource.LIVRAISON_TABLE_NAME;
		Cursor cursor = bdd.rawQuery(selectQuery, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) { 
			users.add(cursor.getString(0)); 
			cursor.moveToNext();
		}
		cursor.close();
		return cursor.getCount();
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
	
	public long update(int id, int statut){
		ContentValues values = new ContentValues(); 
		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_STATUT, statut);		
		return bdd.update(LivraisonAppDataSource.LIVRAISON_TABLE_NAME, values, 
				LivraisonAppDataSource.LIVRAISON_COLUMN_ID + " = " + id, null);
	}
	
//	public void deleteAll() {
//		bdd.delete(LivraisonAppDataSource.LIVRAISON_TABLE_NAME, "1=1", null);
//	}
	
	public void open () throws SQLException
	{
		bdd = baseSQLite.getWritableDatabase();
	}
	
	public void close()
	{
		baseSQLite.close();
	}
}
