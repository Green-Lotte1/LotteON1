$(function (){
    const cate1 = $('#category1');
    const cate2 = $('#category2');
    let selectCate = null;
    const ctxPath = $('input[name=ctxPath]').val();

    $(cate1).change(function() {
        const selectedCate1 = $(this).val();

        console.log(selectedCate1);

        const jsonData = {
            'prodCate1': selectedCate1
        }

        $.ajax({
            url: ctxPath +'/admin/product/register/category',
            type: 'post',
            data: JSON.stringify(jsonData),
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                console.log(data);

                // 기존 옵션 제거
                cate2.empty();

                console.log('empty complete' + data);

                // "2차 분류 선택" 옵션 추가
                cate2.append($('<option>', {
                    value: 'cate0',
                    text: '2차 분류 선택'
                }));

                console.log('append complete' + data);
                // 받아온 JSON 데이터를 기반으로 2차 분류 옵션 추가
                for (let i = 0; i < data.length; i++) {
                    cate2.append($('<option>', {
                        value: data[i].cate2,
                        text: data[i].c2Name
                    }));
                }
            }
        });
    });


});