$(document).ready(function () {

    const inputCount = $('.inputCount');
    const increase = $('.increase');
    const decrease = $('.decrease');
    // 숫자가 아닌 다른 값 입력시 이전 값 입력을 위한 초기화
    let prevCount = 1;
    let price = $('#dis_price').val();
    const discount = $('#discount').val();
    const totalPrice = $('.totalPrice');

    //***********************************************//
    //************ 입력값이 변경될 때 이벤트 처리 ************//
    //***********************************************//
    inputCount.on('input', function () {
        let currentValue = parseInt(inputCount.val()) || 1; // 현재 값이 숫자가 아니면 최솟값(1)로 설정
        //const max = parseInt(inputCount.attr('max'));

        // 숫자인 경우만 prevCount 업데이트
        if (/^\d+$/.test(inputCount.val())) {
            prevCount = currentValue;
        }

        totalPrice.text($.numberWithCommas(parseInt(price * prevCount)));
    }); // input end

    //***********************************************//
    //*** input focus, focusout할 때 input의 숫자 저장 ***//
    //***********************************************//
    inputCount.on('focus', function () {
        prevCount = $(this).val();
    });
    inputCount.on('focusout', function () {
        inputCount.val(prevCount);
        console.log('inputCount :'+inputCount.val());
        console.log('discount :'+discount);
        console.log('price :'+price);
    }); // focus, focusout end

    //***********************************************//
    //***************** +버튼 클릭 함수 *****************//
    //***********************************************//
    $(increase).click(function () {
        let currentValue = parseInt(inputCount.val());

        // 만약 현재 값이 max 값 미만이면 1을 더하고 업데이트
        if (currentValue < 999) {
            currentValue += 1;
            inputCount.val(currentValue);
        }
        totalPrice.text($.numberWithCommas(parseInt(price * inputCount.val())));
        console.log('inputCount :'+inputCount.val());
    }); // increase click end

    //***********************************************//
    //***************** -버튼 클릭 함수 *****************//
    //***********************************************//
    $(decrease).click(function () {
        let currentValue = parseInt(inputCount.val());

        // 만약 현재 값이 최솟값 초과이면 1을 빼고 업데이트
        if (currentValue > 1) {
            currentValue -= 1;
            inputCount.val(currentValue);
        }
        totalPrice.text($.numberWithCommas(parseInt(price * inputCount.val())));
        console.log('inputCount :'+inputCount.val());
    }); // decrease click end

    //***********************************************//
    //*************** 0이하 입력시 1로 변경 ***************//
    //***********************************************//
    inputCount.blur(function () {
        let currentValue = parseInt(inputCount.val());
        // 현재 값이 최솟값 미만일 경우 최솟값으로 설정
        if (currentValue < 1) {
            inputCount.val(prevCount);
        }
    }); //inputCount blur end

    //***********************************************//
    //*************** 숫자 3자리 콤마 함수 ***************//
    //***********************************************//
    $.numberWithCommas = function (x) {
      return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    }










    //***********************************************//
    //*************** 장바구니 버튼 ***************//
    //***********************************************//
    const prodNo = $('#prodNo').val();

    $('.cart').click(function(){
        const input = inputCount.val();
        alert('장바구니');
        console.log('input: '+input);
        console.log('prodNo: '+prodNo);
        console.log('here...1');
        const jsonData = {
            "prodNo": prodNo,
            "input": input
        }
        console.log('jsonData :'+JSON.stringify(jsonData));
        // 위의 jsonData를 insertCartController로 보내서 같은 상품이 있는지 확인
        $.ajax({
            url: '/LotteOn/product/cartCountProduct',
            type: 'get',
            data: jsonData,
            success: function(data){
                console.log('here...2');
                console.log('data:'+data);
                const checkData = Object.assign({},jsonData,{"result":data});

                console.log('jsonData :'+JSON.stringify(jsonData));
                // (해당 상품이 장바구니에 있는 경우)
                // 만약 cart에 같은 상품이 있다면
                if(data == 1){
                    console.log('here...data == 1');
                    console.log('here...3');
                    // 해당 상품을 또 장바구니에 추가할건지 확인받는다.
                    if(confirm('해당 상품이 이미 장바구니에 있습니다. 추가하시겠습니까?')){
                        console.log('here...4');
                        $.ajax({
                            url: '/LotteOn/product/insertCartProduct',
                            type: 'post',
                            data: JSON.stringify(checkData),
                            dataType: 'json',
                            contentType: 'application/json;charset=UTF-8',
                            success: function(data){
                            console.log('here...5');
                            }
                        }); // ajax end
                        if(confirm('장바구니에 추가되었습니다. 지금 장바구니로 이동하시겠습니까?')){
                            console.log('here...6');
                            console.log('jsonData :'+JSON.stringify(jsonData));
                            window.location.href = '/LotteOn/product/cart';
                        }else{
                            console.log('here...7');
                                console.log('jsonData :'+JSON.stringify(jsonData));
                            return;
                        }
                    // 해당 상품이 장바구니에 이미 있기 때문에 취소한다.
                    }else{
                        console.log('here...8');
                        return;
                        /*// 장바구니에 추가하지 않는다면 cartResult 값을 0으로 설정하여 보낸다.
                        jsonData = Object.assign({},jsonData,{"updateConfirm":"no"});

                        $.ajax({
                            url: '/product/cart',
                            type: 'post',
                            data: jsonData,
                            dataType: 'json',
                            success: function(data){
                                console.log(data.cartResult);
                            }
                        }); // ajax end*/
                    }

                // (해당 상품이 장바구니에 없는 경우)
                // 만약 insertCartController에서 받아온 result 값이 0이라면 아래를 실행
                }else if(data == 0){
                    console.log('here...data == 0');
                    console.log('checkData :'+JSON.stringify(checkData));
                    $.ajax({
                        url: '/LotteOn/product/insertCartProduct',
                        type: 'post',
                        dataType: 'json',
                        data: JSON.stringify(checkData),
                        contentType: 'application/json;charset=UTF-8',
                        success: function(data){
                            console.log('here...9');
                        }
                    }); // ajax end
                    if(confirm('장바구니에 추가되었습니다. 지금 장바구니로 이동하시겠습니까?')){
                        console.log('here...10');
                        console.log('jsonData :'+JSON.stringify(checkData));
                        window.location.href = '/LotteOn/product/cart';
                    }else{
                        console.log('here...11');
                        console.log('jsonData :'+JSON.stringify(jsonData));
                        return;
                    }
                }
            } // 전체 ajax success end
        }); // 전체 ajax end
    });

    $('.order').click(function(){
            alert('구매하기');
        });

});