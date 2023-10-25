$(function() {
    $('.tit').off('click').on('click', function(e) {
        e.preventDefault();

        var $answerRow = $(this).closest('tr').next('.answerRow');
        var displayStyle = $answerRow.css('display');
        console.log(' current display : ' + displayStyle);

        if(displayStyle === 'none') {
            $answerRow.css('display', 'table-row');
        }else {
            $answerRow.css('display', 'none');
        }
    });
});