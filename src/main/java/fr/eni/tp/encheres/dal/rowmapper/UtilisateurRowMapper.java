package fr.eni.tp.encheres.dal.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fr.eni.tp.encheres.bo.Utilisateur;

public class UtilisateurRowMapper implements RowMapper<Utilisateur> {

	/**
	 * Méthode rowmapper d'utilisateur
	 */
	@Override
	public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {

		// implementation utilisateur

		Utilisateur u = new Utilisateur();
		u.setNoUtilisateur(rs.getInt("NO_UTILISATEUR"));
		u.setPseudo(rs.getString("PSEUDO"));
		u.setNom(rs.getString("NOM"));
		u.setPrenom(rs.getString("PRENOM"));
		u.setEmail(rs.getString("EMAIL"));
		u.setTelephone(rs.getString("TELEPHONE"));
		u.setRue(rs.getString("RUE"));
		u.setCodePostal(rs.getString("CODE_POSTAL"));
		u.setVille(rs.getString("VILLE"));
		u.setCredit(rs.getInt("CREDIT"));
		u.setAdministrateur(rs.getBoolean("ADMINISTRATEUR"));

		return u;
	}

}
