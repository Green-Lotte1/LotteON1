/**
 * 데이터 유효성 검사
 */

// 검증 상태변수
let isUidOk		= false;
let isPassOk	= false;
let isNameOk	= false;
let isEmailOk	= false;
let isHpOk		= false;
let isFaxOk		= false;
let isCompanyOk	= false;
let isBizOk		= false;
let isManagerOk	= false;
let isManagerHpOk= false;

// 정규표현식
let reUid   = /^[a-z]+[a-z0-9]{4,12}$/g;
let rePass  = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,12}$/;
let reName  = /^[가-힣]{2,10}$/
let reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
let reHp    = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;
let reFax	= /^(0[2-8][0-5]?)-?([1-9]{1}[0-9]{2,3})-?([0-9]{4})$/;
let reCompany = /^\(주\)[a-zA-Z가-힣]{2,}$/;
let reBiz = /^[0-9]{3}-[0-9]{2}-[0-9]{5}$/;

$(function (){
    
    // 아이디 값 조작 방지
    $('input[name=uid]').keydown(function (){
        $('.msgId1').text('');
        isUidOk = false;
    });

    // 최종 전송
    $('#formMember').submit(function(){

        if(!isUidOk){
            alert('아이디를 확인 하십시요.');
            return false; // 폼 전송 취소
        }

        if(!isPassOk){
            alert('비밀번호를 확인 하십시요.');
            return false; // 폼 전송 취소
        }

        if(!isNameOk){
            alert('이름를 확인 하십시요.');
            return false; // 폼 전송 취소
        }

        if(!isEmailOk){
            alert('이메일을 확인 하십시요.');
            return false; // 폼 전송 취소
        }

        if(!isHpOk){
            alert('번호를 확인 하십시요.');
            return false; // 폼 전송 취소
        }

       /* // 판매회원 전용
        if ($('input[name=kms_fax]').length > 0){
            if(!isFaxOk){
                alert('팩스 번호를 확인 하십시요.');
                return false; // 폼 전송 취소
            }

            if(!isCompanyOk){
                alert('회사명을 확인 하십시요.');
                return false; // 폼 전송 취소
            }

            if(!isBizOk){
                alert('사업자등록번호를 확인 하십시요.');
                return false; // 폼 전송 취소
            }

            if(!isManagerOk){
                alert('담당자 이름을 확인 하십시요.');
                return false; // 폼 전송 취소
            }

            if(!isManagerHpOk){
                alert('담당자 번호를 확인 하십시요.');
                return false; // 폼 전송 취소
            }
        }*/

        return true; // 폼 전송 시작
    });

});