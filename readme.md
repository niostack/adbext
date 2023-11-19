# 为什么开发这个工具
> 做群控的时候，经常需要修改设备的语言和时区，但是 Android 系统的adb并没有提供这样的功能，
> 如果用点击交互的方式实现的话，不但效率低，而且还容易出错。
> 所以开发了这个工具。

# 怎么使用
> 在realease目录下下载最新的apk文件，安装到手机上，然后通过下面的adb 命令使用。

# 本工具提供了通过 adb 命令修改系统语言和时区的功能，使用方式如下：
* 第一步：授权本工具的修改系统设置权限
```shell
adb shell pm grant com.niostack.adbext android.permission.CHANGE_CONFIGURATION
```
* 第二步：输入具体的adb命令
命令示例(修改语言为中文)：
```shell
adb shell am start -n com.niostack.adbext/.MainActivity --es language zh
```
命令示例(修改时区为上海)：
```shell
adb shell am start -n com.niostack.adbext/.MainActivity --es timezone Asia/Shanghai
```
命令示例(修改语言为英文)：
```shell
adb shell am start -n com.niostack.adbext/.MainActivity --es language en
```
命令示例(修改时区为伦敦)：
```shell
adb shell am start -n com.niostack.adbext/.MainActivity --es timezone Europe/London
```
命令示例(同时设置时间和时区)：
```shell
adb shell am start -n com.niostack.adbext/.MainActivity --es language en --es timezone Europe/London
```
命令示例(连接wifi)
To join a wifi network with no password:
```shell
adb shell am start -n com.steinwurf.adbjoinwifi/.MainActivity --es ssid SSID
```
To join a password protected wifi network:
```shell
adb shell am start -n com.steinwurf.adbjoinwifi/.MainActivity \
--es ssid SSID --es password_type WEP|WPA --es password PASSWORD
```
To join a wifi network and set a static proxy (with optional bypass list):
```shell
adb shell am start -n com.steinwurf.adbjoinwifi/.MainActivity \
--es ssid SSID --es password_type WEP|WPA --es password PASSWORD \
--es proxy_host HOSTNAME --es proxy_port PORT [--es proxy_bypass COMMA,SEPARATED,LIST]
```
To join a wifi network and set a proxy auto-configuration URL:
```shell
adb shell am start -n com.steinwurf.adbjoinwifi/.MainActivity \
--es ssid SSID --es password_type WEP|WPA --es password PASSWORD \
--es proxy_pac_uri http://my.pac/url
```
To clear proxy settings, simply join the same network again and do not pass proxy arguments.

Modifying existing Wifi configurations
Note that android apps are not allowed to change the wifi configuration if it was created by another app (for example -- by the user in the Settings app). For this reason, if you try to use this app to join/modify an existing wifi network, this app will not modify it and will join it as-configured.

To get around this for testing purposes and modify any wifi configuration, you can grant this app device owner privileges:
```shell
adb shell dpm set-device-owner "com.niostack.adbext/.AdminReceiver"
```
This requires that your device has no provisioned accounts on it. If you wish to demote this app and remove its device owner privileges, run this:
```shell
adb shell am start -n com.steinwurf.adbjoinwifi/.MainActivity --es clear_device_admin true
```