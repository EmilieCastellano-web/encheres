package fr.eni.tp.encheres.bll;

import java.util.List;

import fr.eni.tp.encheres.bo.Categorie;
import fr.eni.tp.encheres.exception.BusinessException;

public interface CategorieService {

	// ********** CREATE **********

	void createCategorie(Categorie categorie);

	// ********** READ **********

	Categorie getCategorie(int idCategorie);

	List<Categorie> getCategories();

	// ********** UPDATE **********

	void updateCategorie(Categorie categorie);

	// ********** DELETE **********

	void deleteCategorie(int idCategorie) throws BusinessException;
}
