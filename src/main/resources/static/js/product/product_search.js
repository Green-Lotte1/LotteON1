$(document).ready(function () {

    const keyword = $('#keyword').val();
    const path = $('#path').val();
    $(".searchDetail").on("keyup",function(key){
            if(key.keyCode==13) {
                alert("엔터키 이벤트");

                var detail = $(this).val();
                console.log("detail: "+detail);
                console.log("keyword: "+keyword);

                window.location.href = path+'/product/complete?keyword='+keyword+'&detail='+detail;
            }
        });


});