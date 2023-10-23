$(document).ready(function () {

    $('#usePoint').on('click', function() {
            var inputValue = $(this).val();
            // 정규 표현식을 사용하여 콤마 추가
            /*var formattedValue = inputValue.replace(/,/g, '').replace(/\B(?=(\d{3})+(?!\d))/g, ',');
            $(this).val(formattedValue);*/

            $(this).val('');
            $(this).val(inputValue);


            // 키보드 방향키로 앞자리로 이동하지 못하도록 설정
            $(this).on('keydown', function(e) {
                if (e.which === 37 || e.which === 39) { // 왼쪽 화살표(37)와 오른쪽 화살표(39)
                    e.preventDefault();
                }
            });
    });
    $('#usePoint').on('input', function() {
            var inputValue = $(this).val();
            var caretPos = this.selectionStart; // 현재 캐럿 위치
            var commaCountBeforeCaret = (inputValue.substring(0, caretPos).match(/,/g) || []).length; // 캐럿 앞에 있는 콤마 수

            // 입력값에서 콤마 제거
            var valueWithoutCommas = inputValue.replace(/,/g, '');

            // 콤마 추가
            var formattedValue = valueWithoutCommas.replace(/\B(?=(\d{3})+(?!\d))/g, ',');

            // 마지막 문자열 수정이면 캐럿을 유지한 채로 콤마 추가
            if (inputValue.charAt(caretPos - 1) === ',' && valueWithoutCommas.charAt(caretPos - 1 - commaCountBeforeCaret) !== ',') {
                formattedValue = formattedValue.slice(0, caretPos + commaCountBeforeCaret) + ',' + formattedValue.slice(caretPos + commaCountBeforeCaret);
            }

            $(this).val(formattedValue);

            // 캐럿 위치 조정
            var newCaretPos = caretPos + (formattedValue.length - inputValue.length);
            this.setSelectionRange(newCaretPos, newCaretPos);
        });


    let prevPoint = 0;
    $('#usePoint').focusout(function(){
        var usePoint = parseInt(($(this).val()).replace(/,/g, ''));
        var hasPoint = parseInt(($("#hasPoint").text()).replace(/,/g, ''));
        // 현재 포인트 초과 입력시 현재 보유 포인트 값을 입력할거임
        var correctPoint = Math.min(usePoint, hasPoint);

        // 입력된 값이 숫자가 아니면 이전 prevPoint 값으로 복원
        if (!/^\d+$/.test(usePoint)) {
            $(this).val(prevPoint.toLocaleString());
        }

        // 현재 보유 포인트와 비교하여 입력된 값이 더 크면 현재 보유 포인트 값으로 업데이트
        if (usePoint > hasPoint) {
            $(this).val(correctPoint.toLocaleString());
        }

        console.log('prevPoint : '+prevPoint);
        // 숫자인 경우만 prevPoint 업데이트
        if (/^\d+$/.test(correctPoint)) {
            prevPoint = correctPoint;
        }
    });
    $('#usePoint').focus(function(){
        prevCount = $(this).val();
    });

    // 포인트 사용 버튼 구현중
    const ordTotPrice = parseInt(($('#ordTotPrice').text()).replace(/,/g, ''));
    var usedPointInput = $('input[type="hidden"][name="usedPoint"]');
    var ordTotPriceInput = $('input[type="hidden"][name="ordTotPrice"]');
    $('#applyDiscount').click(function(){

        if(prevPoint < 5000){
            alert('5,000점 이상만 사용 가능합니다.');
            $('#ordTotUsePoint').text('0원');
            usedPointInput.val(0);
            $($('#ordTotPrice')).text($.numberWithCommas(ordTotPrice)+'원');
            ordTotPriceInput.val(ordTotPrice);
            return;
        }

        var usePoint = parseInt(($('#usePoint').val()).replace(/,/g, ''));
        console.log('usePoint: '+usePoint);
        if(!isNaN(usePoint) & usePoint >= 5000){

            $('#ordTotUsePoint').text('-'+$.numberWithCommas(usePoint)+'원');
            usedPointInput.val(usePoint);

            const ordTotPriceMinusPoint = ordTotPrice - usePoint;

            console.log('ordTotPrice :'+ordTotPrice);
            console.log('usePoint :'+usePoint);
            console.log('ordTotPriceMinusPoint :'+ordTotPriceMinusPoint);

            $($('#ordTotPrice')).text($.numberWithCommas(ordTotPriceMinusPoint)+'원');
            ordTotPriceInput.val(ordTotPriceMinusPoint);
        } else {
            alert("포인트는 5000점 이상이어야 합니다.");
        }
    });// applyDiscount click end

    //***********************************************//
    //*************** 숫자 3자리 콤마 함수 ***************//
    //***********************************************//
    $.numberWithCommas = function (x) {
      return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    }


    // 라디오 버튼이 변경될 때 이벤트 리스너를 추가합니다.
    /*let ordPayment = 11;
    $("input[name='payment']").change(function() {
        // 선택된 라디오 버튼의 value 값을 가져온다.
        var selectedPayment = $("input[name='payment']:checked").val();

        ordPayment = selectedPayment;
        console.log(selectedPayment);
    });*/

     $('input[type="radio"]').on('click', function() {
            var selectedValue = $(this).val();

            // 해당 그룹 내의 모든 라디오 버튼의 checked 속성 제거
            $('input[type="radio"]').prop('checked', false);

            // 선택한 라디오 버튼의 checked 속성 설정
            $('input[type="radio"][value="' + selectedValue + '"]').prop('checked', true);

            // ordPayment 라디오 버튼 설정
            $('input[type="radio"][name="ordPayment"]').prop('name', 'payment');
            $('input[type="radio"][value="' + selectedValue + '"]').prop('name', 'ordPayment');
        });


    //***********************************************//
    //****************** 결제 하기 클릭 ******************//
    //***********************************************//
    const path = $('#path').val();
    /*const savePoint = parseFloat($('#ordTotSavePoint').text().replace(/[^0-9.]+/g, ''));*/
    $('#ordComplete').click(function(e){
        e.preventDefault();
        /*console.log(savePoint);*/
        /*const usedPoint = parseFloat($('#ordTotUsePoint').text().replace(/[^0-9.]+/g, ''));
        const completeTotPrice =  parseFloat($('#ordTotPrice').text().replace(/[^0-9.]+/g, ''));*/
        const recipName = $('#recipName').val();
        const recipHp = $('#recipHp').val();
        const recipZip = $('#recipZip').val();
        const recipAddr1 = $('#recipAddr1').val();
        const recipAddr2 = $('#recipAddr2').val();

        if(recipName == ""){
            alert('받으실 분의 이름을 입력하세요.');
            return;
        }else if(recipHp == ""){
            alert('받으실 분의 휴대폰 번호를 입력하세요.');
            return;
        }else if(recipZip == ""){
            alert('받으실 분의 주소를 입력하세요.');
            return;
        }
        // 이름이 주어진 형식과 일치하는지 검사
        if (!/^[a-zA-Z가-힣]{2,}$/.test(recipName)) {
            alert('이름이 유효하지 않습니다. 2자리 이상의 영문자 또는 한글만 입력하세요.');
            return;
        }
        // 휴대폰 번호가 주어진 형식과 일치하는지 검사
        if (!/^\d{3}-\d{4}-\d{4}$/.test(recipHp)) {
            alert('휴대폰 번호가 유효하지 않습니다. - 포함 11자리를 입력해주세요.');
            return;
        }

        alert('click');
        $(".orderForm").submit();


        //////////////////////////////////////////////////////////////////////////
        /*
        유효성 검증을 위해 AJAX 전송을 하려 했으나
        PROD NO 전송 이슈로 인해 AJAX 전송은 보류... FORM 전송으로 대체
        PROD NO들을 어떻게 보낼 것인지 연구 필요
        */
        //////////////////////////////////////////////////////////////////////////

        /*const length = [[${products.size()}]];
        const count = $('input[name=count]');
        const price = $('input[name=price]');
        const disPrice = $('input[name=disPrice]');
        const delivery = $('input[name=delivery]');
        const total = 0;
        let ordPrice = 0;
        let ordDiscount = 0;
        let ordDelivery = 0;
        let ordTotPrice = 0;
        for (let i = 0; i < length; i++) {
            ordPrice += (count[i].value * 1) * (price[i].value * 1);
            ordDiscount += ((price[i].value * 1) - (disPrice[i].value * 1)) * (count[i].value * 1);
            ordDelivery += (delivery[i].value * 1);
        }
        ordTotPrice += ordPrice - ordDiscount + ordDelivery;*/

        /*const ordCount = $('input[type="hidden"][name="ordCount"]').val();
        const ordPrice = $('input[type="hidden"][name="ordPrice"]').val();
        const ordDiscount = $('input[type="hidden"][name="ordDiscount"]').val();
        const ordDelivery = $('input[type="hidden"][name="ordDelivery"]').val();
        const savePoint = $('input[type="hidden"][name="savePoint"]').val();
        const inputUsedPoint = $('input[type="hidden"][name="usedPoint"]').val();
        const ordTotPrice = $('input[type="hidden"][name="ordTotPrice"]').val();
        const inputRecipName = $('input[type="text"][name="recipName"]').val();
        const inputRecipHp = $('input[type="text"][name="recipHp"]').val();
        const inputRecipZip = $('input[type="text"][name="recipZip"]').val();
        const inputRecipAddr1 = $('input[type="text"][name="recipAddr1"]').val();
        const inputRecipAddr2 = $('input[type="text"][name="recipAddr2"]').val();
        const ordPayment = $('input[type="radio"][name="ordPayment"]').val();

        const jsonData = {
                "ordCount": ordCount,
                "ordPrice": ordPrice,
                "ordDiscount": ordDiscount,
                "ordDelivery": ordDelivery,
                "savePoint": savePoint,
                "usedPoint": inputUsedPoint,
                "ordTotPrice": ordTotPrice,
                "recipName": inputRecipName,
                "recipHp": inputRecipHp,
                "recipZip": inputRecipZip,
                "recipAddr1": inputRecipAddr1,
                "recipAddr2": inputRecipAddr2,
                "ordPayment": ordPayment
        };

        console.log(JSON.stringify(jsonData));

        $.ajax({
            url: path+'/product/insertOrderForm',
            type: 'post',
            data: JSON.stringify(jsonData), // JSON 데이터를 문자열로 변환
            contentType: 'application/json', // 컨텐츠 타입을 JSON으로 설정
            dataType: 'json',
            success: function(data){
                if(data > 0 & ordPayment == 22){
                    alert('주문이 완료되었습니다. 24시간 이내 미입금시 취소됩니다.');
                    window.location.href = path+'/product/complete';
                }else if(data > 0){
                    alert('결제가 완료되었습니다.');
                    window.location.href = path+'/product/complete';
                }else if(data < 1){
                    alert('주문에 실패했습니다. 다시 시도해주세요.');
                    return;
                }
            }
        });*/
    });
});