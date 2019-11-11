<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title></title>
    <link type="text/css" rel="stylesheet" href="css/global.css"/>
    <script type="text/javascript" src="js/lib/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="js/lib/bootstrap.min.js"></script>
    <style>
        .gray-layer {
            z-index: 1000;
            bottom: 0;
            left: 0;
            margin: 0;
            overflow: auto;
            position: fixed;
            right: 0;
            top: 0;
            background-color: #2c3e50;
            opacity:0.4;
        }

        #preview {
            z-index: 1001;
            bottom: 0;
            left: 0;
            margin: auto;
            overflow: auto;
            position: fixed;
            right: 0;
            top: 0;
        }
    </style>
</head>
<body>
    <div class="container">
        <ul class="nav nav-tabs" id="main-tab">
            <li role="presentation"><a onclick="switchTab(1)" id="fileTab">文件</a></li>
            <li role="presentation"><a onclick="switchTab(2)" id="pictureTab">图片</a></li>
        </ul>
        <div class="tab-content">
            <iframe id="content-page" src="/disk/list/file" frameborder="0" width="100%"></iframe>
        </div>
    </div>
    <div id="gray-layer" onclick="closeView()" style="display: none;text-align: center; vertical-align: middle;" align="center">
        <div class="gray-layer"></div>
        <img id="preview" src="" />
    </div>
</body>
<script>
    function switchTab(tab) {
        if (tab===1) {
            $("#content-page")[0].contentWindow.location.href = '/disk/list/file';
        } else if (tab===2) {
            $("#content-page")[0].contentWindow.location.href = '/disk/list/picture';
        }
    };
    $(function(){
        var height = window.screen.availHeight;
        $("#content-page").height(height * 0.9);
    });
    function viewImage(imageUrl) {
        $('#gray-layer').css("display", "table-cell");
        $('#preview')[0].src = imageUrl;
    }
    function closeView() {
        $('#gray-layer').hide();
    }
</script>
</html>
