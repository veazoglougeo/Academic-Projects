window.onload = main;
function main(){
    let password = document.getElementById('password');
    let confirm = document.getElementById('confirm');
    let birthday = document.getElementById('date');
    let name = document.getElementById('fname');
    let surname = document.getElementById('lname')
    let email = document.getElementById('email');
    let number1 = document.getElementById('telnum');
    let number2 = document.getElementById('telnum2');
    let address = document.getElementById('address');
    let pcode = document.getElementById('pcode');
    let username = document.getElementById('username');
    password.onchange = function() {
        let isValid = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{10,}$/.test(password.value);
        if (!isValid) {
            password.setCustomValidity("Ο κωδικός πρέπει να περιλαμβάνει τουλάχιστον 10 χαρακτήρες, έναν αριθμό, ένα κεφαλαίο και ένα πεζό γράμμα.");
        }else{
        password.setCustomValidity('');
        }
    }
    confirm.onchange = function() {
        let isValid = false;
        if (password.value !== confirm.value) {
            isValid = false;
        }else{
            isValid = true;
        }
        if (!isValid) {
            confirm.setCustomValidity('Οι κωδικοί πρέπει να ταυτίζονται!');
        }else{
            this.setCustomValidity('');
        }
    }
    birthday.onchange = function() {
        let birthDate = new Date(birthday.value)
        let current = new Date();
        let age = current.getFullYear() - birthDate.getFullYear();
        if (age < 18) {
            birthday.setCustomValidity("Το κατώτερο όριο ηλικίας είναι 18 χρονών!")
        }else{
            birthday.setCustomValidity('');
        } 
    }
    name.onchange = function () {
        let isValid = /^[a-zA-Zα-ωΑ-Ωίϊΐόάέύϋΰήώ]+$/.test(name.value);
        if (!isValid) {
            name.setCustomValidity('Το όνομα πρέπει να περιέχει μόνο αλφαβητικούς χαρακτήρες!');
        }else{
            name.setCustomValidity('');
        }
    }
    surname.onchange = function() {
        let isValid = /^[a-zA-Zα-ωΑ-Ωίϊΐόάέύϋΰήώ]+$/.test(surname.value);
        if(!isValid) {
            surname.setCustomValidity("Το επώνυμο πρέπει να περιέχει μόνο αλφαβητικούς χαρακτήρες!");
        }else{
            surname.setCustomValidity('');
        }
    }
    email.onchange = function () {
        let isValid  = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email.value);
        if(!isValid) {
            email.setCustomValidity('Παρακαλώ εισάγετε ένα έγκυρο email!');
        }else{
            email.setCustomValidity('');
        }
    }
    number1.onchange = function () {
        let isValid = /^\d{10}$/.test(telnum.value);
        if(!isValid) {
            number1.setCustomValidity('Το τηλέφωνο πρέπει να αποτελείται από 10 ψηφία!');
        }else{
            number1.setCustomValidity('');
        }
    }
    number2.onchange = function () {
        let isValid = /^\d{10}$/.test(telnum2.value) || telnum2.value === '';
        if(!isValid) {
            number2.setCustomValidity('Το τηλέφωνο πρέπει να αποτελείται από 10 ψηφία!');
        }else{
            number2.setCustomValidity('');
        }
    }
    address.onchange = function () {
        let isValid = /^[α-ωΑ-Ω0-9]+$/.test(address.value);
        if(!isValid) {
            address.setCustomValidity("Η διεύθυνση πρέπει να αποτελείται μόνο απο ελληνικούς αλφαρηθμιτικούς χαρακτήρες!");
        }else{
            address.setCustomValidity('');
        }
    }
    pcode.onchange = function () {
        let isValid = /^\d{5}$/.test(pcode.value);
        if(!isValid) {
            pcode.setCustomValidity("Ο ταχυδρομικός κώδικας πρέπει να αποτελέιται από 5 ψηφία!");
        }else{
            pcode.setCustomValidity('');
        }
    }
    username.onchange = function () {
        let isValidUsername = /^[a-zA-Z0-9]+$/.test(username.value);
        if (!isValidUsername) {
            username.setCustomValidity('Το όνομα χρήστη πρέπει να περιέχει μόνο λατινικούς αλφαριθμητικούς χαρακτήρες!');
        }else{
            username.setCustomValidity('');
        } 
    }
}