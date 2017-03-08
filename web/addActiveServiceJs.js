function load() {
    localStorage.setItem('id', -1);
}

function click1(radio) {
    var idValue = localStorage.getItem('id');
    if (idValue == -1) {
        localStorage.setItem('id', radio.value);
        var parent = radio.parentNode;
        var addButton = parent.lastChild;
        addButton.style.display = 'inline';
        var dateField = addButton.previousSibling;
        dateField.style.display = 'inline';
        dateField.required=true;
    }
    else if (idValue != radio.value) {
        var previousAddButton = document.getElementById(idValue).parentNode.lastChild;
        previousAddButton.style.display = 'none';
        previousAddButton.previousSibling.style.display = 'none';
        previousAddButton.previousSibling.required=false;
        var currentAddButton = radio.parentNode.lastChild;
        currentAddButton.style.display = 'inline';
        var currentDateField = currentAddButton.previousSibling;
        currentDateField.style.display = 'inline';
        currentDateField.required=true;
        localStorage.setItem('id', radio.value);

    }
}