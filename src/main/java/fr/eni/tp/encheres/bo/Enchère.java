package fr.eni.tp.encheres.bo;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;

public class Enchère {
	
	// ****************** ATTRIBUTS D'INSTANCE ***********************************

	private LocalDateTime dateEnchère;

	@Min(value = 0)
	private int montant_enchere;

	ArticleVendu artcicleVendu;
	Utilisateur utilisateur;

	// ****************** CONSTRUCTEURS ***********************************
	
	public Enchère() {

	}

	public Enchère(LocalDateTime dateEnchère, int montant_enchere, ArticleVendu artcicleVendu,
			Utilisateur utilisateur) {
		this.dateEnchère = dateEnchère;
		this.montant_enchere = montant_enchere;
		this.artcicleVendu = artcicleVendu;
		this.utilisateur = utilisateur;
	}

	///****************** METHODES ***********************************
	
	@Override
	public String toString() {
		return "Enchère [dateEnchère=" + dateEnchère + ", montant_enchere=" + montant_enchere + ", artcicleVendu="
				+ artcicleVendu + ", utilisateur=" + utilisateur + "]";
	}
	
	// ****************** GETTERS & SETTERS ***********************************

	public LocalDateTime getDateEnchère() {
		return dateEnchère;
	}

	public void setDateEnchère(LocalDateTime dateEnchère) {
		this.dateEnchère = dateEnchère;
	}

	public int getMontant_enchere() {
		return montant_enchere;
	}

	public void setMontant_enchere(int montant_enchere) {
		this.montant_enchere = montant_enchere;
	}

	public ArticleVendu getArtcicleVendu() {
		return artcicleVendu;
	}

	public void setArtcicleVendu(ArticleVendu artcicleVendu) {
		this.artcicleVendu = artcicleVendu;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}
