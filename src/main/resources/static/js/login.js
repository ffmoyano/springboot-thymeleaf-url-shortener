window.onload = function(){
    swapVisibleForm("login");
};

function selectForm(form) {
    hideLoginError();
    swapVisibleForm(form);
}

function swapVisibleForm(form) {
    let loginForm = document.getElementById("loginForm");
    let signUpForm = document.getElementById("signUpForm");
    let loginTab = document.getElementById("loginTab");
    let signUpTab = document.getElementById("signUpTab");
    if(form==="login") {
        // loginForm.style.visibility="visible";
        // signUpForm.style.visibility="hidden";
        loginForm.style.display="block";
        signUpForm.style.display="none"
        loginTab.className = "selectedLoginTab";
        signUpTab.className = "unselectedLoginTab";
    } else  if(form==="signup") {
        loginForm.style.display="none";
        signUpForm.style.display="block"
        loginTab.className = "unselectedLoginTab";
        signUpTab.className = "selectedLoginTab";
    }
}

function hideLoginError() {
    let errorMessage = document.getElementById("hidableLoginError");
    if(errorMessage !== null && errorMessage.style.visibility !== "hidden") {
        errorMessage.style.visibility = "hidden";
    }
}

