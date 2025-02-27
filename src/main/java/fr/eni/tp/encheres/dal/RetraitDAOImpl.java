package fr.eni.tp.encheres.dal;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import org.springframework.stereotype.Repository;

import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Retrait;
import fr.eni.tp.encheres.dal.rowmapper.RetraitRowMapper;

@Repository
public class RetraitDAOImpl implements RetraitDAO {

	// ****************** CONSTANTES ***********************************

	// ********** CREATE **********

	private static final String INSERT = "insert into retraits (no_article, rue, code_postal, ville) values (:noArticle, :rue, :codePostal, :ville)";

	// ********** READ **********

	private static final String SELECT_BY_ID = "SELECT * FROM RETRAITS WHERE no_article = :no_article";

	// ********** UPDATE **********

	private static final String UPDATE = "update retraits set rue = :rue, code_postal = :codePostal, ville = :ville where no_article = :idArticle";

	// ********** DELETE **********

	private static final String DELETE = "delete from retraits where no_article = :idArticle";

	// ********** VALIDATION **********

	// ****************** ATTRIBUT INSTANCES ***********************************

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	// ****************** CONSTRUCTEURS***********************************

	public RetraitDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	// ****************** METHODES ***********************************

	// ********** CREATE **********

	/**
	 * Méthode insérant en BDD un nouvel lieu de retrait lié à un article
	 */
	@Override
	public void createRetrait(ArticleVendu article) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("noArticle", article.getNoArticle());
		map.addValue("rue", article.getLieuRetrait().getRue());
		map.addValue("codePostal", article.getLieuRetrait().getCode_postal());
		map.addValue("ville", article.getLieuRetrait().getVille());

		namedParameterJdbcTemplate.update(INSERT, map);

	}

	// ********** READ **********

	/**
	 * Méthode permettant de chercher un retrait en base grâce à au numéro d'un
	 * article (noArticle) Return un retrait
	 */
	@Override
	public Retrait getRetraitByIdArticle(int noArticle) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("no_article", noArticle);
		return namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID, map, new RetraitRowMapper());
	}

	// ********** UPDATE **********

	@Override
	public void updateRetrait(ArticleVendu article) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("rue", article.getLieuRetrait().getRue());
		map.addValue("codePostal", article.getLieuRetrait().getCode_postal());
		map.addValue("ville", article.getLieuRetrait().getVille());
		map.addValue("idArticle", article.getNoArticle());
		namedParameterJdbcTemplate.update(UPDATE, map);
	}

	// ********** DELETE **********

	@Override
	public void deleteRetrait(int idArticle) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idArticle", idArticle);
		namedParameterJdbcTemplate.update(DELETE, map);

	}

	// ********** VALIDATION **********

}
