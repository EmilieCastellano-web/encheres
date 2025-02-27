package fr.eni.tp.encheres.dal.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Categorie;
import fr.eni.tp.encheres.bo.Utilisateur;

public class ArticleVenduRowMapper implements RowMapper<ArticleVendu> {

	/**
	 * Méthode rowmapper d'article, ajoutant un utilisateur avec un id et une
	 * catégorie avec un id
	 */
	@Override
	public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException {

		// implementation article

		ArticleVendu a = new ArticleVendu();
		a.setNoArticle(rs.getInt("NO_ARTICLE"));
		a.setNomArticle(rs.getString("NOM_ARTICLE"));
		a.setDescription(rs.getString("DESCRIPTION"));
		a.setDateDebutEncheres(rs.getTimestamp("DATE_DEBUT_ENCHERES").toLocalDateTime());
		a.setDateFinEncheres(rs.getTimestamp("DATE_FIN_ENCHERES").toLocalDateTime());
		a.setMiseAPrix(rs.getInt("PRIX_INITIAL"));
		a.setPrixVente(rs.getInt("PRIX_VENTE"));

		// implementation utilisateur

		Utilisateur u = new Utilisateur();
		u.setNoUtilisateur(rs.getInt("NO_UTILISATEUR"));
		a.setVendeur(u);

		// implementation categorie

		Categorie c = new Categorie();
		c.setNoCategorie(rs.getInt("NO_CATEGORIE"));
		a.setCategorieArticle(c);
		return a;
	}

}
