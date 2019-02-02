<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Net Disk</title>
    <script src="js/lib/jquery-1.11.1.min.js"></script>
</head>
<body>
	<script>
        var deleteFile = function(fileName) {
            $.ajax({
                type: "post",
                url: "file/delete",
                data: "file=" + fileName,
                success: function () {
                    window.location.reload();
                },
                error: function () {
                    alert("删除失败。");
                }
            });
        }
    </script>
<div>
    <form action="file/upload" method="post" enctype="multipart/form-data">
        <p>选择文件: <input type="file" name="file"/></p>
        <p><input type="submit" value="提交"/></p>
    </form>
    <#list fileList as item>
        <p><a target="_blank" href="file/download?file=${item}">${item}</a> <button onclick="deleteFile('${item}')">删除</button></p>
    </#list>
</div>
</body>
</html>