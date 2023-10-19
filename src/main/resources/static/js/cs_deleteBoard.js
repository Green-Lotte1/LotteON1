$(function() {
    $('a[status=true]').click(function(e) {
        e.preventDefault();

        if(!confirm('정말 삭제하시겠습니까?')) {
            return false;
        }

        const no    = $('input[name=no]').val();
        const cate1 = $('input[name=cate1]').val();
        const path  = $('input[name=path]').val();
        const url   = path + '/cs/delete';
        console.log('no    : ' + no);
        console.log('cate1 : ' +cate1);
        console.log('url   : ' + url);

        const jsonData = {
            "no": no,
            "cate1": cate1
        }

        $.ajax({
            url: url,
            type: 'DELETE',
            data: JSON.stringify(jsonData),
            dataType: 'json',
            contentType: 'application/json',
            success: function(data) {
                // 성공시 list로
                // window.location.reload();
                if(data.result == 'success') {
                    alert('게시글이 삭제되었습니다.');
                    window.location.href = path + data.url;
                }else {
                    window.location.href = path + data.result;
                }
            }
        });
    });
});