$(document).ready(function () {




    const keyword = $('#keyword').val();
    const path = $('#path').val();
    $(".searchDetail").on("keyup",function(key){
        alert("엔터키 이벤트");
        if(key.keyCode==13) {
            $("#searchDetail").click();
        }
    }); // ENTER KEY EVENT

    $("#searchDetail").on("click", function(){
        alert("클릭 이벤트");

        var detail = $(this).val();
        console.log("detail: "+detail);
        console.log("keyword: "+keyword);

        jsonData = {
            "keyword": keyword,
            "detail": detail
        }

        /*$.ajax({
            url: path+'/product/searchDetail',
            type: 'post',
            data: JSON.stringify(jsonData),
            //traditional: true,
            dataType: 'json',
            contentType: 'application/json;charset=UTF-8',
            success: function(data){


            }
        })// ajax end*/

        /*window.location.href = path+'/product/complete?keyword='+keyword+'&detail='+detail;*/
    });

});