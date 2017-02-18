/**
 * Created by User on 18.02.2017.
 */
/**
 * Created by User on 17.02.2017.
 */
/**
 * Created by User on 17.02.2017.
 */
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
        var dateField=addButton.previousSibling;
        dateField.style.display = 'inline';
    }
    else if (idValue != radio.value) {
        var previousAddButton = document.getElementById(idValue).parentNode.lastChild;
       previousAddButton.style.display = 'none';
        previousAddButton.previousSibling.style.display = 'none';
        var currentAddButton = radio.parentNode.lastChild;
        currentAddButton.style.display = 'inline';
        var currentDateField = currentAddButton.previousSibling;
        currentDateField.style.display = 'inline';
        localStorage.setItem('id', radio.value);

    }
}