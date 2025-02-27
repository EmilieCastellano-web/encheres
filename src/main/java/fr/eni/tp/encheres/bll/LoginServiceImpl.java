package fr.eni.tp.encheres.bll;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import fr.eni.tp.encheres.bo.PasswordToken;
import fr.eni.tp.encheres.bo.Utilisateur;

import fr.eni.tp.encheres.dal.UtilisateurDAO;
import fr.eni.tp.encheres.exception.BusinessException;

@Service
public class LoginServiceImpl implements LoginService {

	private UtilisateurDAO utilisateurDAO;

	public LoginServiceImpl(UtilisateurDAO utilisateurDAO) {
		this.utilisateurDAO = utilisateurDAO;

	}

	// ********** CREATE **********

	/**
	 * Créer le lien de pour reset le mdp Vérifie si l'email est bien existant dans
	 * la base de donnée Création d'un token : génère un id universel aléatoire de
	 * nombre + convertit en String
	 * 
	 * @Return String
	 */
	@Override
	public String creerResetPasswordLink(String email) throws BusinessException {
		BusinessException be = new BusinessException();
		Utilisateur user = new Utilisateur();
		PasswordToken passwordToken = new PasswordToken();
		boolean valide = validerUtilisateurEmail(email, be);
		try {
			if (!valide) {
				user = utilisateurDAO.getUtilisateurByEmail(email);

				String token = UUID.randomUUID().toString();
				Timestamp dateExpiration = new Timestamp(System.currentTimeMillis() + 3600000);
				LocalDateTime dateTime = dateExpiration.toLocalDateTime();
				passwordToken.setDateExpiration(dateTime);
				passwordToken.setToken(token);
				passwordToken.setNoUtilisateur(user.getNoUtilisateur());

				utilisateurDAO.sauvegarderPasswordResetToken(passwordToken);

			}

		} catch (DataAccessException e) {
			e.printStackTrace();
			// be.addErreur("erreur.user.notfound");
			throw be;
		}

		return passwordToken.getToken();

	}

	// ********** READ **********

	/**
	 * Trouver un Utilisateur grâce à son pseudo
	 * 
	 * @return Utilisateur
	 */
	@Override
	public Utilisateur charger(String pseudo) {
		return this.utilisateurDAO.getUtilisateurByPseudo(pseudo);

	}

	// ********** UPDATE **********

	/**
	 * Validation des mots de passe entrer par l'User, verifie le token si valable +
	 * encodage mdp avant envoie en base de donnée
	 * 
	 * @param String
	 * @param String
	 * @param String
	 */
	@Override
	public void resetPassword(String token, String password, String passwordConfirm) throws BusinessException {
		BusinessException be = new BusinessException();

		// Utilisateur utilisateur = utilisateurDAO.readByToken(token);
		PasswordToken passwordToken = utilisateurDAO.findToken(token);
		Utilisateur user = utilisateurDAO.getUtilisateurById(passwordToken.getNoUtilisateur());
		boolean valide = validerConfirmMdp(passwordConfirm, password, be);
		valide &= validerDateToken(passwordToken, be);
		try {
			if (valide) {
				String mdpEncode = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password);
				utilisateurDAO.resetPassword(passwordToken, mdpEncode);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			// be.addErreur("erreur.password.save");
			throw be;
		}
	}

	// ********** DELETE **********

	// ********** VALIDATION **********

	/**
	 * Méthode permettant de verifier si l'email est unique Appel DAO pour récupérer
	 * le nombre d'email associer au email entrer par l'Utilisateur
	 * 
	 * @param email
	 * @param be
	 * @return boolean
	 */
	private boolean validerUtilisateurEmail(String email, BusinessException be) {

		boolean valide = true;
		int nbEmail = utilisateurDAO.getCountUtilisateurByEmail(email);
		if (nbEmail == 1) {
			valide = false;
			be.addErreur("erreur.utilisateur.email.exist");
		}

		return valide;

	}

	/**
	 * Méthode permettant de comparer le mot de passe et la confirmation mots de
	 * passe entrer par l'Utilisateur
	 * 
	 * @param mdp
	 * @param mdpConfirm
	 * @param be
	 * @return boolean
	 */
	private boolean validerConfirmMdp(String mdp, String mdpConfirm, BusinessException be) {

		boolean valide = true;
		if (!mdp.equals(mdpConfirm)) {
			valide = false;
			be.addErreur("erreur.password.confirm");
		}
		return valide;
	}

	/**
	 * Fais appel à la base de donnée pour vérifier que le token existe et que la
	 * date d'expiration n'est pas dépasser
	 * 
	 * @param String
	 */
	@Override
	public boolean validerResetToken(String token) throws BusinessException {
		BusinessException be = new BusinessException();
		Boolean valide = true;
		PasswordToken passwordToken = utilisateurDAO.findToken(token);
		if (passwordToken == null) { // Vérifie si le token existe
			// be.addErreur("erreur.token.invalid");
			return false;
		}
		boolean valid = this.validerDateToken(passwordToken, be);

		if (!valid) {
			// be.addErreur("erreur.token.invalid");
		}

		return valide;

	}

	private boolean validerDateToken(PasswordToken passwordToken, BusinessException be) {
		boolean valide = true;

		if (passwordToken.getDateExpiration().isBefore(LocalDateTime.now())) {
			valide = false;
			// be.addErreur("erreur.token.date.invalid");
		}
		return valide;
	}

}
