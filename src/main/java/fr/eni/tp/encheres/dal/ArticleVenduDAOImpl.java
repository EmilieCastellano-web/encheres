package fr.eni.tp.encheres.dal;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.dal.rowmapper.ArticleVenduRowMapper;

@Repository
public class ArticleVenduDAOImpl implements ArticleVenduDAO {

	// ****************** CONSTANTES ***********************************

	// ********** CREATE **********

	private static final String INSERT = "insert into articles_vendus (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, no_utilisateur, no_categorie) values (:nom, :description, :dateDebut, :dateFin, :prixInitial, :idUtilisateur, :idCategorie)";

	// ********** READ **********

	private static final String SELECT_BY_ID = "SELECT  no_categorie, prix_vente, no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, no_utilisateur FROM ARTICLES_VENDUS WHERE no_article = :id";
	private static final String SELECT_ALL = "select no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie from articles_vendus";
	private static final String SELECT_BY_STRING = "select no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie from articles_vendus where nom_article like :filtreSql";
	private static final String SELECT_BY_ID_CATEGORIE = "select no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie from articles_vendus where no_categorie = :idCategorie";
	private static final String SELECT_BY_STRING_AND_ID_CATEGORIE = "select no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie from articles_vendus where nom_article like :filtreSql and no_categorie = :idCategorie";
	private static final String CHECK_ARTICLES_VENDUS = "SELECT * FROM ARTICLES_VENDUS a WHERE a.no_utilisateur = :id";

	// ********** UPDATE **********

	private static final String UPDATE = "update articles_vendus set nom_article = :nom, description = :description, date_debut_encheres = :dateDebut, date_fin_encheres = :dateFin, prix_initial = :prixInitial, no_categorie = :idCategorie where no_article = :idArticle";

	// ********** DELETE **********

	private static final String DELETE = "delete from articles_vendus where no_article = :idArticle";

	// ********** VALIDATION **********

	private static final String COUNT_BY_ID_CATEGORIE = "select count(*) from articles_vendus where no_categorie = :idCategorie";

	// ****************** ATTRIBUT INSTANCES ***********************************

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	// ****************** CONSTRUCTEURS***********************************

	public ArticleVenduDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	// ****************** METHODES ***********************************

	// ********** CREATE **********

	/**
	 * Méthode insérant en BDD un nouvel article tout en changeant l'id de cet
	 * article avec l'id en BDD
	 */
	@Override
	public void createArticle(ArticleVendu article) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("nom", article.getNomArticle());
		map.addValue("description", article.getDescription());
		map.addValue("dateDebut", article.getDateDebutEncheres());
		map.addValue("dateFin", article.getDateFinEncheres());
		map.addValue("prixInitial", article.getMiseAPrix());
		map.addValue("idUtilisateur", article.getVendeur().getNoUtilisateur());
		map.addValue("idCategorie", article.getCategorieArticle().getNoCategorie());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(INSERT, map, keyHolder);

		if (keyHolder != null && keyHolder.getKey() != null) {

			article.setNoArticle(keyHolder.getKey().intValue());
		}
	}

	// ********** READ **********

	@Override
	public ArticleVendu getArticleById(int id) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", id);
		return namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID, map, new ArticleVenduRowMapper());
	}

	@Override
	public List<ArticleVendu> getArticles() {
		return namedParameterJdbcTemplate.query(SELECT_ALL, new ArticleVenduRowMapper());

	}

	/**
	 * Méthode retournant la liste des articles filtrée par un mot-clé
	 */
	@Override
	public List<ArticleVendu> getArticlesFiltresByString(String filtreSql) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("filtreSql", filtreSql);
		return namedParameterJdbcTemplate.query(SELECT_BY_STRING, map, new ArticleVenduRowMapper());
	}

	/**
	 * Méthode retournant la liste des articles filtrée par l'id d'une catégorie
	 */
	@Override
	public List<ArticleVendu> getArticlesFiltresById(int idCategorie) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idCategorie", idCategorie);
		return namedParameterJdbcTemplate.query(SELECT_BY_ID_CATEGORIE, map, new ArticleVenduRowMapper());
	}

	/**
	 * Méthode retournant la liste des articles filtrée par un mot-clé et par l'id
	 * d'une catégorie
	 */
	@Override
	public List<ArticleVendu> getArticlesFiltresByStringAndId(String filtreSql, int idCategorie) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("filtreSql", filtreSql);
		map.addValue("idCategorie", idCategorie);
		return namedParameterJdbcTemplate.query(SELECT_BY_STRING_AND_ID_CATEGORIE, map, new ArticleVenduRowMapper());
	}

	/**
	 * Méthode permettant de trouver tous les ArticleVendu correspondant à l'id de
	 * l'utilisateur Return List ArticleVendu
	 */
	@Override
	public List<ArticleVendu> getArticlesByIdUser(int idUser) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", idUser);

		return namedParameterJdbcTemplate.query(CHECK_ARTICLES_VENDUS, map, new ArticleVenduRowMapper());

	}

	// ********** UPDATE **********

	@Override
	public void updateArticle(ArticleVendu article) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("nom", article.getNomArticle());
		map.addValue("description", article.getDescription());
		map.addValue("dateDebut", article.getDateDebutEncheres());
		map.addValue("dateFin", article.getDateFinEncheres());
		map.addValue("prixInitial", article.getMiseAPrix());
		map.addValue("idCategorie", article.getCategorieArticle().getNoCategorie());
		map.addValue("idArticle", article.getNoArticle());
		namedParameterJdbcTemplate.update(UPDATE, map);
	}

	// ********** DELETE **********

	@Override
	public void deleteArticle(int idArticle) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idArticle", idArticle);
		namedParameterJdbcTemplate.update(DELETE, map);

	}

	// ********** VALIDATION **********

	@Override
	public int getCountArticleByIdCategorie(int idCategorie) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idCategorie", idCategorie);
		return namedParameterJdbcTemplate.queryForObject(COUNT_BY_ID_CATEGORIE, map, Integer.class);
	}

}
