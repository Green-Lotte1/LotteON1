$(document).ready(function () {

    // 초기화
    let selectCate = null;
    const cate1 = $('select[name=type1]');
    const cate2 = $('select[name=type2]');
    console.log('9 : ' + cate1);
    console.log('10 : ' + cate2);

    // 1, 2차 유형 선택 없이 submit 시
    $('.btnSubmit').click(function(e) {
      e.preventDefault();
      if(cate1.val() < 1) {
        alert('1차 상세유형을 선택해주세요.');
        return false;
      }

      if(cate2.val() < 1) {
        alert('2차 상세유형을 선택해주세요.');
        return false;
      }

      $('#write').submit();
    });

    // 1차 유형 선택시
    $(cate1).change(function() {
        alert('cate1 change');
        const selectCate1 = $(this).val();
        console.log('31 : ' + selectCate1);

        const jsonData = {
            "type": "cateReloading",
            "selectCate": selectCate1
        }

        console.log('39 : ' + jsonData);
        $.ajax({
            url: '/cs/qna/write',
            type: 'post',
            data: jsonData,
            dataType: 'json',
            success: function(data) {
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