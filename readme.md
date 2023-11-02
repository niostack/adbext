# 为什么开发这个工具
> 做群控的时候，经常需要修改设备的语言和时区，但是 Android 系统的adb并没有提供这样的功能，
> 如果用点击交互的方式实现的话，不但效率低，而且还容易出错。
> 所以开发了这个工具。

# 怎么使用
> 在realease目录下下载最新的apk文件，安装到手机上，然后通过下面的adb 命令使用。

<p><b>本工具提供了通过 adb 命令修改系统语言和时区的功能，使用方式如下：</b></p>
<p><b>第一步：授权本工具的修改系统设置权限</b></p>
<p><code>adb shell pm grant com.niostack.adbext android.permission.CHANGE_CONFIGURATION</code></p>
<p><b>第二步：输入要修改的语言或时区</b></p>
命令示例(修改语言为中文)：
<p><code>adb shell am start -n com.niostack.adbext/.MainActivity --es language zh</code></p>
命令示例(修改时区为上海)：
<p><code>adb shell am start -n com.niostack.adbext/.MainActivity --es timezone Asia/Shanghai</code></p>
命令示例(修改语言为英文)：
<p><code>adb shell am start -n com.niostack.adbext/.MainActivity --es language en</code></p>
命令示例(修改时区为伦敦)：
<p><code>adb shell am start -n com.niostack.adbext/.MainActivity --es timezone Europe/London</code></p>
命令示例(同时设置时间和时区)：
<p><code>adb shell am start -n com.niostack.adbext/.MainActivity --es language en --es timezone Europe/London</code></p>