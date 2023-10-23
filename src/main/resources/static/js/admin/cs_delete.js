$(function() {
    const allChk = $('#allChk'); // 전체선택 토글
    const chk   = $('input[name=chk]'); // 개별선택 토글
    const path  = $('input[name=url]').val();
    const group = $('.group').attr('id');

    const c1 = $('input[name=cate1]');
    const c2 = $('input[name=cate2]');

    // 전체 선택
    allChk.change(function() {
        const isChecked = $(this).is(':checked');
        chk.prop('checked', isChecked);
    });

    // 개별 선택에 따라 전체 선택 토글 on/off
    chk.change(function() {
        let allChecked = true;
        chk.each(function() {
            if (!$(this).is(':checked')) {
                allChecked = false;
                return false; // each 루프 중단
            }
        });
        allChk.prop('checked', allChecked);
    });


    ///////////////////////////////////////////////////////
    // view 페이지에서 삭제 버튼 or list 페이지에서 개별 삭제 버튼
    $('.delete').click(function(e) {
        if(confirm('정말 삭제하시겠습니까?')) {
            const no = $(this).parent().parent().find('input[name=chk]').val();
            const jsonData = {
                "no": no
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

    // list페이지에서 선택 삭제 버튼
    const btnCD = $('.btnCD');
    var selects = "/";

    btnCD.click(function(e) {
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
});