package fr.eni.tp.encheres.controller;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.tp.encheres.bll.UtilisateurService;
import fr.eni.tp.encheres.bo.Utilisateur;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.eni.tp.encheres.exception.BusinessException;
import jakarta.validation.Valid;

@Controller
@SessionAttributes("userSession")
public class UtilisateurController {

	private UtilisateurService utilisateurService;
	private MessageSource messageSource;

	public UtilisateurController(UtilisateurService utilisateurService, MessageSource messageSource) {
		this.utilisateurService = utilisateurService;
		this.messageSource = messageSource;
	}

	// ********** CREATE **********

	// ********** READ **********

	/**
	 * Méthode permettant d'afficher la page du profil avec les infos de
	 * l'utilisateur en session
	 * 
	 * @param model
	 * @param userSession
	 * @return la page mon profil
	 */
	@GetMapping("/monProfil")
	public String getMonProfil(Model model, @ModelAttribute("userSession") Utilisateur userSession) {
		Utilisateur user = this.utilisateurService.getUtilisateurById(userSession.getNoUtilisateur());
		model.addAttribute("utilisateur", user);

		return "mon-profil";
	}

	/**
	 * Méthode permettant d'accéder au formulaire de modification de l'utilisateur
	 * 
	 * @param model
	 * @param userSession
	 * @return la page formulaire modifier-profil
	 */
	@PostMapping("/monProfil")
	public String postMonProfil(Model model, @ModelAttribute("userSession") Utilisateur userSession) {

		Utilisateur user = this.utilisateurService.getUtilisateurById(userSession.getNoUtilisateur());
		model.addAttribute("utilisateur", user);

		return "modifier-profil";
	}

	/**
	 * Méthode permettant d'afficher le profil d'un utilisateur
	 * 
	 * @param id
	 * @param model
	 * @return la page profil-utilisateur
	 */
	@GetMapping("/profil")
	public String getProfil(@RequestParam("id") int id, Model model) {
		Utilisateur user = this.utilisateurService.getUtilisateurById(id);
		model.addAttribute("utilisateur", user);

		return "profil-vendeur";

	}

	// ********** UPDATE **********

	/**
	 * Méthode permettant d'afficher la page de formulaire pour modifier le profil
	 * 
	 * @param model
	 * @param userSession
	 * @return page modifier le profil
	 */
	@GetMapping("/monProfil/modifier")
	public String getModifierMonProfil(Model model, @ModelAttribute("userSession") Utilisateur userSession) {

		Utilisateur user = this.utilisateurService.getUtilisateurById(userSession.getNoUtilisateur());
		model.addAttribute("utilisateur", user);

		return "modifier-profil";
	}

	/**
	 * Méthode permettant de soumettre les informations modifier de l'utilisateur
	 * puis après traitement de modifier l'user en session
	 * 
	 * @param newMdp
	 * @param mdpConfirm
	 * @param userSession
	 * @param user
	 * @param bindingResult
	 * @return la page mon-profil; Si erreur : @Return le formulaire de modification
	 */
	@PostMapping("/monProfil/modifier")
	public String postModifierMonProfil(@RequestParam("NewMotDePasse") String newMdp,
			@RequestParam("PasswordConfirm") String mdpConfirm, @ModelAttribute("userSession") Utilisateur userSession,
			@Valid @ModelAttribute("utilisateur") Utilisateur user, BindingResult bindingResult) {

		user.setNoUtilisateur(userSession.getNoUtilisateur());
		if (!bindingResult.hasErrors()) {

			try {
				this.utilisateurService.updateUtilisateur(user, mdpConfirm, newMdp);

				Utilisateur utilisateur = this.utilisateurService.getUtilisateurById(user.getNoUtilisateur());

				if (utilisateur != null) {
					userSession.setNoUtilisateur(utilisateur.getNoUtilisateur());
					userSession.setPseudo(utilisateur.getPseudo());
					userSession.setNom(utilisateur.getNom());
					userSession.setPrenom(utilisateur.getPrenom());
					userSession.setEmail(utilisateur.getEmail());
					userSession.setCredit(utilisateur.getCredit());
					userSession.setTelephone(utilisateur.getTelephone());
					userSession.setRue(utilisateur.getRue());
					userSession.setCodePostal(utilisateur.getCodePostal());
					userSession.setVille(utilisateur.getVille());
					userSession.setAdministrateur(utilisateur.isAdministrateur());

				} else {

					userSession.setNoUtilisateur(0);
					userSession.setPseudo(null);
					userSession.setNom(null);
					userSession.setPrenom(null);
					userSession.setEmail(null);
					userSession.setCredit(0);
					userSession.setTelephone(null);
					userSession.setRue(null);
					userSession.setCodePostal(null);
					userSession.setVille(null);
					userSession.setAdministrateur(false);

				}
				
				return "redirect:/monProfil";
			} catch (BusinessException e) {
				e.printStackTrace();
				e.getClesErreurs().forEach(cle -> {
					ObjectError error = new ObjectError("globalError", cle);
					bindingResult.addError(error);
				});
			}
			
			return "modifier-profil";
		} else {
			
			return "modifier-profil";
		}

	}

	/**
	 * Méthode renvoyant la vue permettant l'achat de crédit par l'utilisateur
	 * 
	 * @param userSession l'utilisateur en session
	 * @param model       avec les données de l'utilisateur
	 * @return la vue de l'achat de crédit
	 */
	@GetMapping("/monProfil/achatCredit")
	public String getAchatCredit(@ModelAttribute("userSession") Utilisateur userSession, Model model) {
		Utilisateur user = utilisateurService.getUtilisateurById(userSession.getNoUtilisateur());
		model.addAttribute("user", user);
		return "view-achat-credit";
	}

	/**
	 * Méthode permettant de modifier la valeur des crédits en BDD de l'utilisatur
	 * en fonction de son id d'utilisateur et de son choix d'achat
	 * 
	 * @param achatCredit choix du nombre de crédits
	 * @param userSession utilisateur en session
	 * @return la vue des enchères
	 */
	@PostMapping("/monProfil/achatCredit")
	public String postAchatCredit(@RequestParam("achat") int achatCredit,
			@ModelAttribute("userSession") Utilisateur userSession) {
		utilisateurService.updateCredit(userSession, achatCredit);
		return "redirect:/encheres";

	}

	// ********** DELETE **********

	/**
	 * Méthode permettant d'appeller loginservice pour supprimer Utilisateur
	 * 
	 * @param userSession
	 * @param redirectAttributes
	 * @param locale
	 * @return redirect la page mon-profil si erreur
	 * @return déconnexion si pas d'erreur
	 * @throws BusinessException
	 */
	@PostMapping("/monProfil/supprimer")
	public String postSupprimerMonProfil(@ModelAttribute("userSession") Utilisateur userSession,
			RedirectAttributes redirectAttributes, Locale locale) throws BusinessException {

		try {

			utilisateurService.deleteUtilisateur(userSession);
			return "redirect:/logout";
		} catch (BusinessException e) {
			e.printStackTrace();
			e.getClesErreurs().forEach(cle -> {
				String message = messageSource.getMessage(cle, null, locale);
				redirectAttributes.addFlashAttribute("erreurs", message);
			});

			return "redirect:/monProfil";
		}
	}
}
