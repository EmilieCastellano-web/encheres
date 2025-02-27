// Méthode permettant d'avoir un événement sur les boutons radios pour désactiver et décocher les cases du formulaire

const radioAchats = document.getElementById("achats");
const radioVentes = document.getElementById("ventes");
const checkboxAchats = document.querySelectorAll(".achats-checkbox");
const checkboxVentes = document.querySelectorAll(".ventes-checkbox");

function toggleCheckboxes() {
	if (radioAchats.checked) {
		checkboxAchats.forEach(checkbox => checkbox.disabled = false);
		checkboxVentes.forEach(checkbox => checkbox.disabled = true);
		checkboxVentes.forEach(checkbox => checkbox.checked = false);
	}
	if (radioVentes.checked) {
		checkboxVentes.forEach(checkbox => checkbox.disabled = false);
		checkboxAchats.forEach(checkbox => checkbox.disabled = true);
		checkboxAchats.forEach(checkbox => checkbox.checked = false);
	}
}

if (radioAchats) {
	radioAchats.addEventListener("change", toggleCheckboxes);
}
if (radioVentes) {
	radioVentes.addEventListener("change", toggleCheckboxes);
}