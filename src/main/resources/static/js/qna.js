$(document).ready(function () {

    // 초기화
    let selectCate = null;
    const cate1 = $('select[name=cate1]');
    const cate2 = $('select[name=cate2]');
    const url   = $('select[name=url]');
    console.log('cate1 : ' + cate1);
    console.log('cate2 : ' + cate2);
    console.log('url   : ' + url);

    // 1, 2차 유형 선택 없이 submit 시
    $('.btnSubmit').click(function(e) {
      e.preventDefault();
      if(cate1.val() == 0) {
        alert('1차 유형을 선택해주세요.');
        return false;
      }

      if(cate2.val() == 0) {
        alert('2차 유형을 선택해주세요.');
        return false;
      }

      $('#write').submit();
    });

    // 1차 유형 선택시
    $(cate1).change(function() {
        const selectCate1 = $(this).val();
        console.log('selectCate1 : ' + selectCate1);

        const jsonData = {
            "selectCate1": selectCate1
        }

        $.ajax({
            url: url,
            type: 'post',
            data: JSON.stringify(jsonData),
            dataType: 'json',
            contentType: 'application/json',
            success: function(data) {
                alert('전송 시작');
                // 2차유형 초기화
                cate2.empty();
                cate2.empty($('<option>', {
                value: '0',
                text: '2차 선택'
              }));

              // 2차유형 동적처리
              for(let i=0 ; i<data.categorys.length ; i++) {
                const category = data.categorys[i];

                cate2.append($('<option>', {
                    value: cate.cate2,
                    text: cate.cate2_name
                }));
              }
            }
        });
    });
});