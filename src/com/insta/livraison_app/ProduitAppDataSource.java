package com.insta.livraison_app;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ProduitAppDataSource {
	
	private SQLiteDatabase bdd;
	private LivraisonAppBaseOpenHelper baseSQLite;

	private static final String PRODUIT_TABLE_NAME = "livraison";
	private static final String PRODUIT_COLUMN_ID = "id";
	private static final String PRODUIT_COLUMN_ID_WEBSERVICE = "id_webservice";
	private static final String PRODUIT_COLUMN_REFERENCE = "reference";
	private static final String PRODUIT_COLUMN_QUANTITE = "quantite";
	private static final String PRODUIT_COLUMN_STATUT = "statut";
	private static final String PRODUIT_COLUMN_COMMENTAIRE = "commentaire";
	private static final String PRODUIT_COLUMN_LIVRAISON_ID = "livraison_id";
	
	public ProduitAppDataSource(Context context){
		baseSQLite = new LivraisonAppBaseOpenHelper(context);
	}
	
	
	public int getAll() {
		List<String> users = new ArrayList<String>(); 
		String selectQuery = "SELECT * FROM " + ProduitAppDataSource.PRODUIT_TABLE_NAME;
		Cursor cursor = bdd.rawQuery(selectQuery, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) { 
			users.add(cursor.getString(0)); 
			cursor.moveToNext();
		}
		cursor.close();
		return cursor.getCount();
	}
	
	public long insertProduit(int id_webservice, String reference, String quantite, int statut, 
			String commentaire, int livraison_id) {
		
		ContentValues values = new ContentValues(); 
		values.put(ProduitAppDataSource.PRODUIT_COLUMN_ID_WEBSERVICE, id_webservice);
		values.put(ProduitAppDataSource.PRODUIT_COLUMN_REFERENCE, reference);
		values.put(ProduitAppDataSource.PRODUIT_COLUMN_QUANTITE, quantite);
		values.put(ProduitAppDataSource.PRODUIT_COLUMN_STATUT, statut);
		values.put(ProduitAppDataSource.PRODUIT_COLUMN_COMMENTAIRE, commentaire);
		values.put(ProduitAppDataSource.PRODUIT_COLUMN_LIVRAISON_ID, livraison_id);
		
		return bdd.insert(ProduitAppDataSource.PRODUIT_TABLE_NAME, null, values);
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
