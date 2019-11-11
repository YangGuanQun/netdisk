<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Net Disk</title>
    <link type="text/css" rel="stylesheet" href="/disk/css/global.css"/>
    <script type="text/javascript" src="/disk/js/lib/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="/disk/js/lib/bootstrap.min.js"></script>
    <style type="text/css">
        #imgFrame{ position:absolute; top:40%; left:40%; z-index:1000; display:none;}
    </style>
</head>
<body>
	<script>
        var deleteFile = function(fileName) {
            if (window.confirm("确认删除")) {
                $.ajax({
                    type: "post",
                    url: "/disk/file/delete/picture",
                    data: "file=" + fileName,
                    success: function () {
                        window.location.reload();
                    },
                    error: function () {
                        alert("删除失败。");
                    }
                });
            }
        };
        var copyToClipboard = function(text) {
            var clipboardInput = document.createElement('input');
            clipboardInput.setAttribute('id', 'clipboardInput');
            document.getElementsByTagName('body')[0].appendChild(clipboardInput);
            clipboardInput.value = window.location.origin + text;
            var el = document.getElementById('clipboardInput');
            el.select();
            document.execCommand("copy");
            el.remove();
        };
        function showOriginal(imgURL) {
            parent.viewImage(imgURL);
        }
    </script>
    <div class="container">
        <form action="/disk/file/upload/picture" method="post" id="fileForm"
              enctype="multipart/form-data">
            <p>选择文件: <input type="file" name="file"/></p>
            <p><input type="submit" value="提交"/></p>
        </form>
        <#list fileMap as dateStr,fileList>
        <div class="panel panel-primary">
            <div class="panel-heading">${dateStr}</div>
            <div class="panel-body" style="background-color: #b2f0ff">
                <#list fileList as fileItem>
                    <div class="thumbnail" style="float: left; margin-right: 20px">
                        <div style="width: 200px; height: 200px; text-align: center; vertical-align: middle;
                                display: table-cell" align="center" onclick="showOriginal
                                ('/disk/file/download/picture?file=${fileItem}')">
                            <img src="/disk/file/thumb?file=${fileItem}" alt="${fileItem}" style="margin: auto" />
                        </div>
                        <div class="caption">
                            <a target="_blank" href="/disk/file/download/picture?file=${fileItem}">
                                <h4>${fileItem}</h4>
                            </a>
                            <p>
                                <a id="btn-${fileItem}" onclick="copyToClipboard('/disk/file/download/picture?file=${fileItem}')"
                                   class="btn btn-primary" role="button">复制链接</a>
                                <a onclick="deleteFile('${fileItem}')" class="btn btn-default" role="button">删除</a>
                            </p>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
        </#list>
    </div>
</body>
</html>