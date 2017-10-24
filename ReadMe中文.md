# PotatoDetector
（懒得翻译这部分。。）

This is an Android App for WOWS(World of Warships) players. Users simplily just need to run potato_server in PC side that fetches the real time match information. After connected with the server, users can find out teammates or enemies's performance level in the Android APP.

This app is greatly inspirded by <b>"Matchmaking Monitor Application" </b>
* https://github.com/jammin411/MatchmakingMonitor

The formula used to calculate the player's performance comes from <b>WOWS-number's PR formula</b>.
* https://na.wows-numbers.com/personal/rating

# 使用方法

## 步骤一. 启用游戏自带的录像功能（已启用请忽略）
官方的启用方法
* https://na.wargaming.net/support/kb/articles/517

## 步骤二. 安装java 1.7 或更高的运行环境（已安装请忽略）
You can go ahead and download JRE from Oracle.
* http://www.oracle.com/technetwork/java/javase/downloads/index.html

## 步骤三. 下载并运行电脑端potato服务器
本安卓app必须要从potato服务器中获得数据才能显示结果

### 先下载potato服务器
下载链接: * https://drive.google.com/file/d/0B_CDJZjuyTTqT19LNEFIVlRVR3M
### 解压并把文件放在replays文件夹中，路径如下
```
窝窝屎安装目录\replays\potato_server
```
如图:
<img src="https://raw.githubusercontent.com/Vigroid/PotatoDetector/master/screenshots/server_unzip.jpg">

### 执行potato_server.bat文件并选择区域。（可以创建个桌面快捷方式方便使用）
截图:
<img src="https://raw.githubusercontent.com/Vigroid/PotatoDetector/master/screenshots/region.jpg">

### 运行完后会显示ip地址，端口和一个二维码。注意不要关闭，否则服务器就断开了
截图:
<img src="https://raw.githubusercontent.com/Vigroid/PotatoDetector/master/screenshots/server_UI.jpg">

## 步骤四. 运行安卓app

### 输入服务器信息或者扫描二维码来与服务器连接
<img src="https://raw.githubusercontent.com/Vigroid/PotatoDetector/master/screenshots/connect.png" width="300">

### 如果连接成功，你可以看队友个对手信息
<img src="https://raw.githubusercontent.com/Vigroid/PotatoDetector/master/screenshots/team.png" width="300">
<img src="https://raw.githubusercontent.com/Vigroid/PotatoDetector/master/screenshots/enemy.png", width="300">

### 如果新游戏开始了，下滑刷新信息
<img src="https://raw.githubusercontent.com/Vigroid/PotatoDetector/master/screenshots/refresh.png" width="300">

# 特别感谢

My girlfriend who designed the icon and improve the UI for me! <3
