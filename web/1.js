function showCalendar() {
    var hiddenText = document.getElementById('hiddenText');
    var date = document.getElementById('date');
    var changeButton = document.getElementById('changeDate');
    var cancelButton = document.getElementById('cancel');
    var submitButton = document.getElementById('submit');
    hiddenText.style.display = 'inline';
    date.style.display = 'inline';
    submitButton.style.display = 'inline';
    changeButton.style.display = 'none';
    cancelButton.style.display = 'none';
}