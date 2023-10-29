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
        const point = $('input[name=point]');

        let doubPoint = price * 0.01;

        point.val(Math.round(doubPoint));
    });

    $('input[name=point]').focusout(function (){
        // 입력 필드에서 값 가져오기
        let inputValue = $(this).val();

        // 값이 숫자인지 확인하고 숫자로 변환
        let point = parseFloat(inputValue);

        // 숫자가 아니면 무시
        if (isNaN(point)) {
            return;
        }

        // 값 반올림
        let roundedPoint = Math.round(point);

        // 반올림한 값을 정수로 변환
        let intPoint = parseInt(roundedPoint, 10);

        // 반올림 및 정수 변환한 값 다시 입력 필드에 설정
        $(this).val(intPoint);
    });

    $('input:text[onlyNumber]').on('keyup',function (){
        // 숫자만 입력하게 하는 기능

        $(this).val($(this).val().replace(/[^0-9]/g, ""));
    });

});