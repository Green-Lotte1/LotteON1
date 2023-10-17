$(document).ready(function () {
    $('.more').click(function(e){
        e.preventDefault();

        let item = $(this).parent().find('> li:nth-child(n+4)');
        let displayStatus = item.css('display');

        if(displayStatus == 'none'){
            item.slideDown(100);
            $(this).find('a').text('간단히 보기');
        }else{
            item.slideUp(100);
            $(this).find('a').text('더보기');
        }
    });
});