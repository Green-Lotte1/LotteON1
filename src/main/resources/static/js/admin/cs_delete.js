$(function() {
    // check 처리
    const allChk = $('#allChk'); // 전체선택 토글
    const chk = $('.chk');       // 개별선택 토글
    allChk.change(function() {
        const isChecked = $(this).is(':checked');

        if(isChecked) {
            chk.prop('checked', true);
        }else {
            chk.prop('checked', false);
        }
    });

    chk.change(function() {
        const chkStatus = chk.map(function() {
            return $(this).is(':checked');
        }).get();

        if (chkStatus.indexOf(false) === -1) {
            allChk.prop('checked', true);
        } else {
            allChk.prop('checked', false);
        }
    });


    // view페이지에서 삭제 버튼 or list페이지에서 개별 삭제 버튼
    const btnDelete = $('.btnDelete');
    btnDelete.click(function(e) {
        if(confirm('정말 삭제하시겠습니까?')) {
            return true;
        }
        e.preventDefault();
    });



    // list페이지에서 선택 삭제 버튼
    const btnCD = $('.btnCD');
    btnCD.click(function(e) {
        if(confirm('정말 삭제하시겠습니까?')) {
            return true;
        }
        e.preventDefault();
    });
});