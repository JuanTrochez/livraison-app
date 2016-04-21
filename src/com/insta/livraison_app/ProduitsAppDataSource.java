package com.insta.livraison_app;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ProduitsAppDataSource {
	
	private SQLiteDatabase bdd;
	private LivraisonAppBaseOpenHelper baseSQLite;

	private static final String PRODUIT_TABLE_NAME = "produit";
	private static final String PRODUIT_COLUMN_ID = "id";
	private static final String PRODUIT_COLUMN_REFERENCE = "reference";
	private static final String PRODUIT_COLUMN_QUANTITE = "quantite";
	private static final String PRODUIT_COLUMN_STATUT = "statut";
	private static final String PRODUIT_COLUMN_COMMENTAIRE = "commentaire";
	private static final String PRODUIT_COLUMN_LIVRAISON_ID = "livraison_id";
	
	public ProduitsAppDataSource(Context context){
		baseSQLite = new LivraisonAppBaseOpenHelper(context);
	}
	
	public ArrayList<Produit> getProduitByLivraisonID(int livraisonid) {
		ArrayList<Produit> listproduit = new ArrayList<Produit>();
		String selectQuery = "SELECT * FROM " + ProduitsAppDataSource.PRODUIT_TABLE_NAME 
				+ " WHERE  livraison_id = livraisonid";
		Cursor cursor = bdd.rawQuery(selectQuery, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Produit produit = new Produit();
			produit.setIdWebService(Integer.parseInt(cursor.getString(1)));
			produit.setReference(cursor.getString(2));
			produit.setQuantite(cursor.getString(3));
			produit.setStatut(Integer.parseInt(cursor.getString(4)));
			produit.setCommentaire(cursor.getString(5));
			listproduit.add(produit); 
			cursor.moveToNext();
		}
		cursor.close();
		
		return listproduit;
	}
	
	
//	public int isLivraisonInDb(int id) {
//		String selectQuery = "SELECT * FROM " + LivraisonAppDataSource.LIVRAISON_TABLE_NAME
//				+ " WHERE " + LivraisonAppDataSource.LIVRAISON_COLUMN_ID_WEBSERVICE + " = " + id;
//		Cursor cursor = bdd.rawQuery(selectQuery, null);
//		
//		return cursor.getCount();		
//	}
//	
//	public long insertLivraison(int id_webservice, String adresse, String numero, String postal, 
//			String ville, String latitude, String longitude, String date, String duration, 
//			String distance, int statut, int client_id, int livreur_id) {
//		
//		ContentValues values = new ContentValues(); 
//		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_ID_WEBSERVICE, id_webservice);
//		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_ADRESSE, adresse);
//		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_NUMERO, numero);
//		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_POSTAL, postal);
//		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_VILLE, ville);
//		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_LATITUDE, latitude);
//		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_LONGITUDE, longitude);
//		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_DATE, date);
//		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_DURATION, duration);
//		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_DISTANCE, distance);
//		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_STATUT, statut);
//		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_CLIENT_ID, client_id);
//		values.put(LivraisonAppDataSource.LIVRAISON_COLUMN_LIVREUR_ID, livreur_id);
//		
//		return bdd.insert(LivraisonAppDataSource.LIVRAISON_TABLE_NAME, null, values);
//	}
	
	
	
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
