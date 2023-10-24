$(function() {
    $('.tit a').click(function(e) {
        e.preventDefault(); // 기본 클릭 동작(링크 이동)을 막습니다.
        var $answerRow = $(this).closest('tr').next('.answerRow');
        $answerRow.toggle();
    });
});