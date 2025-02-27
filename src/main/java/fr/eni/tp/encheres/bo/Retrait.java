package fr.eni.tp.encheres.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Retrait {

	// ****************** ATTRIBUTS D'INSTANCE ***********************************
	
	@NotBlank
	@Size(max = 30)
	private String rue;

	@NotBlank
	@Size(max = 10)
	private String code_postal;

	@NotBlank
	@Size(max = 30)
	private String ville;

	ArticleVendu acticleVendu;
	
	// ****************** CONSTRUCTEURS ***********************************

	public Retrait() {

	}

	public Retrait(String rue, String code_postal, String ville, ArticleVendu acticleVendu) {
		this.rue = rue;
		this.code_postal = code_postal;
		this.ville = ville;
		this.acticleVendu = acticleVendu;
	}
	
	///****************** METHODES ***********************************

	@Override
	public String toString() {
		return "Retrait [rue=" + rue + ", code_postal=" + code_postal + ", ville=" + ville + ", acticleVendu="
				+ acticleVendu + "]";
	}

	// ****************** GETTERS & SETTERS ***********************************
	
	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCode_postal() {
		return code_postal;
	}

	public void setCode_postal(String code_postal) {
		this.code_postal = code_postal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public ArticleVendu getActicleVendu() {
		return acticleVendu;
	}

	public void setActicleVendu(ArticleVendu acticleVendu) {
		this.acticleVendu = acticleVendu;
	}

}
