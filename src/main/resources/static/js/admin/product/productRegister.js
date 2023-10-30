$(function () {

    $('select[name=prodCate2]').change(function () {

        let cate1 = $('select[name=prodCate1]').val();
        let cate2 = $(this).val();


        let actionUrl = "/admin/product/register?prodCate1=" + cate1 + "&prodCate2=" + cate2;
        $('#formRegister').attr('action', actionUrl);


    });

    $('input[name=price]').focusout(function () {
        // 포인트 자동계산
        const price = $(this).val();

    });

    $('input:text[onlyNumber]').on('keyup',function (){
        // 숫자만 입력하게 하는 기능

        $(this).val($(this).val().replace(/[^0-9]/g, ""));
    });

});