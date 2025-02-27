package fr.eni.tp.encheres.dal;

import java.util.List;

import fr.eni.tp.encheres.bo.Categorie;

public interface CategorieDAO {

	// ********** CREATE **********

	void createCategorie(Categorie categorie);

	// ********** READ **********

	Categorie getCategorie(int noCategorie);

	List<Categorie> getCategories();

	// ********** UPDATE **********

	void updateCategorie(Categorie categorie);

	// ********** DELETE **********

	void deleteCategorie(int idCategorie);

	// ********** VALIDATION **********

}
