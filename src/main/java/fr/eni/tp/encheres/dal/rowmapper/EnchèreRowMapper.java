package fr.eni.tp.encheres.dal.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Enchère;
import fr.eni.tp.encheres.bo.Utilisateur;

public class EnchèreRowMapper implements RowMapper<Enchère> {

	/**
	 * Méthode rowmapper d'enchère, ajoutant un utilisateur avec un id et un article
	 * avec un id
	 */
	@Override
	public Enchère mapRow(ResultSet rs, int rowNum) throws SQLException {

		// implementation enchère

		Enchère e = new Enchère();
		e.setMontant_enchere(rs.getInt("MONTANT_ENCHERE"));
		e.setDateEnchère(rs.getTimestamp("DATE_ENCHERE").toLocalDateTime());

		// implementation utilisateur

		Utilisateur u = new Utilisateur();
		u.setNoUtilisateur(rs.getInt("NO_UTILISATEUR"));
		e.setUtilisateur(u);

		// implementation article

		ArticleVendu a = new ArticleVendu();
		a.setNoArticle(rs.getInt("NO_ARTICLE"));
		e.setArtcicleVendu(a);

		return e;
	}

}
