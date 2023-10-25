/**
 * 데이터 유효성 검사
 */

// 검증 상태변수
let isUidOk		= false;
let isPassOk	= false;
let isNameOk	= false;
let isGenderOk          = false;
let isEmailOk	= false;
let isHpOk		= false;
let isSsnOk             = false;
let isTelOk             = false;
let isFaxOk		= false;
let isCompanyOk	= false;
let isBizOk		= false;
let isManagerOk	= false;
let isManagerHpOk= false;

// 정규표현식
const reUid   = /^[a-z]+[a-z0-9]{4,12}$/g;
const rePass  = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,12}$/;
const reName  = /^[가-힣]{2,10}$/
const reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
const reHp    = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;
const reSsn             = /^[0-9]{6}-[0-9]{7}$/;
const reTel	= /^(0[2-8][0-5]?)-?([1-9]{1}[0-9]{2,3})-?([0-9]{4})$/;
const reComNo = /^(?:[가-힣]{2,4}-[0-9]{5}|제\s\d-\d{2}-\d{2}-\d{4}호|\d{4}-[가-힣]{4}-\d{4})$/;
const reFax	= /^(0[2-8][0-5]?)-?([1-9]{1}[0-9]{2,3})-?([0-9]{4})$/;
const reCompany = /^\(주\)[a-zA-Z가-힣]{2,}$/;
const reBiz = /^[0-9]{3}-[0-9]{2}-[0-9]{5}$/;

$(function (){
    
    // 아이디 값 조작 방지
    $('input[name=uid]').keydown(function (){
        $('.msgId1').text('');
        isUidOk = false;
    });
    // 비밀번호 검사
    $('input[name=km_pass2]').focusout(function(){

        const pass1 = $('input[name=pass]').val();
        const pass2 = $('input[name=km_pass2]').val();

        if(pass1 == pass2){

            if(pass1.match(rePass)){ // 유효성 검사
                $('.msgPass1').css('color','green').text('사용할 수 있는 비밀번호 입니다.');
                isPassOk = true;
            } else {
                $('.msgPass1').css('color', 'red').text('비밀번호는 숫자, 영문, 특수문자 조합 8자리 이상이어야 합니다.');
                isPassOk = false;
            }

        } else { // 두 비밀번호가 일치하지 않을 시
            $('.msgPass1').css('color', 'red').text('비밀번호가 일치하지 않습니다.');
            isPassOk = false;
        }
        
    });
    
    // 성별 체크
    $('input[name=gender]').change(function (){
        const gender = $('input[name=gender]').val();
        if(gender < 0) {
            isGenderOk = false;
            return;
        }
        isGenderOk = true;
    });

    // 이름 유효성 검사
    $('input[name=name]').focusout(function (){
        const name = $('input[name=name]').val();

        if (!name.match(reName)){
            $('.msgName').css('color', 'red').text('유효한 이름이 아닙니다.');
            isNameOk = false;
            return;
        }
        $('.msgName').text('');
        isNameOk = true;
    });

    // 주민번호 유효성 검사
    $('input[name=ssn]').focusout(function (){
        const ssn = $('input[name=ssn]').val();

        if (!ssn.match(reSsn)){
            $('.msgSsh1').css('color', 'red').text('주민번호 양식에 맞게 입력해주십시오.');
            isSsnOk = false;
            return;
        }
        $('.msgSsh1').text('');
        isSsnOk = true;
    });

    // 담당자 이름 유효성 검사
    $('input[name=manager]').focusout(function (){
        const manager = $('input[name=manager]').val();

        if (!manager.match(reName)){
            $('.msgManager').css('color', 'red').text('유효한 이름이 아닙니다.');
            isNameOk = false;
            return;
        }
        $('.msgManager').text('');
        isNameOk = true;
    });

    // 회사명 검사
    $('input[name=company]').focusout(function(){
        const company = $(this).val();

        if(company.match(reCompany)){
            $('.msgCompany1').text('');
            isCompanyOk = true;
        } else {
            $('.msgCompany1').css('color', 'red').text('(주)포함하여 다시 입력해주십시오.');
            isCompanyOk = false;
        }

    });

    // 사업자등록번호 검사
    $('input[name=bizRegNum]').focusout(function(){
        const bizNum = $(this).val();

        if(bizNum.match(reBiz)){
            $('.msgCorp1').text('');
            isBizOk = true;
        } else {
            $('.msgCorp1').css('color', 'red').text('유효하지 않은 사업자등록번호입니다.');
            isBizOk = false;
        }
    });
    
    // 통신판매업번호 검사
    $('input[name=comRegNum]').focusout(function(){
        const comRegNum = $(this).val();

        if(comRegNum.match(reComNo)){
            $('.msgOnline1').text('');
            isBizOk = true;
        } else {
            $('.msgOnline1').css('color', 'red').text('유효하지 않은 통신판매업번호입니다.');
            isBizOk = false;
        }
    });
    // 전화번호 검사
    $('input[name=tel]').focusout(function(){
        const tel = $(this).val();

        if(tel.match(reTel)){
            $('.msgTel1').text('');
            isFaxOk = true;
        } else {
            $('.msgTel1').css('color', 'red').text('유효하지 않은 전화번호입니다.');
            isFaxOk = false;
        }
    });

    // 팩스번호 검사
    $('input[name=fax]').focusout(function(){
        const fax = $(this).val();

        if(fax.match(reFax)){
            $('.msgFax1').text('');
            isFaxOk = true;
        } else {
            $('.msgFax1').css('color', 'red').text('유효하지 않은 팩스번호입니다.');
            isFaxOk = false;
        }
    });

    // 담당자 번호 검사
    $('input[name=managerHp]').focusout(function(){
        const managerHp = $(this).val();

        if(managerHp.match(reHp)){
            $('.msgManagerHp1').text('');
            isManagerHpOk = true;
        } else {
            $('.msgManagerHp1').css('color', 'red').text('유효하지 않은 번호입니다.');
            isManagerHpOk = false;
        }
    });

    // 최종 전송
    $('#formMember').submit(function(e){

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

        if($('input[name=gender]').length > 0){
            if(!isGenderOk){
                alert('성별을 선택해주세요.');
                return false;
            }
        }

       // 판매회원 전용
        if ($('input[name=tel]').length > 0){
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
        }
        return true; // 폼 전송 시작

    });

});