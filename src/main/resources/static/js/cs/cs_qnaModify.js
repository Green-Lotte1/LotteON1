$(function() {

    const article = $('#modify').parent();

    const no = $('input[name=no]').val();
    const url = $('input[name=url]').val();

    // 수정완료 클릭
    $('#modifyBtn').click(function(e) {
        e.preventDefault();

        if(confirm('수정하시겠습니까?')) {
            $('#modify').submit();
            // no값을 통해 글을 조회하고 uid 비교하기
            // setter를 통해 혹은 setter 생략. update.
        }
    });
});