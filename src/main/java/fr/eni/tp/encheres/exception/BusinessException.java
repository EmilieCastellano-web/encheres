package fr.eni.tp.encheres.exception;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends Exception {

	private List<String> clesErreurs = new ArrayList<String>();

	public void addErreur(String cleErreur) {
		if (cleErreur == null) {
			clesErreurs = new ArrayList<String>();

		}
		clesErreurs.add(cleErreur);
	}

	public List<String> getClesErreurs() {
		return clesErreurs;
	}

}