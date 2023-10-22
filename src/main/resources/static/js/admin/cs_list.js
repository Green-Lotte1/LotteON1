/*cate1, cate2 선택시 list 출력*/
$(function() {
    const group = document.querySelector('.group').id;
    const cate1 = $('select[name=cate1]');
    const cate2 = $('select[name=cate2]');

    const table = $('.adminList').parent();
    const url   = $('input[name=url]').val() + '/cs/list';

    const trigger = $('.shot');

    if(cate2.length == 0) {
        console.log('cate2 is null');
        /*공지사항의 경우 cate2를 출력하지않으므로 거기서 사용*/
    }

    // 1차 유형 선택시
    $(trigger).change(function() {
        selectCate1 = $(this).val();
        selectCate2 = (cate2.length != 0)?$('select[name=cate2]').val():null;
        console.log("selectCate1 : " + selectCate1);
        console.log("selectCate2 : " + selectCate2);

        const jsonData = {
            "group": group,
            "cate1": selectCate1,
            "cate2": selectCate2
        }

        $.ajax({
            url: url,
            type: 'post',
            data: JSON.stringify(jsonData),
            dataType: 'json',
            contentType: 'application/json',
            success: function(data) {
                const list  = $('.adminList');
                list.remove();
                console.log('group : ' + data.group);

                for(const dto of data.csList) {
                    if(dto.no > 0) {
                        var tr = $('<tr>').addClass('adminList');

                        tr.append('<td><input type="checkbox" value="' + dto.no + '"></td>');
                        tr.append('<td>' + dto.no + '</td>');

                        if (data.group === 'notice') {
                            tr.append('<td>' + dto.cate1.cate1_name + '</td>');

                        } else if (data.group === 'faq' || data.group === 'qna') {
                            tr.append('<td>' + dto.cate1.cate1_name + '</td>');
                            tr.append('<td>' + dto.cate2.cate2_name + '</td>');
                        }

                        tr.append('<td class="tit"><a href="/admin/cs/' + data.group + '/view?'
                            + (data.cate1 ? 'cate1=' + data.cate1 + '&' : '')
                            + (data.cate2 ? 'cate2=' + data.cate2 + '&' : '')
                            + 'no=' + dto.no + '&pg=' + data.pg + '">' + dto.title + '</a></td>');

                        if (data.group === 'qna') {
                            tr.append('<td>' + dto.uid.uid + '</td>');
                        } else if (data.group === 'notice' || data.group === 'faq') {
                            tr.append('<td>' + dto.hit + '</td>');
                        }

                        tr.append('<td>' + dto.yyMMdd + '</td>');

                        if (data.group === 'notice' || data.group === 'faq') {
                            tr.append('<td><a href="#">[삭제]</a><br><a href="/admin/cs/' + data.group + '/modify?'
                                + (data.cate1 ? 'cate1=' + data.cate1 + '&' : '')
                                + (data.cate2 ? 'cate2=' + data.cate2 + '&' : '')
                                + 'no=' + dto.no + '">[수정]</a></td>');

                        } else if (data.group === 'qna') {
                            tr.append('<td>' + (dto.parent === -1 ? '답변완료' : '검토중') + '</td>');
                        }

                        table.append(tr);
                    }
                }
            }
        });
    });
});