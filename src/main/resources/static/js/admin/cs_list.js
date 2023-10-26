/*cate1, cate2 선택시 list 출력*/
$(function() {
    const group = document.querySelector('.group').id;
    const cate1 = $('select[name=cate1]');
    const cate2 = $('select[name=cate2]');

    const prev = $('.prev');
    const numb = $('.num');
    const next = $('.next');

    const table = $('.adminList').parent();
    const path  = $('input[name=url]').val();

    const trigger = $('.shot');

    table.on('click', '.delete', function(e) {
        if(confirm('정말 삭제하시겠습니까?')) {
            $('input[name="chk"]:checked').each(function(){
                selects = selects + $(this).val() + "/";
            });
            console.log('total : ' + selects);

            const jsonData = {
                "noSelect": selects
            }

            $.ajax({
                url: path + '/cs/deletes',
                type: 'delete',
                data: JSON.stringify(jsonData),
                dataType: 'json',
                contentType: 'application/json',
                success: function(data) {
                    console.log(data);
                    location.href = path + '/admin/cs/' + group
                                + '/list?'
                                + '&success=' + data;
                }
            });
        }
        e.preventDefault();
    });

    $(document).on('change', '#allChk', function() {
        const isChecked = $(this).is(':checked');
        $('input[name="chk"]').prop('checked', isChecked);
    });

    $(document).on('change', 'input[name="chk"]', function() {
        let allChecked = true;
        $('input[name="chk"]').each(function() {
            if (!$(this).is(':checked')) {
                allChecked = false;
                return false; // each 루프 중단
            }
        });
        $('#allChk').prop('checked', allChecked);
    });



    $(cate1).change(function() {
        cate2.empty();
        cate2.append($('<option>', {
            value: '0',
            text: '2차유형'
        }));
    });

    // 유형 선택시
    $(trigger).change(function() {
        selectCate1 = (cate1.length != 0)?$('select[name=cate1]').val():null;
        selectCate2 = (cate2.length != 0)?$('select[name=cate2]').val():null;
        console.log("selectCate1 : " + selectCate1);
        console.log("selectCate2 : " + selectCate2);

        newUrl = path + '/admin/cs/' + group + '/write'
              + (selectCate1 != 0 ? '?cate1=' + selectCate1 : '')
              + (selectCate2 != 0 ? (selectCate2 != null ? '&cate2=' + selectCate2 : '') : '');
        $('.btnWrite').attr('href', newUrl);
        console.log('url : ' + newUrl);

        const jsonData = {
            "group": group,
            "cate1": selectCate1,
            "cate2": selectCate2
        }

        /*선택유형에따라 게시글 동적 생성*/
        $.ajax({
            url: path + '/cs/list',
            type: 'post',
            data: JSON.stringify(jsonData),
            dataType: 'json',
            contentType: 'application/json',
            success: function(data) {
                const list  = $('.adminList');

                list.remove();
                $('.notfind').remove();
                console.log('group : ' + data.group);

                if(data.total > 0) {
                    for(const dto of data.csList) {
                        var tr = $('<tr>').addClass('adminList');

                        tr.append('<td><input name="chk" type="checkbox" value="' + dto.no + '"></td>');
                        tr.append('<td>' + dto.no + '</td>');

                        if (data.group === 'notice') {
                            tr.append('<td>' + dto.cate1.cate1_name + '</td>');

                        } else if (data.group === 'faq' || data.group === 'qna') {
                            tr.append('<td>' + dto.cate1.cate1_name + '</td>');
                            tr.append('<td>' + dto.cate2.cate2_name + '</td>');
                        }

                        tr.append('<td class="tit"><a href="' + path + '/admin/cs/' + data.group + (dto.comment > 0 ? '/view?' : '/reply?')
                            + (dto.cate1.cate1 ? 'cate1=' + dto.cate1.cate1 : '')
                            + (selectCate2 != 0 ? (selectCate2 != null ? '&cate2=' + selectCate2 : '') : '')
                            + '&no=' + dto.no + '&pg=' + data.pg + '">' + dto.brTitle + '</a></td>');

                        if (data.group === 'qna') {
                            tr.append('<td>' + dto.uid.uid + '</td>');
                        } else if (data.group === 'notice' || data.group === 'faq') {
                            tr.append('<td>' + dto.hit + '</td>');
                        }

                        tr.append('<td>' + dto.yyMMdd + '</td>');

                        if (data.group === 'notice' || data.group === 'faq') {
                            tr.append('<td><a href="#">[삭제]</a><br>'
                                + '<a href="' + path + '/admin/cs/' + data.group + '/modify?'
                                + (dto.cate1.cate1 ? 'cate1=' + dto.cate1.cate1 + '&' : '')
                                + (selectCate2 != '2차유형' ? 'cate2=' + (dto.cate2 == null ? '' : dto.cate2.cate2) + '&' : '')
                                + 'no=' + dto.no + '">[수정]</a></td>');

                        } else if (data.group === 'qna') {
                            dto.comment>0? tr.append('<td style="color: green; font-weight: bold">답변완료</td>') : tr.append('<td>검토중</td>');
                        }
                        table.append(tr);

                    }
                }else {
                    /*var article = table.parent().parent().parent();*/
                    table.parent().after('<div class="notfind" style="text-align: center; font-size: 14px; padding: 50px 0;">'
                            + '<br><br>등록된 문의 내용이 없습니다.</div>');
                }
                prev.empty();
                numb.empty();
                next.empty();

                if(data.prev) {
                    prev.append('<a href="' + path + '/admin/cs' + data.group + '/list?'
                        + (dto.cate1.cate1 ? 'cate1=' + dto.cate1.cate1 + '&' : '')
                        + (selectCate2 != '2차유형' ? 'cate2=' + dto.cate2.cate2 + '&' : '')
                        + 'pg=' + data.start-1 + '"</a>');
                }

                for(var num = data.start ; num <= data.end ; num++) {
                    var hrefUrl  = path + '/admin/cs/' + data.group + '/list?'
                                  + (selectCate1 != '1차유형' ? 'cate1=' + selectCate1 + '&' : '')
                                  + (selectCate2 != '2차유형' ? 'cate2=' + selectCate2 + '&' : '')
                                  + 'pg=' + num;
                    var numClass = (data.pg == num) ? 'num on' : 'num';
                    numb.append($('<a>')
                                .attr('href', hrefUrl)
                                .addClass(numClass)
                                .text(num)
                    );
                }

                if(data.next) {
                    next.append('<a href="' + path + '/admin/cs' + data.group + '/list?'
                        + (dto.cate1.cate1 ? 'cate1=' + dto.cate1.cate1 + '&' : '')
                        + (selectCate2 != '2차유형' ? 'cate2=' + dto.cate2.cate2 + '&' : '')
                        + 'pg=' + data.end+1 + '"</a>')
                }


                /*2유형선택 동적생성*/
                $.ajax({
                    url: path + '/cs/cate2',
                    type: 'post',
                    data: JSON.stringify(jsonData),
                    dataType: 'json',
                    contentType: 'application/json',
                    success: function(data) {
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
            }
        });
    });
});