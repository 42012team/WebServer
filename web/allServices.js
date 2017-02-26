function clickRadio(radio) {
    var elements = document.getElementsByName("buttons");
    for (i = 0; i < elements.length; i++) {
        elements[i].style.display = 'none';
    }
    document.getElementById(radio.value.toString() + "del").style.display = 'inline';
    document.getElementById(radio.value.toString() + "ch").style.display = 'inline';
};