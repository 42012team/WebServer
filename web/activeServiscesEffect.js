
function load() {
    localStorage.setItem('id', -1);
}

function click1(radio) {
    var idValue = localStorage.getItem('id');
    if (idValue == -1) {
        localStorage.setItem('id', radio.value);
        var parent = radio.parentNode;
        var deleteButton = parent.lastChild;
        deleteButton.style.display = 'inline';
        var changeButton = deleteButton.previousSibling;
        changeButton.style.display = 'inline';
    }
    else if (idValue != radio.value) {
        var previousDeleteButton = document.getElementById(idValue).parentNode.lastChild;
        previousDeleteButton.style.display = 'none';
        previousDeleteButton.previousSibling.style.display = 'none';
        var currentDeleteButton = radio.parentNode.lastChild;
        currentDeleteButton.style.display = 'inline';
        var currentChangeButton = currentDeleteButton.previousSibling;
        currentChangeButton.style.display = 'inline';
        localStorage.setItem('id', radio.value);
    }
}
