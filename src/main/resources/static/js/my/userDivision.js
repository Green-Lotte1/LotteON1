$(function (){

   /* $.ajax({
        url : '/LotteOn/my/getUser',
        contentType : ''

    });*/

    /*const email = '[[${myMember.email}]]';
    const hp = '[[${myMember.hp}]]';*/
    const birth = $('.birth');
    const birthDate = birth.text();


    // "@" 문자를 기준으로 이메일 주소를 분리
    const emailParts = email.split('@');

    // "-" 문자를 기준으로 전화번호를 분리
    const hpParts = hp.split('-');

    // email 데이터 조회
    if (emailParts.length === 2) {
        const email1Input = document.querySelector('input[name="email1"]');
        const email2Input = document.querySelector('input[name="email2"]');

        // 분리된 email를 각각 넣기
        email1Input.value = emailParts[0];
        email2Input.value = emailParts[1];

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
        const hp1Input = document.querySelector('input[name="hp1"]');
        const hp2Input = document.querySelector('input[name="hp2"]');
        const hp3Input = document.querySelector('input[name="hp3"]');

        hp1Input.value = hpParts[0]; // "010"을 hp1의 value에 설정
        hp2Input.value = hpParts[1]; // "1234"을 hp2의 value에 설정
        hp3Input.value = hpParts[2]; // "5678"을 hp3의 value에 설정
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

});