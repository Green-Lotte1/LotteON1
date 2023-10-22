$(document).ready(function () {
    // 초기화
    const cate1 = $('select[name=cate1]');
    const cate2 = $('select[name=cate2]');
    const url   = $('input[name=url]').val() + '/cs/cate2';

    // 1차 유형 선택시
    $(cate1).change(function() {
        const selectCate = $(this).val();
        const jsonData = {
            "selectCate": selectCate
        }

        $.ajax({
            url: url,
            type: 'post',
            data: JSON.stringify(jsonData),
            dataType: 'json',
            contentType: 'application/json',
            success: function(data) {
                // 2차유형 초기화
                cate2.empty();
                cate2.append($('<option>', {
                    value: '0',
                    text: '2차유형'
                }));

                // 2차유형 동적처리
                for(let i=0 ; i<data.returnCate.length ; i++) {
                    const category = data.returnCate[i];
                    cate2.append($('<option>', {
                        value: category.cate2,
                        text: category.cate2_name
                    }));
                }
            }
        });
    });

    // 게시글 submit시 null값 체크
    $('.btnSubmit').click(function(e) {
          e.preventDefault();

        // 1, 2차 유형 선택 없이 submit 시
        if(cate1.val() == 0) {
            alert('1차 유형을 선택해주세요.');
            return false;
        }

        if(cate2.val() == 0) {
            alert('2차 유형을 선택해주세요.');
            return false;
        }

        if($('input[name=title]').val().trim() === '') {
            alert('제목을 입력해주세요.');
            return false;
        }
        // 제목, 내용 입력 없이 submit 시
        if($('textarea[name=content]').val().trim() === '') {
            alert('내용을 입력해주세요.');
            return false;
        }
        $('#write').submit();
    });
});