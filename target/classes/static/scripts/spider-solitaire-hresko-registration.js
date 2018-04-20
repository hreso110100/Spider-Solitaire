firebase.auth().onAuthStateChanged(function (user) {
    if (user) {
        window.location.href = "spider-solitaire-hresko.html";
    } else {

    }
});

function register() {
    var userEmail = document.getElementById("inputEmail").value;
    var userPass = document.getElementById("inputPassword").value;
    var checkbox = document.getElementById("checkbox");

    if (checkbox.checked) {
        firebase.auth().createUserWithEmailAndPassword(userEmail, userPass).catch(function (error) {
            var errorCode = error.code;
            var errorMessage = error.message;

            document.getElementById("error_message").innerHTML = errorMessage;

        });
    }else {
        document.getElementById("error_message").innerHTML = "You must accept terms and conditions";
    }
}
