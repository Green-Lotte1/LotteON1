$(document).ready(function () {

    let prevPoint = 0;
    		$('#usePoint').focusout(function(){
    			var usePoint = parseInt($(this).val());
    			var hasPoint = parseFloat($("#hasPoint").text());
    			// 현재 포인트 초과 입력시 현재 보유 포인트 값을 입력할거임
    			var correctPoint = Math.min(usePoint, hasPoint);

    			// 입력된 값이 숫자가 아니면 이전 prevPoint 값으로 복원
    	        if (!/^\d+$/.test(usePoint)) {
    	            $(this).val(prevPoint);
    	        }

    			// 현재 보유 포인트와 비교하여 입력된 값이 더 크면 현재 보유 포인트 값으로 업데이트
    	        if (usePoint > hasPoint) {
    	            $(this).val(correctPoint);
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
    		const ordTotPrice = $('.ordTotPrice').val();
    		$('#applyDiscount').click(function(){

    			if(prevPoint < 1){
    				alert('사용할 포인트를 입력해주세요');
    				$('.usePoint').text('0원');
    				$($('#ordTotPrice')).text($.numberWithCommas(ordTotPrice)+'원');
    				return;
    			}

    			var usePoint = $('#usePoint').val();
    			if(!isNaN(usePoint) & usePoint > 0){

    				$('.usePoint').text('-'+$.numberWithCommas(usePoint)+'원');

    				const disOrdTotPrice = ordTotPrice - $('#usePoint').val();

    				console.log('ordTotPrice :'+ordTotPrice);
    				console.log('usePoint :'+$('#usePoint').val());
    				console.log('disOrdTotPrice :'+disOrdTotPrice);

    				$($('#ordTotPrice')).text($.numberWithCommas(disOrdTotPrice)+'원');
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
    	 	let ordPayment = 11;
    	    $("input[name='payment']").change(function() {
    	        // 선택된 라디오 버튼의 value 값을 가져온다.
    	        var selectedPayment = $("input[name='payment']:checked").val();

    	        ordPayment = selectedPayment;
    	        console.log(selectedPayment);
    	    });
});