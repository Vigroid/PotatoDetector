# PotatoDetector

This is an Android App for WOWS(World of Warships) players. Users simplily just need to run potato_server in PC side that fetches the real time match information. After connected with the server, users can find out teammates or enemies's performance level in the Android APP.

This app is greatly inspirded by <b>"Matchmaking Monitor Application" </b>
* https://github.com/jammin411/MatchmakingMonitor

The formula used to calculate the player's performance comes from <b>WOWS-number's PR formula</b>.
* https://na.wows-numbers.com/personal/rating

# User Guide

## Step 1. Enable WOWS replays in PC side (can skip this step if replays is already enabled)
To enable WOWS replays, there is offical guide from Wargaming.
* https://na.wargaming.net/support/kb/articles/517

## Step 2. Got Java Runtime Environment 1.7 or higher in PC side (can skip this if already installed)
You can go ahead and download JRE from Oracle.
* http://www.oracle.com/technetwork/java/javase/downloads/index.html

## Step 3. Download and run the potato_server
To receive information in Android App, you have to download and run the potato_server in your PC. 

### First, download the potato_server.
Dowload Link: * https://drive.google.com/file/d/0B_CDJZjuyTTqT19LNEFIVlRVR3M
### Unzip the file and put in your WOWS replay folder. It should look like this:
```
Your_WOWS_Game_Folder\replays\potato_server
```
Screenshot:
<img src="https://raw.githubusercontent.com/Vigroid/PotatoDetector/master/screenshots/server_unzip.jpg">

### Execute the potato_server.bat and select a region. (You can create a shortcut in desktop to execute it easier latter)
Screenshot:
<img src="https://raw.githubusercontent.com/Vigroid/PotatoDetector/master/screenshots/region.jpg">

### A window should pop up and show the IP, port and QRcode.
Screenshot:
<img src="https://raw.githubusercontent.com/Vigroid/PotatoDetector/master/screenshots/server_UI.jpg">

## Step 4. Launch the WOWS Match Monitor Android App

### Input or Scan the QRcode to connect with potato_server.
<img src="https://raw.githubusercontent.com/Vigroid/PotatoDetector/master/screenshots/connect.png" width="300">

### If connected, you can view teammates or enemy performance now. Default order is sorted descendingly by PR value.
<img src="https://raw.githubusercontent.com/Vigroid/PotatoDetector/master/screenshots/team.png" width="300">
<img src="https://raw.githubusercontent.com/Vigroid/PotatoDetector/master/screenshots/enemy.png", width="300">

### If a new game started, you can refresh by swipe down.
<img src="https://raw.githubusercontent.com/Vigroid/PotatoDetector/master/screenshots/refresh.png" width="300">

# Special Thanks

My girlfriend who designed the icon and improve the UI for me! <3
