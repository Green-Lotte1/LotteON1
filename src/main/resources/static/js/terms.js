/*
 * 약관 체크 동의
 */
$(function(){
    console.log("checked...1")
    const agree1 = document.getElementsByName('agree1')[0];
    const agree2 = document.getElementsByName('agree2')[0];
    const agree3 = document.getElementsByName('agree3')[0];
    const agree4 = document.getElementsByName('agree4')[0];

    const typeInput = document.getElementsByName('type')[0];
    const type = typeInput.value;

    // 모두 동의 체크
    $('input[name=all]').change(function(){
        console.log("checked...2")
        const isChecked = $(this).is(':checked');

        if(isChecked){
            $('input[type=checkbox]').prop('checked',true);
        }else{
            $('input[type=checkbox]').prop('checked',false);
        }
    });

    // 약관 동의
    const agree = document.querySelector('.agree');
    agree.addEventListener('click', function(e){
        e.preventDefault();
        console.log("checked...3")
        if(!agree1.checked){
            alert('이용약관에 동의하셔야 합니다.');
            return;
        }else if(!agree2.checked){
            alert('전자금융거래 이용약관에 동의하셔야 합니다.');
            return;
        }else if(!agree3.checked){
            alert('개인정보 수집동의에 동의하셔야 합니다.');
            return;
        }else{

            // 일반 회원, 판매 회원 구분
            if(type == 'normal'){
                location.href ="/LotteOn/member/register";
            }else{
                location.href ="/LotteOn/member/registerSeller";
            }
        }
    })
})