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