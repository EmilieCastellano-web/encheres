package fr.eni.tp.encheres.bo;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Categorie {
	
	// ****************** ATTRIBUTS D'INSTANCE ***********************************
	
	private int noCategorie;

	@NotBlank
	@Size(max = 30)
	private String libelle;
	List<ArticleVendu> articlesVendus = new ArrayList<ArticleVendu>();

	// ****************** CONSTRUCTEURS ***********************************
	
	public Categorie() {
	}

	public Categorie(int noCategorie, String libelle, List<ArticleVendu> articlesVendus) {
		this.noCategorie = noCategorie;
		this.libelle = libelle;
		this.articlesVendus = articlesVendus;
	}

	//****************** METHODES ***********************************
	
	@Override
	public String toString() {
		return "Categorie [noCategorie=" + noCategorie + ", libelle=" + libelle + ", articlesVendus=" + articlesVendus
				+ "]";
	}
	
	// ****************** GETTERS & SETTERS ***********************************

	public int getNoCategorie() {
		return noCategorie;
	}

	public void setNoCategorie(int noCategorie) {
		this.noCategorie = noCategorie;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public List<ArticleVendu> getArticlesVendus() {
		return articlesVendus;
	}

	public void setArticlesVendus(List<ArticleVendu> articlesVendus) {
		this.articlesVendus = articlesVendus;
	}

}
