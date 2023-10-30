
$(function (){

    // 아이디 마스킹 처리
    const maskingUid = uid.replace(/^(.{3})(.*)$/, (_, prefix, rest) => prefix + '*'.repeat(rest.length));
    $('.uid').text(maskingUid);

    // 이메일 데이터 처리
    const emailParts = myEmail.split('@');
    if (emailParts.length === 2) {
        email1Input.val(emailParts[0]);
        email2Input.val(emailParts[1]);
        if (!beforeEmail1 && !beforeEmail2) {
            beforeEmail1 = email1Input.val();
            beforeEmail2 = email2Input.val();
        }
        $('.emailDomain').change(function () {
            const selectedDomain = $(this).val();
            if (selectedDomain !== '직접입력') {
                email2Input.val(selectedDomain);
            }
        });
    }

    // 휴대폰 데이터 처리
    const hpParts = myHp.split('-');
    if (hpParts.length === 3) {
        hp1Input.val(hpParts[0]);
        hp2Input.val(hpParts[1]);
        hp3Input.val(hpParts[2]);
        if (!beforeHp1 && !beforeHp2 && !beforeHp3) {
            beforeHp1 = hp1Input.val();
            beforeHp2 = hp2Input.val();
            beforeHp3 = hp3Input.val();
        }
    }

    // brith 데이터
    let year = birthDate.substring(0, 4);
    let month = birthDate.substring(4, 6);
    let day = birthDate.substring(6, 8);
    birth.text(year + '년 ' + month + '월 ' + day + '일');


    // 기본으로 변경전 데이터를 넣음
    emailId = beforeEmail1;
    emailDomain = beforeEmail2;
    hp1 = beforeHp1;
    hp2 = beforeHp2;
    hp3 = beforeHp3;

    // 이메일 수정 모드
    $('#btnChangeEmail').click(function (){
        const btn = this.parentElement;
        let btnText = this.textContent;
        console.log('btnEmail'+btnText);

        if (btnText == "수정하기") {
            // 수정하기 버튼 클릭시
            Array.from(btn.querySelectorAll('[readonly], [disabled]')).forEach(function(element) {
                element.removeAttribute('readonly'); // readonly 속성 제거
                element.removeAttribute('disabled'); // disabled 속성 제거
            });

            $('.emailDomain').val($('.emailDomain option:first').text()); // 이메일 옵션 원위치
            this.textContent = '수정취소';
        } else {
            // 수정취소 버튼 클릭시
            Array.from(btn.querySelectorAll('input:not([readonly]), select:not([disabled])')).forEach(function(element) {
                console.log('setAttribute...1');
                element.setAttribute('readonly', 'readonly'); // readonly 속성 다시 추가
                element.setAttribute('disabled', 'disabled'); // disabled 속성 다시 추가
            });

            // 이전 이메일 불러오기
            email1Input.val(beforeEmail1);
            email2Input.val(beforeEmail2);

            $('.emailDomain').val($('.emailDomain option:first').text());
            this.textContent = '수정하기';
        }
    });

    // 휴대폰 수정 모드
    $('#btnChangeHp').click(function (){
        const btn = this.parentElement;
        let btnText = this.textContent;
        console.log('btnHp : '+btnText);
        console.log('hp1 : '+hp1);
        console.log('hp2 : '+hp2);
        console.log('hp3 : '+hp3);

        if (btnText == "수정하기") {
            Array.from(btn.querySelectorAll('[readonly]')).forEach(function(element) {
                element.removeAttribute('readonly'); // readonly 속성 제거
            });

            this.textContent = '수정취소';
        } else {
            Array.from(btn.querySelectorAll('input:not([readonly])')).forEach(function(element) {
                console.log('setAttribute...1');
                element.setAttribute('readonly', 'readonly'); // readonly 속성 다시 추가
            });

            // 이전 휴대폰 번호 불러오기
            hp1Input.val(beforeHp1);
            hp2Input.val(beforeHp2);
            hp3Input.val(beforeHp3);

            this.textContent = '수정하기';
        }
    });


    // 회원 정보 수정
    $('#btnInfoChange').click( function (){

        emailId = email1Input.val();
        emailDomain = email2Input.val();
        hp1 = hp1Input.val();
        hp2 = hp2Input.val();
        hp3 = hp3Input.val();
        const finalZip = $('input[name=zip]').val();
        const finalAddr1 = $('input[name=addr1]').val();
        const finalAddr2 = $('input[name=addr2]').val();

        finalEmail = emailId + '@' + emailDomain;
        console.log('finalEmail :'+finalEmail);

        finalHp = hp1 + '-' + hp2 + '-' +hp3;
        console.log('finalHp : '+finalHp);

        // 유효성 검사
        if(!finalEmail.match(reEmail)){
            alert('입력된 이메일이 유효하지 않습니다.');
            return;
        }
        if(!finalHp.match(reHp)){
            alert('입력된 휴대폰 번호가 유효하지 않습니다.');
            return;
        }

        if(confirm('회원 정보를 수정하시겠습니까?')){

            const jsonData = {
                'uid':uid,
                'email': finalEmail,
                'hp': finalHp,
                'zip': finalZip,
                'addr1': finalAddr1,
                'addr2': finalAddr2
            }
            console.log(jsonData);

            $.ajax({
                url: '/LotteON/my/update/user',
                type: 'put',
                data: JSON.stringify(jsonData),
                dataType: 'json',
                contentType: 'application/json',
                success: function (data){
                    console.log('데이터 수정 완료!'+data);
                    window.location.href = '/LotteON/my/info';
                    alert('회원정보 설정이 정상적으로 변경되었습니다.');

                    // 유저 정보가 수정되었으므로 변경 전 데이터들은 비운다
                    beforeEmail1 = '';
                    beforeEmail2 = '';
                    beforeHp1 = '';
                    beforeHp2 = '';
                    beforeHp3 = '';

                    // 페이지 갱신
                    location.reload();
                }
            });

        }
    });
});