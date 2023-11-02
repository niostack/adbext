<!DOCTYPE html>
<html>
<head>
    <style>
        /* 设置文字自动换行 */
        p, li, pre {
            white-space: pre-wrap;
        }
        code {
            background-color: #f4f4f4; /* 设置背景颜色 */
            padding: 5px; /* 添加内边距以改进可读性 */
            border: 1px solid #ccc; /* 可选：添加边框 */
            border-radius: 4px; /* 可选：添加圆角边框 */
            display: block; /* 可选：将代码块显示为块级元素以换行 */
        }

    </style>
</head>
<body>
    <p><b>本工具提供了通过 adb 命令修改系统语言和时区的功能，使用方式如下：</b></p>
    <p><b>第一步：授权本工具的修改系统设置权限</b></p>
    <p><code>adb shell pm grant com.niostack.adbext android.permission.CHANGE_CONFIGURATION</code></p>
    <p><b>第二步：输入要修改的语言或时区</b></p>
    命令示例(修改语言为中文)：
    <p><code>adb shell am start -n com.niostack.adbext/.MainActivity --es language zh</code></p>
    命令示例(修改时区为东八区)：
    <p><code>adb shell am start -n com.niostack.adbext/.MainActivity --es timezone GMT+8</code></p>
    命令示例(修改语言为英国英文)：
    <p><code>adb shell am start -n com.niostack.adbext/.MainActivity --es language en_GB</code></p>
    命令示例(修改时区为伦敦)：
    <p><code>adb shell am start -n com.niostack.adbext/.MainActivity --es timezone Europe/London</code></p>
</body>
</html>