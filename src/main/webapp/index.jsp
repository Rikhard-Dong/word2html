<%--
  Created by IntelliJ IDEA.
  User: ride
  Date: 18-1-13
  Time: 下午9:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Title</title>

    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>

    <style>
        html, body {
            padding: 0;
            margin: 0;
        }

        main {
            margin-top: 32px;
        }
    </style>
</head>
<body>
<header>

</header>
<main>
    <div class="row">
        <div class="col-md-4 col-md-offset-1">
            <form id="file-upload" class="form-inline" enctype="multipart/form-data">
                <div class="form-group">
                    <input type="file" id="filename" accept=".doc" name="file">
                </div>
            </form>
            <button id="show-btn" class="btn btn-default">显示</button>
        </div>
        <div class="col-md-2 ">
            <button class="btn btn-primary">上传</button>
        </div>
    </div>

    <div class="row">
        <div class="col-md-10 col-md-offset-1">
            <div id="info">

            </div>
        </div>
    </div>
</main>
<script>
    let html = "<!DOCTYPE html>\n" +
        "<html lang=\"en\">\n" +
        "<head>\n" +
        "    <meta charset=\"UTF-8\">\n" +
        "    <title>Title</title>\n" +
        "</head>\n" +
        "<body>\n" +
        "<h1>预览文档区域</h1>\n" +
        "</body>\n" +
        "</html>";

    $(function () {
        $('#info').append($(html));
    });

    $('#show-btn').click(function () {
        console.log("click....");
        $.ajax({
            url: '${pageContext.request.contextPath}/doc?op=show',
            method: 'POST',
            cache: false,
            data: new FormData($('#file-upload')[0]),
            processData: false,
            contentType: false,
            success: function (result) {
                result = eval("(" + result + ")");
                console.log(result);
                if (result.code === 1) {
                    $('#info').empty();
                    $('#info').append($(result.data.content));
                } else if (result.code === 0) {
                    alert(result.data.msg);
                }
            }
        });
    })
</script>
</body>
</html>
