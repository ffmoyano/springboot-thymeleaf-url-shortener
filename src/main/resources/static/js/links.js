function hideUrlError() {
    let errorMessage = document.getElementById("hidableUrlError");
    if(errorMessage !== null && errorMessage.style.visibility !== "hidden") {
        errorMessage.style.visibility = "hidden";
    }
}

function copyToClipboard(value) {
    navigator.clipboard.writeText(value);
    alert('Copiado al portapapeles: ' + value);
}
