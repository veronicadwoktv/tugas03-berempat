let form = document.getElementById("form")
let errorBox = document.getElementById("errorBox")

function setError(str) {
    errorBox.innerHTML = str
}

function validateName(name) {
    if(name.length >= 3) return true
    else return false
}

function validateGender() {
    let genderButtons = document.getElementsByName("gender");

    for(let i=0; i<genderButtons.length; i++) {
        if(genderButtons[i].checked) return true
        }
    return false
}

function validateEmail(email) { 
    if(email.length == 0) return 0

    let checkAt = email.split("@")
    if(checkAt.length != 2 || checkAt[1] == '') return 1

    let checkDot = checkAt[1].split(".")
    console.log(checkDot)
    if(checkDot.length <= 1 || checkDot[checkDot.length-1] == '') return 2

    return -1
}

function validatePhoneNumber(){
    if(phoneNumber.length <= 12) return true
    else return false
}

function validatePassword(){
    let password = document.getElementById("password").value;

    if(password.length < 7 || password.length > 15) return 0;

    let hasLetter = false;
    let hasNumber = false;
    for(let i=0; i<password.length; i++) {
        if(isNaN(password[i])) hasLetter = true;
        else hasNumber = true;
    }

    if(hasLetter && hasNumber) return -1;
    else return 1;
}


function validateTAC() {
    let checkbox = document.getElementById("TaC")
    if(!checkbox.checked) return false
    else return true
}

function validateForm(){
    let name = document.getElementById("name")
    if(!validateName(name.value)) {
        setError("Name length must be at least 3")
        return
    }
    let genderValidation = validateGender();
    if(!genderValidation) {
        setError("Gender must be selected")
        return
    }
    let email = document.getElementById("email")
    let emailValidation = validateEmail(email.value)

    if(emailValidation > -1) {
        if(emailValidation == 0) setError("Email cannot be empty")
        else if(emailValidation == 1) setError("Email must contain only 1 @ and must")
        else if(emailValidation == 2) setError("Email must contain . and must be valid")
        return
    }
    let phoneNumber = document.getElementById("Phone Number")
    if(!validatePhoneNumber(phoneNumber.value)) {
        setError("Name length must be at least 11")
        return
    }
    let passwordValidation = validatePassword();
    if(passwordValidation > -1) {
        if(passwordValidation == 0) setError("Length must be between 7 - 15")
        else if(passwordValidation == 1) setError("Password must be alphanumeric")
        return
    }
    let tacValidation = validateTAC()
    if(!tacValidation) {
        setError("Please agree to TAC")
        return
    }

    setError("")
}

form.addEventListener("submit", (e) => {
    e.preventDefault()
    validateForm()
    window.location.href = "./home.html"
})

