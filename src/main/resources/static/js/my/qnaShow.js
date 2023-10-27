$(function() {
    const path = $('.path').val();
    /*$('.tit').off('click').on('click', function(e) {
        e.preventDefault();

        var $answerRow = $(this).closest('tr').next('.answerRow');
        var displayStyle = $answerRow.css('display');
        console.log(' current display : ' + displayStyle);

        if(displayStyle === 'none') {
            $answerRow.css('display', 'table-row');
        }else {
            $answerRow.css('display', 'none');
        }
    });*/

    $('.tit').off('click').on('click', function(e) {
        e.preventDefault();

        var status = $(this).parent();

        if (status.hasClass('none')) {
            return false;

        }else if(status.hasClass('on')) {
            status.removeClass('on')
                .addClass('close')
                .next().css('display', 'none');
            return false;

        }else if(status.hasClass('close')) {
            status.removeClass('close')
                .addClass('on')
                .next().css('display', 'table-row');
            return false;

        }else if(status.hasClass('off')) {
            status.removeClass('off')
                .addClass('on');
        }

        $(this).parent().addClass('')

        const no    = $(this).children('input[name=no]').val();
        const table = $(this).parent().parent();
        console.log('no : ' + no);

        const qtitle   = $(this).children('input[name=title]').val();
        const qcontent = $(this).children('input[name=content]').val();
        const qrdate   = $(this).children('input[name=rdate]').val();

        console.log('qtitle   : ' + qtitle);
        console.log('qcontent : ' + qcontent);
        console.log('qrdate   : ' + qrdate);

        jsonData = {
            "qno": no
        }

        $.ajax({
            url: path + '/my/myQna',
            type: 'post',
            data: JSON.stringify(jsonData),
            dataType: 'json',
            contentType: 'application/json',
            success: function(data) {
                console.log('title   : ' + data.answer.title);
                console.log('title   : ' + data.answer.brTitle);
                console.log('content : ' + data.answer.content);
                console.log('content : ' + data.answer.brContent);
                console.log('rdate   : ' + data.answer.yyyyMMddHHmmss);
                var tr = $('<tr>').addClass('answerRow').css('display', 'table-row');
                tr.append('<td colspan="6">'
                            + '<div class="question">'
                                + '<p class="tit">'
                                    + qtitle
                                    + '<span class="date">' + qrdate + '</span>'
                                + '</p>'
                                + '<p class="content">'
                                    + qcontent
                                + '</p>'
                            + '</div>'

                            + '<div class="answer">'
                                + '<p class="tit">'
                                    + data.answer.brTitle
                                    + '<span class="date">' + data.answer.yyyyMMddHHmmss + '</span>'
                                + '</p>'
                                + '<p class="content">'
                                    + data.answer.content
                                + '</p>'
                            + '</div>'
                        + '</td>');
                status.after(tr);
            }
        });
    });
});