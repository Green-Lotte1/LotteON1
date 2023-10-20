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
            var orderTotal = parseFloat($(this).closest('tr').find('td:last').text().replace(/,/g, ''));
            var countTotal = parseFloat($(this).closest('tr').find('td:eq(2)').text().replace(/,/g, ''));
            var orgPriceTotal = parseFloat($(this).closest('tr').find('td:eq(3)').text().replace(/,/g, ''));
            var discountTotal = parseFloat($(this).closest('tr').find('td:eq(4)').text().replace(/,/g, ''));
            var pointTotal = parseFloat($(this).closest('tr').find('td:eq(5)').text().replace(/,/g, ''));
            var deliveryTotal = parseFloat($(this).closest('tr').find('td:eq(6)').text().replace(/,/g, ''));

            // 할인된 금액과 정가의 차액
            discountTotal = orderTotal - orgPriceTotal - deliveryTotal;

            totalOrderAmount += orderTotal;
            totalOrgPriceAmount += orgPriceTotal;
            totalDiscountAmount += discountTotal;
            totalDeliveryAmount += deliveryTotal;
            totalPointAmount += pointTotal;
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
            var countTotal = parseFloat($(this).closest('tr').find('td:eq(2)').text().replace(/,/g, ''));
            var orgPriceTotal = parseFloat($(this).closest('tr').find('td:eq(3)').text().replace(/,/g, ''));
            var discountTotal = parseFloat($(this).closest('tr').find('td:eq(4)').text().replace(/,/g, ''));
            var pointTotal = parseFloat($(this).closest('tr').find('td:eq(5)').text().replace(/,/g, ''));
            var deliveryTotal = parseFloat($(this).closest('tr').find('td:eq(6)').text().replace(/,/g, ''));

            // 할인된 금액과 정가의 차액
            discountTotal = orderTotal - orgPriceTotal - deliveryTotal;

            totalOrderAmount += orderTotal;
            totalOrgPriceAmount += orgPriceTotal;
            totalDiscountAmount += discountTotal;
            totalDeliveryAmount += deliveryTotal;
            totalPointAmount += pointTotal;
        });
        // 합산된 값을 전체주문금액(td#totalOrderAmount)에 입력
        $('#totalOrderAmount').text(totalOrderAmount.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'원');
        $('#totalCountAmount').text($('input[name^="cartNo"]:checked').length + '개');
        $('#totalOrgPriceAmount').text(totalOrgPriceAmount.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'원');
        $('#totalDiscountAmount').text(totalDiscountAmount.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'원');
        // 배송비 총합이 0이면 무료라고 입력
        if(totalDeliveryAmount == 0){
            $('#totalDeliveryAmount').text('무료');
        }else{
            $('#totalDeliveryAmount').text(totalDeliveryAmount.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'원');
        }
        $('#totalPointAmount').text(totalPointAmount.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ",")+'점');
    });

});