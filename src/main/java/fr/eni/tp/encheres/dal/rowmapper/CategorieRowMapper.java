package fr.eni.tp.encheres.dal.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fr.eni.tp.encheres.bo.Categorie;

public class CategorieRowMapper implements RowMapper<Categorie> {

	/**
	 * Méthode rowmapper de catégorie
	 */
	@Override
	public Categorie mapRow(ResultSet rs, int rowNum) throws SQLException {

		// implementation categorie

		Categorie c = new Categorie();
		c.setNoCategorie(rs.getInt("NO_CATEGORIE"));
		c.setLibelle(rs.getString("LIBELLE"));

		return c;
	}

}
