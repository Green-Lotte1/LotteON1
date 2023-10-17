
// 중복 검사
$(function(){
    // 아이디 중복 검사
    $('#btnId').click(function(){
        const uid = $('input[name=uid]').val();
        console.log(uid);

        $.ajax({
            url : '/LotteOn/member/check/uid/'+uid,
            type: 'GET',
            dataType: 'json',
            success:function(data){
                console.log("uid : "+data);
                if(data >= 1){
                    $(".msgId1").css('color','red').text('이미 사용중인 아이디 입니다.');
                }else{
                    $(".msgId1").css('color','green').text('사용가능한 아이디 입니다.');
                }
            }

        }); // ajax end
    });
    // 아이디 중복 검사
    $('input[name=email]').focusout(function(){
        const email = $('input[name=email]').val();
        console.log(email);

        $.ajax({
            url : '/LotteOn/member/check/email/'+email,
            type: 'GET',
            dataType: 'json',
            success:function(data){
                console.log("email : "+data);
                if(data >= 1){
                    $(".msgEmail").css('color','red').text('이미 사용중인 이메일 입니다.');
                }else{
                    $(".msgEmail").css('color','green').text('사용가능한 이메일 입니다.');
                }
            }

        }); // ajax end
    });
    // 아이디 중복 검사
    $('input[name=hp]').focusout(function(){
        const hp = $('input[name=hp]').val();
        console.log(hp);

        $.ajax({
            url : '/LotteOn/member/check/hp/'+hp,
            type: 'GET',
            dataType: 'json',
            success:function(data){
                console.log("hp : "+data);
                if(data >= 1){
                    $(".msgHp1").css('color','red').text('이미 사용중인 휴대폰 번호 입니다.');
                }else{
                    $(".msgHp1").css('color','green').text('사용가능한 휴대폰 번호 입니다.');
                }
            }

        }); // ajax end
    });

})