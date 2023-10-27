// 기존 데이터
let myEmail = $('#myEmail').val();
let myHp = $('#myHp').val();
console.log('myHp : '+myHp);
const birth = $('.birth');
const birthDate = birth.text();
let myZip = $('input[name=zip]').val();
let myAddr1 = $('input[name=addr1]').val();
let myAddr2 = $('input[name=addr2]').val();
let finalEmail = $('#finalEmail').val();
let finalHp = $('#finalHp').val();
const uid = $('input[name=uid]').val();
console.log("myuid : "+uid);

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

// 정규 표현식
const reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
const reHp    = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;