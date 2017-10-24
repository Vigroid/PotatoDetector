# PotatoDetector

This is an Android App for WOWS(World of Warships) players. Users simplily just need to scan QR code or type Server Address and port to get access to real time WOWS game information.

# User Guide

## 1. Enable WOWS replays in PC side
To enable WOWS replays, there is offical guide from Wargaming.
* https://na.wargaming.net/support/kb/articles/517

## 2. Got Java Runtime Environment 1.7 or higher in PC side

You can skip this if JRE is already installed. Otherwise, You can go ahead and download JRE from Oracle.
* http://www.oracle.com/technetwork/java/javase/downloads/index.html

## 3. Download and run the potato_server

To receive information in Android App, you have to download and run the potato_server in your PC. 

### First, download the potato_server.
Dowload Link: * https://drive.google.com/file/d/0B_CDJZjuyTTqT19LNEFIVlRVR3M
### Unzip the file and put in your WOWS replay folder. It should look like this:
```
Your_WOWS_Game_Folder\replays\potato_server
```
Screenshot:
<img src="https://raw.githubusercontent.com/Vigroid/PotatoDetector/master/screenshots/server_unzip.jpg">

### Execute the potato_server.bat and select a region.
Screenshot:
<img src="https://raw.githubusercontent.com/Vigroid/PotatoDetector/master/screenshots/region.jpg">

### A window should pop up and show the IP, port and QRcode.
Screenshot:
<img src="https://raw.githubusercontent.com/Vigroid/PotatoDetector/master/screenshots/server_UI.jpg">

## 4. Launch the WOWS Match Monitor Android App

## Input or Scan the QRcode to connect with potato_server.
<img src="https://raw.githubusercontent.com/Vigroid/PotatoDetector/master/screenshots/connect.png" width="256">

## If connected, you can view teammates or enemy performance now. Default order is sorted decendantly by PR value.
<img src="https://raw.githubusercontent.com/Vigroid/PotatoDetector/master/screenshots/team.png" width="256">
<img src="https://raw.githubusercontent.com/Vigroid/PotatoDetector/master/screenshots/enemy.png", width="256">

## If a new game started, you can refresh by swipe down.
<img src="https://raw.githubusercontent.com/Vigroid/PotatoDetector/master/screenshots/refresh.png" width="256">
