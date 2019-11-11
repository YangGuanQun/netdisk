<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Net Disk</title>
    <script src="../js/lib/jquery-1.11.1.min.js"></script>
</head>
<body>
	<script>
        var deleteFile = function(fileName) {
            $.ajax({
                type: "post",
                url: "/disk/file/delete/file",
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
    <form action="/disk/file/upload/file" method="post" enctype="multipart/form-data" id="fileForm">
        <p>选择文件: <input type="file" name="file" id="fileField"/></p>
        <p><input type="submit" value="提交"/></p>
    </form>
    <ul>
        <#list fileMap as dateStr,fileList>
            <li>${dateStr}
                <ul>
                    <#list fileList as fileItem>
                    <li>
                        <a target="_blank" href="/disk/file/download/file?file=${fileItem}">${fileItem}</a>
                        <button onclick="deleteFile('${fileItem}')">删除</button>
                    </li>
                    </#list>
                </ul>
            </li>
        </#list>
    </ul>
</div>
<script>
    $('#fileForm').on('submit', function(event){
        event.preventDefault(); //阻止form表单默认提交
        formSubmit();
    });
    function formSubmit () {
        var formData = new FormData();
        formData.append("file", $("#fileField")[0].files[0]);
        $.ajax({
            type: "post",
            url: "/disk/file/upload/file",
            data: formData,
            processData: false,
            success:function() {
                window.location.reload();
            }
        });
    }
</script>
</body>
</html>