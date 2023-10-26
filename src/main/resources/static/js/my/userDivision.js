$(function (){

    // 기존 데이터
    let email = $('#myEmail').val();
    let hp = $('#myHp').val();
    console.log('myHp : '+hp);
    const birth = $('.birth');
    const birthDate = birth.text();
    let zip = $('input[name=zip]').val();
    let addr1 = $('input[name=addr1]').val();
    let addr2 = $('input[name=addr2]').val();
    let finalEmail = $('#finalEmail').val();
    let finalHp = $('#finalHp').val();

    // input 태그
    const email1Input = $('input[name="email1"]');
    const email2Input = $('input[name="email2"]');
    const hp1Input = $('input[name="hp1"]');
    const hp2Input = $('input[name="hp2"]');
    const hp3Input = $('input[name="hp3"]');

    // 분리된 이메일, 휴대폰 번호 데이터
    let emailId = '';
    let emailDomain = '';
    let hp1 = '';
    let hp2 = '';
    let hp3 = '';

    // 변경전 데이터(백업)
    let beforeEmail1 = '';
    let beforeEmail2 = '';
    let beforeHp1 = '';
    let beforeHp2 = '';
    let beforeHp3 = '';

    // "@" 문자를 기준으로 이메일 주소를 분리
    const emailParts = email.split('@');

    // email 데이터 조회
    if (emailParts.length === 2) {
        // 분리된 email를 각각 넣기
        email1Input.val(emailParts[0]);
        email2Input.val(emailParts[1]);

        // 변경 전 이메일 데이터 저장
        if (beforeEmail1 === '' || beforeEmail2 === '') {
            beforeEmail1 = email1Input.val();
            beforeEmail2 = email2Input.val();

            console.log('beforeEmail1 : '+beforeEmail1+', beforeEmail2 : '+beforeEmail2);
        }

        $('.emailDomain').change(function (){

            // 현재 선택된 옵션의 값 가져오기
            const selectedDomain = $(this).val();

            // "직접입력"이 아닌 경우에만 값을 설정
            if (selectedDomain !== '직접입력') {
                email2Input.val(selectedDomain);
            }
        });
    }

    // "-" 문자를 기준으로 전화번호를 분리
    const hpParts = hp.split('-');

    console.log('hpParts : '+hpParts);

    // hp 데이터 조회
    if (hpParts.length === 3) {

        hp1Input.val(hpParts[0]);  // "010"을 hp1의 value에 설정
        hp2Input.val(hpParts[1]); // "1234"을 hp2의 value에 설정
        hp3Input.val(hpParts[2]); // "5678"을 hp3의 value에 설정

        console.log('hpParts[0] : '+hpParts[0]);
        console.log('hpParts[1] : '+hpParts[1]);
        console.log('hpParts[2] : '+hpParts[2]);

        // 변경 전 휴대폰 번호 저장
        if(beforeHp1 === '' || beforeHp2 === '' || beforeHp3 === '') {
            beforeHp1 = hp1Input.val(hpParts[0]).val();
            beforeHp2 = hp2Input.val(hpParts[1]).val();
            beforeHp3 = hp3Input.val(hpParts[2]).val();

            console.log('beforeHp1 : '+beforeHp1+', beforeHp2 : '+ beforeHp2+',beforeHp3 : '+beforeHp3);
        }
    }

    // brith 데이터
    function formatBirthdate(birthDate) {
        // 생년월일 문자열에서 연, 월, 일 추출
        const year = birthDate.substring(0, 4);
        const month = birthDate.substring(4, 6);
        const day = birthDate.substring(6, 8);

        // 포맷된 문자열 생성
        const formattedBirthdate = `${year}년 ${Number(month)}월 ${Number(day)}일`;

        return formattedBirthdate;
    }
    const birthdateString = birthDate;
    const formattedBirthdate = formatBirthdate(birthdateString);
    birth.text(formattedBirthdate);

    //
    emailId = beforeEmail1;
    emailDomain = beforeEmail2;
    hp1 = beforeHp1;
    hp2 = beforeHp2;
    hp3 = beforeHp3;

    // 변경된 이메일 가져오기
    email1Input.on('focusout', function (){
        emailId = $(this).val();

        updateEmail();
    });
    email2Input.on('focusout', function (){
        emailDomain = $(this).val();

        updateEmail();
    });

    // 변경된 휴대폰 번호 가져오기
    hp1Input.on('focusout', function (){
        hp1 = $(this).val();

        updateHp();
    });
    hp2Input.on('focusout', function (){
        hp2 = $(this).val();

        updateHp();
    });
    hp3Input.on('focusout', function (){
        hp3 = $(this).val();

        updateHp();
    });

    //
    function updateEmail(){
        finalEmail = emailId + '@' + emailDomain;
        console.log('finalEmail :'+finalEmail);
    }
    function updateHp(){
        finalHp = hp1 + '-' + hp2 + '-' +hp3;
    }

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

        if(confirm('회원 정보를 수정하시겠습니까?')){

            const jsonData = {
                'email': finalEmail,
                'hp': finalHp,
                'zip': zip,
                'addr1': addr1,
                'addr2': addr2
            }

            $.ajax({
                url: '/LotteOn/my/update/user',
                type: 'put',
                data: jsonData,
                dataType: 'json',
                success: function (data){
                    console.log('데이터 수정 완료!');
                }
            });

        }
    });
});