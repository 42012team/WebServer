$(document).ready(function() {
    $('[id^=detail-]').hide();
    $('.toggle').click(function() {
        $input = $( this );
        $target = $('#'+$input.attr('data-toggle'));
        $target.slideToggle();
    });
});

function myFunc(radio) {
    var elements = document.getElementsByClassName("buttons");
    for (i = 0; i < elements.length; i++) {
        elements[i].disabled=true;
    }
    document.getElementById(radio.value.toString()+"services").disabled=false;
    document.getElementById(radio.value.toString()+"profile").disabled=false;
};

