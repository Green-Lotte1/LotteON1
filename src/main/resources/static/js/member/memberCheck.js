// 중복 검사
$(function(){
    // 선택 사항 체크
    const local = localStorage.getItem('local');
    $('input[name=location]').val(local);
    localStorage.removeItem(local);

    // 아이디 중복 검사
    $('#btnId').click(function(){
        const uid = $('input[name=uid]').val();
        console.log(uid);
        if(!uid.match(reUid)){
            $('.msgId1').css('color', 'red').text('유효한 아이디가 아닙니다.');
            isUidOk = false;
            return;
        }

        $.ajax({
            url : '/LotteOn/member/check/uid/'+uid,
            type: 'GET',
            dataType: 'json',
            success:function(data){
                console.log("uid : "+data);
                if(data >= 1){
                    $(".msgId1").css('color','red').text('이미 사용중인 아이디 입니다.');
                    isUidOk = false;
                }else{
                    $(".msgId1").css('color','green').text('사용가능한 아이디 입니다.');
                    isUidOk = true;
                }
            }

        }); // ajax end
    });
    // 이메일 중복 검사
    $('input[name=email]').focusout(function(){
        const email = $('input[name=email]').val();
        console.log(email);

        if(!email.match(reEmail)){
            $('.msgEmail').css('color', 'red').text('유효한 이메일이 아닙니다.');
            isEmailOk = false;
            return;
        }

        $.ajax({
            url : '/LotteOn/member/check/email/'+email,
            type: 'GET',
            dataType: 'json',
            success:function(data){
                console.log("email : "+data);
                if(data >= 1){
                    $(".msgEmail").css('color','red').text('이미 사용중인 이메일 입니다.');
                    isEmailOk = false;
                }else{
                    $(".msgEmail").css('color','green').text('사용가능한 이메일 입니다.');
                    isEmailOk = true;
                }
            }

        });
    });
    // 휴대폰 번호 중복 검사
    $('input[name=hp]').focusout(function(){
        const hp = $('input[name=hp]').val();
        console.log(hp);

        if(!hp.match(reHp)){
            $('.msgHp1').css('color', 'red').text('유효한 휴대폰 번호가 아닙니다.');
            isHpOk = false;
            return;
        }

        $.ajax({
            url : '/LotteOn/member/check/hp/'+hp,
            type: 'GET',
            dataType: 'json',
            success:function(data){
                console.log("hp : "+data);
                if(data >= 1){
                    $(".msgHp1").css('color','red').text('이미 사용중인 휴대폰 번호 입니다.');
                    isHpOk = false;
                }else{
                    $(".msgHp1").css('color','green').text('사용가능한 휴대폰 번호 입니다.');
                    isHpOk = true;
                }
            }

        }); // ajax end
    });

})