$(document).ready(function () {

    // SELECT ALL
    $('#selectAll').on('change', function(){
        console.log('selectAll');

        // ALL CHECK CHECKED
        var allChecked = $(this).prop('checked');
        // ALL CHECKBOX CHECKED
        $('input[name^="cartNo"]').prop('checked', allChecked);

        // 합산 값 초기화
        var totalOrderAmount = 0;
        var totalCountAmount = 0;
        var totalOrgPriceAmount = 0;
        var totalDiscountAmount = 0;
        var totalDeliveryAmount = 0;
        var totalPointAmount = 0;

        // CHECKED CHECKBOX AMOUNT TOTAL
        $('input[name^="cartNo"]:checked').each(function() {
            // 합산 값을 초기화(누를때마다 초기화 해야되기 때문에 밖으로 빼면 안됨)
            var count = parseFloat($(this).closest('tr').find('td:eq(2)').text().replace(/,/g, ''));
            var orgPrice = parseFloat($(this).closest('tr').find('td:eq(3)').text().replace(/,/g, ''));
            var discount = parseFloat($(this).closest('tr').find('td:eq(4)').text().replace(/,/g, ''));
            var point = parseFloat($(this).closest('tr').find('td:eq(5)').text().replace(/,/g, ''));
            var delivery = parseFloat($(this).closest('tr').find('td:eq(6)').text().replace(/,/g, ''));
            var orderTotal = parseFloat($(this).closest('tr').find('td:last').text().replace(/,/g, ''));


            console.log('count : '+ count);
            console.log('orgPrice : '+ orgPrice);
            console.log('discount : '+ discount);
            console.log('point : '+ point);
            console.log('delivery : '+ delivery);
            console.log('order : '+ orderTotal);
            console.log('==============================================')

            // 할인된 금액과 정가의 차액
            discount = orderTotal - (orgPrice*count) - delivery;

            totalOrderAmount += orderTotal;
            totalOrgPriceAmount += orgPrice*count;
            totalDiscountAmount += discount;
            totalDeliveryAmount += delivery;
            totalPointAmount += point;
        });
        // 합산된 값을 전체주문금액(td#totalOrderAmount)에 입력
        $('#totalOrderAmount').text(totalOrderAmount.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'원');
        $('#totalCountAmount').text($('input[name^="cartNo"]:checked').length + '개');
        $('#totalOrgPriceAmount').text(totalOrgPriceAmount.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'원');
        $('#totalDiscountAmount').text(totalDiscountAmount.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'원');
        $('#totalPointAmount').text(totalPointAmount.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'점');
        // 배송비 총합이 0이면 무료라고 입력
        if(totalDeliveryAmount == 0){
            $('#totalDeliveryAmount').text('무료');
        }else{
            $('#totalDeliveryAmount').text(totalDeliveryAmount.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'원');
        }
    }); // SELECT ALL END


    $('input[name^="cartNo"]').on('change', function() {
        // 합산 값을 초기화(누를때마다 초기화 해야되기 때문에 밖으로 빼면 안됨)
        var totalOrderAmount = 0;
        var totalCountAmount = 0;
        var totalOrgPriceAmount = 0;
        var totalDiscountAmount = 0;
        var totalDeliveryAmount = 0;
        var totalPointAmount = 0;


        // 체크된 체크박스들의 cart.total 값을 합산
        $('input[name^="cartNo"]:checked').each(function() {
            var orderTotal = parseFloat($(this).closest('tr').find('td:last').text().replace(/,/g, ''));
            var count = parseFloat($(this).closest('tr').find('td:eq(2)').text().replace(/,/g, ''));
            var orgPrice = parseFloat($(this).closest('tr').find('td:eq(3)').text().replace(/,/g, ''));
            var discount = parseFloat($(this).closest('tr').find('td:eq(4)').text().replace(/,/g, ''));
            var point = parseFloat($(this).closest('tr').find('td:eq(5)').text().replace(/,/g, ''));
            var delivery = parseFloat($(this).closest('tr').find('td:eq(6)').text().replace(/,/g, ''));

            console.log('count : '+ count);
            console.log('orgPrice : '+ orgPrice);
            console.log('discount : '+ discount);
            console.log('point : '+ point);
            console.log('delivery : '+ delivery);
            console.log('order : '+ orderTotal);
            console.log('==============================================')

            // 할인된 금액과 정가의 차액
            discount = orderTotal - (orgPrice*count) - delivery;

            totalOrderAmount += orderTotal;
            totalOrgPriceAmount += orgPrice*count;
            totalDiscountAmount += discount;
            totalDeliveryAmount += delivery;
            totalPointAmount += point;
        });
        // 합산된 값을 전체주문금액(td#totalOrderAmount)에 입력
        $('#totalOrderAmount').text(totalOrderAmount.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'원');
        $('#totalCountAmount').text($('input[name^="cartNo"]:checked').length + '개');
        $('#totalOrgPriceAmount').text(totalOrgPriceAmount.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'원');
        $('#totalDiscountAmount').text(totalDiscountAmount.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'원');
        $('#totalPointAmount').text(totalPointAmount.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'점');
        // 배송비 총합이 0이면 무료라고 입력
        if(totalDeliveryAmount == 0){
            $('#totalDeliveryAmount').text('무료');
        }else{
            $('#totalDeliveryAmount').text(totalDeliveryAmount.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'원');
        }
    });

    //*******************************************************//
    //********************** 선택삭제 클릭 ************************//
    //*******************************************************//
    var selectedItemNos = "/";
    const path = $('#path').val();

    $('#del').click(function(e){
        e.preventDefault();
        alert('click');
        console.log('delete here...1');
        // 체크돼있는 prodNo를 배열로 만들어 넣음
        $('input[name^="cartNo"]:checked').each(function(){
            selectedItemNos = selectedItemNos + $(this).val() + "/";
            //selectedItemNos.push($(this).val());
            console.log('반복');
        });
        console.log('delete here...2');
        console.log('delete path: '+path);
        console.log(selectedItemNos);
        // 체크돼있는 prodNo를 배열이 없다면 돌려보냄.
        if(selectedItemNos === "/"){
            alert('선택된 상품이 없습니다.');
            return;
        }
        console.log('delete here...3');
        // 체크된 prodNo 전체와 uid를 jsonData로 만듬
        const jsonData ={
                "selectedItemNos" : selectedItemNos
        };

        console.log(JSON.stringify(jsonData));
        if(confirm('선택된 상품을 장바구니에서 삭제하시겠습니까?')){
            console.log('delete here...4');
            // jsonData를 전송
            // traditional: true는 배열을 ajax로 보낼때 있어야됨.
            $.ajax({
                url: path+'/product/deleteCartProduct',
                type: 'post',
                data: JSON.stringify(jsonData),
                //traditional: true,
                dataType: 'json',
                contentType: 'application/json;charset=UTF-8',
                success: function(data){
                    console.log('delete here...5');
                    // 삭제가 성공적으로 됐을때 알림 띄워주고 창 새로고침
                    if(data > 0){
                        alert('장바구니에서 삭제되었습니다.');
                        window.location.href = path+'/product/cart';
                    }else if(data < 1){
                        alert('다시 시도해주세요.');
                        return;
                    }
                }
            })// ajax end
        }else{
            return;
        }// 삭제 confirm end
    }); // 선택삭제 click end

    //*******************************************************//
    //******************* 선택된 상품 order *********************//
    //*******************************************************//
    $('input[name="order"]').click(function(e){
        e.preventDefault();
        /*alert('order click');*/

        // 체크돼있는 prodNo를 배열로 만들어 넣음
        $('input[name^="cartNo"]:checked').each(function(){
            selectedItemNos = selectedItemNos + $(this).val() + "/";
        });

        if(selectedItemNos === "/"){
            alert('주문할 상품을 선택해주세요.');
            return;
        }
        selectedItemNos= "/cart" + selectedItemNos;
        /*alert("selectedItemNos: "+selectedItemNos);*/

        const chk = {
            "selectedItemNos" : selectedItemNos
        }
        /*alert('chk: '+chk.selectedItemNos);*/
        console.log('here...1');
        window.location.href = path+'/product/order?chk='+ encodeURIComponent(chk.selectedItemNos);
        /*alert('다시 시도해주세요.');*/
        return;
        const parametersJsonData = $.param(jsonData);
        console.log(parametersJsonData);
        /*$.ajax({
            url: path+'/product/order',
            type: 'get',
            data: jsonData,
            dataType: 'json',
            contentType: 'application/json;charset=UTF-8',
            success: function(data){
                console.log('here...2');
                if(data > 0){
                    console.log('here...3');

                    console.log('here...4');
                    const newURL = path + '/product/order?' + parametersJsonData;
                    console.log('here...5');
                    alert('선택된 상품들을 주문합니다.');
                    window.location.href = newURL;
                }else if(data < 1){
                    alert('다시 시도해주세요.');
                    return;
                }
            }
        })*/
    }); // order click end
});