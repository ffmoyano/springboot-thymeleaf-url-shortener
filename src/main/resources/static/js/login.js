const emailRegex =
    new RegExp(/^[A-Za-z0-9_!#$%&'*+\/=?`{|}~^.-]+@[A-Za-z0-9.-]+$/);

window.onload = function(){
    swapVisibleForm("login");
};

function selectForm(form) {
    swapVisibleForm(form);
}

function swapVisibleForm(form) {
    let loginForm = document.getElementById("loginForm");
    let signupForm = document.getElementById("signupForm");
    let loginTab = document.getElementById("loginTab");
    let signupTab = document.getElementById("signupTab");
    if(form==="login") {
        // loginForm.style.visibility="visible";
        // signUpForm.style.visibility="hidden";
        loginForm.style.display="block";
        signupForm.style.display="none"
        loginTab.className = "selectedLoginTab";
        signupTab.className = "unselectedLoginTab";
    } else  if(form==="signup") {
        loginForm.style.display="none";
        signupForm.style.display="block"
        loginTab.className = "unselectedLoginTab";
        signupTab.className = "selectedLoginTab";
    }
}

function hideError(elementName) {
    let errorMessage = document.getElementById(elementName);
    if(errorMessage !== null && errorMessage.style.visibility !== "hidden") {
        errorMessage.style.visibility = "hidden";
    }
}

function checkSignup() {
    let valid = true;
    let errorTooltip = "";
    let email = document.getElementById("emailSignup").value
    let password = document.getElementById("passwordSignup").value
    let passwordRepeat = document.getElementById("passwordRepeat").value
    let signupButton = document.getElementById("signupButton")
    if(!emailRegex.test(email)) {
        valid = false;
        errorTooltip+= "El email debe ser válido.\r\n"
    }
    if(password == null || password.length<8) {
        valid = false;
        errorTooltip+="El password debe tener al menos 8 caracteres.\r\n"
    }
    if(password !== passwordRepeat) {
        valid = false;
        errorTooltip+="Los passwords deben coincidir.\r\n";
    }
    if(errorTooltip.length===0) {
        signupButton.title = "Registro";
    } else {
        signupButton.title = errorTooltip;
    }
    signupButton.disabled = !valid;
    console.log(password)
    console.log(passwordRepeat)
    console.log(email)
}



