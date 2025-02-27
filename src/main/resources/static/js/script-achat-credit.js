const inputAchat = document.getElementById("achat");
const resultat = document.getElementById("resultat");
const resultatContainer = document.getElementById("resultatContainer");
const buttonContainer = document.getElementById("buttonContainer");

function displayResulat() {
	resultat.textContent = "";

	let nbCredit = inputAchat.value;
	let prix = nbCredit / 4;
	if (prix != 0) {
		resultat.textContent = prix;
		resultatContainer.classList.remove("hidden");
		buttonContainer.classList.remove("hidden");
	} else {
		resultatContainer.classList.add("hidden");
		buttonContainer.classList.add("hidden");
	}
}

inputAchat.addEventListener("change", displayResulat);