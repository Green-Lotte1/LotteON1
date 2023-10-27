$(function (){

    // SELECT ALL
    $('.selectAll').on('change', function() {
        console.log('selectAll');

        // ALL CHECK CHECKED
        let allChecked = $(this).prop('checked');
        // ALL CHECKBOX CHECKED
        $('input[name=prod]').prop('checked', allChecked);
    });

    let checkProdNo = [];

    // 선택 삭제
    $('.deleteProduct').click(function () {
        console.log('delete here...1');
        // 체크돼있는 prodNo를 배열로 만들어 넣음
        $('input[name=prod]:checked').each(function () {
            checkProdNo.push($(this).closest("tr").find("td:nth-child(3)").text());
            console.log('반복');
        });
        console.log('delete here...2');
        //console.log('delete path: ' + path);
        console.log(checkProdNo);
        // 체크돼있는 prodNo를 배열이 없다면 돌려보냄.
        if (checkProdNo.length === 0) {
            alert('선택된 상품이 없습니다.');
            return;
        }
        console.log('delete here...3');
        // 체크된 prodNo 전체와 uid를 jsonData로 만듬
        const jsonData = {
            "prodNo": checkProdNo
        };

        console.log(jsonData);
        if (confirm('선택된 상품을 삭제하시겠습니까?')) {
            console.log('delete here...4');
            // jsonData를 전송
            // traditional: true는 배열을 ajax로 보낼때 있어야됨.
            $.ajax({
                url: '/LotteON/admin/product/deleteCheckProduct',
                type: 'POST',
                data: JSON.stringify(jsonData),
                //traditional: true,
                dataType: 'json',
                contentType: 'application/json;charset=UTF-8',
                success: function (data) {
                    console.log('delete here...5 '+checkProdNo);

                    checkProdNo.forEach(function (prodNo) {
                        console.log('delete here...6');
                        // 행 숨기기
                        $('td:contains(' + prodNo + ')').closest('tr').hide();
                    });

                }
            })// ajax end
        } else {
            return;
        }// 삭제 confirm end
    }); // 선택삭제 click end
});