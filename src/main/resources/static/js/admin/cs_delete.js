$(function() {
    const allChk = $('#allChk'); // 전체선택 토글
    const chk = $('input[name=chk]'); // 개별선택 토글
    const path  = $('input[name=url]').val();

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
    const btnDelete = $('.btnDelete');
    btnDelete.click(function(e) {
        if(confirm('정말 삭제하시겠습니까?')) {
            return true;
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
                "noSelect" = selects;
            }

            $.ajax({
                url: path + '/cs/deletes',
                type: 'delete',
                data: JSON.stringify(jsonData),
                dataType: 'json',
                contentType: 'application/json',
                success: function(data) {
                    console.log(data);
                }

            });
        }
        e.preventDefault();
    });
});