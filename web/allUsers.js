/* API method to get paging information */

'use strict';
var $ = jQuery;

$.getScript("datatables.js", function () {

    $('#example').DataTable({
        "paging": true,
        "ordering": true,
        "info": false
    });
});




