function hideUrlError() {
    let errorMessage = document.getElementById("hidableUrlError");
    if(errorMessage !== null && errorMessage.style.visibility !== "hidden") {
        errorMessage.style.visibility = "hidden";
    }
}
