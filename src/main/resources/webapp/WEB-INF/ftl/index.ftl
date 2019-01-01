<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Net Disk</title>
</head>
<body>
<div>
    <form action="file/upload" method="post" enctype="multipart/form-data">
        <p>选择文件: <input type="file" name="file"/></p>
        <p><input type="submit" value="提交"/></p>
    </form>
    <#list fileList as item>
        <p><a target="_blank" href="file/download?file=${item}">${item}</a></p>
    </#list>
</div>
</body>
</html>