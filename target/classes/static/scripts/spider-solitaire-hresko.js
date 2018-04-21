var counter = 0;
var url;
var sourceRowIndex;

function replace(clicked_id) {
    if (counter === 0) {
        counter++;
        sourceRowIndex = clicked_id.toString().substr(1, 2);
        url = "?command=move&sourceRow=" + clicked_id[0].toString() + "&sourceRowIndex=" + sourceRowIndex;
        document.getElementById(clicked_id).style.border = "thick solid #3498db";
        document.getElementById(clicked_id).style.borderRadius = "15px";
    } else if (counter === 1) {
        url += "&destinationRow=" + clicked_id[0].toString();
        document.getElementById(clicked_id).style.border = "thick solid #3498db";
        document.getElementById(clicked_id).style.borderRadius = "15px";
        window.location.href = url;
        counter = 0;
    }
}

firebase.auth().onAuthStateChanged(function (user) {
    if (user) {
        if (user.displayName == null) {
            document.getElementById("user").innerHTML = "Logged as " + user.email;
        } else {
            document.getElementById("user").innerHTML = "Logged as " + user.displayName;
        }
    }
});

$(document).ready(function () {

    window.setTimeout(function () {
        $(".alert").fadeTo(1000, 0).slideUp(1000, function () {
            $(this).remove();
        });
    }, 2000);

});

$(function () {

    $("#rateYo").rateYo({
        starWidth: "40px",
        ratedFill: "#f1c40f",
        fullStar: true
    });

});

function saveData() {

    var playerName = document.getElementById("user").innerHTML.substr(10, 500);

    $.ajax({
        url: "http://localhost:8080/spider-solitaire-hresko-score",
        type: "POST",
        data: "{" + "\"player\": " + "\"" + playerName + "\"" + "}",
        contentType: "application/json"
    });

    document.getElementById("save-button").innerHTML = "Saved !";
    setTimeout(function () {
        document.getElementById("save-button").disabled = true;
    }, 1000);

}

function saveComment() {
    var playerName = document.getElementById("user").innerHTML.substr(10, 500);
    var comment = document.getElementById("message-text").value;

    $.ajax({
        url: "http://localhost:8080/spider-solitaire-hresko-comment",
        type: "POST",
        data: "{" + "\"player\": " + "\"" + playerName + "\"" + ",\"comment\":" + "\"" + comment + "\"" + "}",
        contentType: "application/json"
    });
}

function saveRating() {
    var playerName = document.getElementById("user").innerHTML.substr(10, 500);
    var rating = $("#rateYo").rateYo("rating");

    $.ajax({
        url: "http://localhost:8080/spider-solitaire-hresko-rating",
        type: "POST",
        data: "{" + "\"player\": " + "\"" + playerName + "\"" + ",\"rating\":" + "\"" + rating + "\"" + "}",
        contentType: "application/json"
    });
}
