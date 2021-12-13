# Ratel

[![GitHub forks](https://img.shields.io/github/forks/ainilili/ratel?style=flat-square)](https://github.com/ainilili/ratel/network)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/ainilili/ratel?style=flat-square)
![GitHub all releases](https://img.shields.io/github/downloads/ainilili/ratel/total?logo=spring&style=flat-square)
[![GitHub stars](https://img.shields.io/github/stars/ainilili/ratel?logo=java&style=flat-square)](https://github.com/ainilili/ratel/stargazers)
[![GitHub license](https://img.shields.io/github/license/ainilili/ratel?logo=apache&style=flat-square)](https://github.com/ainilili/ratel/blob/master/LICENSE)
![Build ratel(Java with Maven)](https://github.com/ainilili/ratel/workflows/Build%20ratel(Java%20with%20Maven)/badge.svg?branch=master)
![Docker Image Version (latest by date)](https://img.shields.io/docker/v/kebyn/ratel?label=Docker&logo=docker&style=flat-square)

## 介绍
基于Netty实现的命令行斗地主游戏，为划水摸鱼而生~

> 新版开发中：[直通车](https://github.com/ratel-online)，新增癞子模式，增加超时机制，完美复现欢乐斗地主，欢迎体验在线版 [http://rtol.isnico.com/](http://rtol.isnico.com/) 

## 安装
首先下载打包，确保本地安装有maven及JRE环境：
```powershell
git clone https://github.com/ainilili/ratel.git
cd ratel
mvn install package
```
接下来分别运行 ``landlords-client`` 和 ``landlords-server`` 的 ``target`` 文件夹下的Jar包：
```powershell
java -jar landlords-server/target/landlords-server-#{version}.jar -p 1024
java -jar landlords-client/target/landlords-client-#{version}.jar -p 1024 -h 127.0.0.1
```
客户端亦可直接运行，程序会自动拉取[Serverlist](https://github.com/ainilili/ratel/blob/master/serverlist.json)中的公网服务器：
```powershell
java -jar landlords-client/target/landlords-client-#{version}.jar
```
**注意**，实际运行中请将``#{version}``改为当前运行版本！
## 玩法介绍
在线试玩：[传送门](http://ratel.isnico.com)

![demo](demo.gif)

### 出牌规则
所有牌型：
```
┌──┐──┐──┐──┐──┐──┐──┐──┐──┐──┐──┐──┐──┐──┐──┐
│3 |4 |5 |6 |7 |8 |9 |10|J |Q |K |A |2 |S |X |
│♦ |♦ |♦ |♦ |♦ |♦ |♦ |♦ |♦ |♦ |♦ |♦ |♦ |  |  |
└──┘──┘──┘──┘──┘──┘──┘──┘──┘──┘──┘──┘──┘──┘──┘
```
示例：
 - 王炸：``sx``
 - 顺子：``34567``
 - 三带一：``3334``
 - 飞机：``333444a2``
 - 单张10：``0``或者``t``
 - 单张A：``a``或者``1``
 - 封顶顺子：``34567890jqka``
 - 不想出牌： ``pass``或``p``
 - 退出： ``exit``或者``e``
 - [更多](https://zh.wikipedia.org/zh-sg/%E9%AC%A5%E5%9C%B0%E4%B8%BB)

#### 协议支持
 - TCP
 - Websocket

Websocket协议的地址为 ``ws://host:port/ratel``，Websocket的端口需要在原端口基础上加1 （如果tcp端口为1024，则ws端口需要为1025）
## 划水俱乐部
QQ群 ``948365095``，划水一时爽，一直划水一直爽！

## 生态
 - [go-ratel-client](https://github.com/ZuoFuhong/go-ratel)
 - [javafx-ratel-client](https://github.com/marmot-z/javafx-ratel-client)
 - [javascript-ratel-client](https://github.com/marmot-z/js-ratel-client)
 
## 教学
 - [Ratel浅析] (https://github.com/HelloGitHub-Team/Article/blob/master/contents/Java/landlords/content.md)
 - [Ratel玩法视频教学] (https://www.bilibili.com/video/av97603585)

## 更新日志
 - [更新日志](https://github.com/ainilili/ratel/blob/master/UPDATE.md)

## 计划
 - 支持高级难度机器人

## More
 - [Serverlist.json](https://github.com/ainilili/ratel/blob/master/serverlist.json) 是当前的服务器列表, 如果你的服务器部署着当前最新版本的服务端并且分享给大家，可以通过PR提交给我们!
 - 如果您想贡献代码，非常欢迎提``PR``，我们将会合并优秀的代码.
 - 如果您发现了``Bug``，非常欢迎提``Issue``给我们.
 - 欢迎扩展其他语言的客户端.
 - 联系我们请发邮件到 ``ainililia@163.com``.
