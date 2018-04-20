var logged;

$(document).ready(function () {
    firebase.auth().onAuthStateChanged(function (user) {
        logged = !!user;
    });
});

function logOut() {
    if (logged) {
        firebase.auth().signOut();
    } else {
        window.location.href = "spider-solitaire-hresko-login.html";
    }
}
