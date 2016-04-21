package com.insta.livraison_app;

import java.util.ArrayList;

public class Produit {
	
	private int idWebService;
	private String reference;
	private String quantite;
	private int statut;
	private String commentaire;
	private int livraisonId;
	public static ArrayList<Produit> produit = new ArrayList<Produit>();
	
	public int getIdWebService() {
		return idWebService;
	}
	public void setIdWebService(int idWebService) {
		this.idWebService = idWebService;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getQuantite() {
		return quantite;
	}
	public void setQuantite(String quantite) {
		this.quantite = quantite;
	}
	public int getStatut() {
		return statut;
	}
	public void setStatut(int statut) {
		this.statut = statut;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	public int getLivraisonId() {
		return livraisonId;
	}
	public void setLivraisonId(int livraisonId) {
		this.livraisonId = livraisonId;
	}
	
	

}
