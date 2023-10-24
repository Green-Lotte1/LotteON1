$(function() {
    /*const success = $('input[name=success]').val();*/

    const queryString = window.location.search;

    // URL에서 'success' 파라미터 값 추출
    const urlParams = new URLSearchParams(queryString);
    const success = urlParams.get('success');

    if(success != 0) {
        if(success == 100) {
            // 게시글 작성 실패
            alert('게시글 작성에 실패했습니다.');

        }else if(success == 101) {
            // 게시글 삭제 실패
            alert('게시글 삭제 실패. 다시 시도해주세요.');

        }else if(success == 200) {
            // 게시글 삭제 성공
            alert('게시글이 삭제 됐습니다.');

        }else if(success == 403) {
            // 게시글 삭제 실패(권한 없음)
            alert('서비스 이용 권한이 없습니다.')
        }
    }
});

/*

// 상단메뉴 마우스오버
$(document).ready(function() {
    var $groupMenu = $('.group-menu div');
    var $cate1Menu = $('.cate1-menu div');

    // group_name 메뉴에 마우스를 올렸을 때
    $groupMenu.hover(
        function() {
            // 현재 애니메이션이 진행 중이지 않을 때만 애니메이션 시작
            if (!$(this).find('span').is(':animated')) {
                $(this).find('span').stop().slideDown('fast');
            }
        },
        function() {
            // 현재 애니메이션이 진행 중이지 않을 때만 애니메이션 시작
            if (!$(this).find('span').is(':animated')) {
                $(this).find('span').stop().slideUp('fast');
            }
        }
    );

    // cate1_name 메뉴에 마우스를 올렸을 때
    $cate1Menu.hover(
        function() {
            // 현재 애니메이션이 진행 중이지 않을 때만 애니메이션 시작
            if (!$(this).find('span').is(':animated')) {
                $(this).find('span').stop().slideDown('fast');
            }
        },
        function() {
            // 현재 애니메이션이 진행 중이지 않을 때만 애니메이션 시작
            if (!$(this).find('span').is(':animated')) {
                $(this).find('span').stop().slideUp('fast');
            }
        }
    );
});
*/
