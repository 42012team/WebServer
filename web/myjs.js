/**
 * Created by User on 17.02.2017.
 */
/**
 * Created by User on 17.02.2017.
 */
function load() {
    localStorage.setItem('id', -1);
    alert('be here')
}

function click1(radio) {
    var idValue = localStorage.getItem('id');
    if (idValue == -1) {
        localStorage.setItem('id', radio.value);

        alert(radio.value + 'lalka');
        var parent = radio.parentNode;
        var deleteButton = parent.lastChild;
        deleteButton.style.display = 'inline';
        var changeButton = deleteButton.previousSibling;
        changeButton.style.display = 'inline';
    }
    else if (idValue != radio.value) {
        var lastDeleteButton = document.getElementById(idValue).parentNode.lastChild;
        lastDeleteButton.style.display = 'none';
        lastDeleteButton.previousSibling.style.display = 'none';
        var currentDeleteButton = radio.parentNode.lastChild;
        currentDeleteButton.style.display = 'inline';
        var currentChangeButton = currentDeleteButton.previousSibling;
        currentChangeButton.style.display = 'inline';
        localStorage.setItem('id', radio.value);

    }
}