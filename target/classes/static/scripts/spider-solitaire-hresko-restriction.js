//check if user is logged
firebase.auth().onAuthStateChanged(function (user) {
    if (!user) {
        window.location.href = "spider-solitaire-hresko-login.html";
    }
});
