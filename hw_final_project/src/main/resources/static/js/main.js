function back() {
    window.history.back();
}

function forward() {
    window.history.forward();
}

function showCheckboxes() {
    var expanded = false;
    var checkboxes = document.getElementById("checkboxes");
    if (!expanded) {
        checkboxes.style.display = "block";
        expanded = true;
    } else {
        checkboxes.style.display = "none";
        expanded = false;
    }
}