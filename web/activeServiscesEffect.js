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
        var changeTariffButton = deleteButton.previousSibling;
        changeTariffButton.style.display = 'inline';
        var changeDateButton = changeTariffButton.previousSibling;
        changeDateButton.style.display = 'inline';
    }
    else if (idValue != radio.value) {
        var previousDeleteButton = document.getElementById(idValue).parentNode.lastChild;
        previousDeleteButton.style.display = 'none';
        previousDeleteButton.previousSibling.style.display = 'none';
        previousDeleteButton.previousSibling.previousSibling.style.display = 'none';
        var currentDeleteButton = radio.parentNode.lastChild;
        currentDeleteButton.style.display = 'inline';
        var currentDateChangeButton = currentDeleteButton.previousSibling;
        currentDateChangeButton.style.display = 'inline';
        var currentTariffChangeButton = currentDateChangeButton.previousSibling;
        currentTariffChangeButton.style.display = 'inline';
        localStorage.setItem('id', radio.value);
    }
    function showCalendar() {
        var hiddenText = document.getElementById('hiddenText');
        var date = document.getElementById('date');
        hiddenText.style.display = 'inline';
        date.style.display = 'inline';

    }
}
