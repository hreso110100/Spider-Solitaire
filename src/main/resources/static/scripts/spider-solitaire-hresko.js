var counter = 0;
var url;
var sourceRowIndex;

function replace(clicked_id) {
    if (counter === 0) {
        counter++;
        sourceRowIndex = clicked_id.toString().substr(1,2);
        url = "?command=move&sourceRow=" + clicked_id[0].toString() + "&sourceRowIndex=" +sourceRowIndex ;
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