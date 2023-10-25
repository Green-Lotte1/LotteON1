$(function (){

    // 기존 데이터
    let email = $('#myEmail').val();
    let hp = $('#myHp').val();
    const birth = $('.birth');
    const birthDate = birth.text();
    let zip = $('input[name=zip]').val();
    let addr1 = $('input[name=addr1]').val();
    let addr2 = $('input[name=addr2]').val();

    // input 태그
    const email1Input = document.querySelector('input[name="email1"]');
    const email2Input = document.querySelector('input[name="email2"]');
    const hp1Input = document.querySelector('input[name=hp1]');
    const hp2Input = document.querySelector('input[name=hp2]');
    const hp3Input = document.querySelector('input[name=hp3]');

    // 분리된 이메일, 휴대폰 번호 데이터
    let emailId = email1Input.value;
    let emailDomain = email2Input.value;
    let hp1 = hp1Input.value;
    let hp2 = hp2Input.value;
    let hp3 = hp3Input.value;

    // 변경전 데이터(백업)
    let beforeEmail1 = '';
    let beforeEmail2 = '';
    let beforeHp1 = '';
    let beforeHp2 = '';
    let beforeHp3 = '';

    // "@" 문자를 기준으로 이메일 주소를 분리
    const emailParts = email.split('@');

    // "-" 문자를 기준으로 전화번호를 분리
    const hpParts = hp.split('-');
    

    // email 데이터 조회
    if (emailParts.length === 2) {
        // 분리된 email를 각각 넣기
        emailId = emailParts[0];
        emailDomain = emailParts[1];
        
        // 변경 전 이메일 데이터 저장
        beforeEmail1 = email1Input.value;
        beforeEmail2 = email2Input.value;

        $('.emailDomain').change(function (){

            // 현재 선택된 옵션의 값 가져오기
            const selectedDomain = this.value;

            // "직접입력"이 아닌 경우에만 값을 설정
            if (selectedDomain !== '직접입력') {
                email2Input.value = selectedDomain;
            }
        });
    }

    // hp 데이터 조회
    if (hpParts.length === 3) {

        hp1 = hpParts[0]; // "010"을 hp1의 value에 설정
        hp2 = hpParts[1]; // "1234"을 hp2의 value에 설정
        hp3 = hpParts[2]; // "5678"을 hp3의 value에 설정

        // 변경 전 휴대폰 번호 저장
        beforeHp1 = hp1Input.value;
        beforeHp2 = hp2Input.value;
        beforeHp3 = hp3Input.value;
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
    const birthdateString = "20011030";
    const formattedBirthdate = formatBirthdate(birthdateString);
    birth.text(formattedBirthdate);

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
            email1Input.value = beforeEmail1;
            email2Input.value = beforeEmail2;

            $('.emailDomain').val($('.emailDomain option:first').text());
            this.textContent = '수정하기';
        }
    });

    // 휴대폰 수정 모드
    $('#btnChangeHp').click(function (){
        const btn = this.parentElement;
        let btnText = this.textContent;
        console.log('btnHp : '+btnText);

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
            hp1Input.value = beforeHp1;
            hp2Input.value = beforeHp2;
            hp3Input.value = beforeHp3;

            this.textContent = '수정하기';
        }


    });

    
    // 회원 정보 수정
    $('#btnInfoChange').addEventListener('click', function (){

        if(confirm('회원 정보를 수정하시겠습니까?')){

            // 분리된 이메일, 휴대폰 번호 통합
            //email =

            const jsonData = {
                'email': email,
                'hp': hp,
                'zip': zip,
                'addr1': addr1,
                'addr2': addr2
            }


        }
    });
});