# Ratel

[![GitHub forks](https://img.shields.io/github/forks/ainilili/ratel?style=flat-square)](https://github.com/ainilili/ratel/network)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/ainilili/ratel?style=flat-square)
![GitHub all releases](https://img.shields.io/github/downloads/ainilili/ratel/total?logo=spring&style=flat-square)
[![GitHub stars](https://img.shields.io/github/stars/ainilili/ratel?logo=java&style=flat-square)](https://github.com/ainilili/ratel/stargazers)
[![GitHub license](https://img.shields.io/github/license/ainilili/ratel?logo=apache&style=flat-square)](https://github.com/ainilili/ratel/blob/master/LICENSE)
![Build ratel(Java with Maven)](https://github.com/ainilili/ratel/workflows/Build%20ratel(Java%20with%20Maven)/badge.svg?branch=master)
![Docker Image Version (latest by date)](https://img.shields.io/docker/v/kebyn/ratel?label=Docker&logo=docker&style=flat-square)

## 项目介绍 Project introduction
斗地主我想大家都会玩吧，但是不知道大家有没有在命令行内玩过斗地主。

这个项目是基于Netty实现的一款命令行斗地主游戏，在下班后或者工作闲暇之余，你都可以肆无忌惮的在命令行中玩斗地主~

## 安装 Install
下载打包(该项目需要一些Java基础，需要确保您的机器拥有JVM环境)
```powershell
git clone https://github.com/ainilili/ratel.git
cd ratel
mvn install package
```
接下来，运行 ``landlords-client`` 和 ``landlords-server`` 的 ``target`` 文件夹下的Jar包：
```
java -jar landlords-server/target/landlords-server-#{version}.jar -p 1024
java -jar landlords-client/target/landlords-client-#{version}.jar -p 1024 -h 127.0.0.1
```
游戏开始...
## 玩法介绍 How to Play
你可以创建一个私服, 或者加入公网服务器 ``121.5.140.133``(``-h``参数指定对应的服务器)

![demo](demo.gif)

#### 出牌
```
┌──┐──┐──┐──┐──┐──┐──┐──┐──┐──┐──┐──┐──┐──┐──┐
│3 |4 |5 |6 |7 |8 |9 |10|J |Q |K |A |2 |S |X |
│♦ |♦ |♦ |♦ |♦ |♦ |♦ |♦ |♦ |♦ |♦ |♦ |♦ |  |  |
└──┘──┘──┘──┘──┘──┘──┘──┘──┘──┘──┘──┘──┘──┘──┘
```
这是按照从小打大的规则将3到大王排序后的结果

如果你想出顺子
```
┌──┐──┐──┐──┐──┐
│7 |8 |9 |10|J |
│♦ |♦ |♦ |♦ |♦ |
└──┘──┘──┘──┘──┘
```
你可以输入 ``7890j`` or ``789tj``

如果你想出王炸
```
┌──┐──┐
│S |X |
│  |  |
└──┘──┘
```
你可以输入 ``sx``

这是所有牌的别名映射规则
```
poker-> │3 |4 |5 |6 |7 |8 |9 |10   |J  |Q  |K  |A    |2 |S  |X  |
alias-> │3 |4 |5 |6 |7 |8 |9 |T t 0|J j|Q q|K k|A a 1|2 |S s|X x|
```
- 如果本回合内你不想出牌，可以输入 ``pass``
- 如果本回合内你想退出游戏，可以输入 ``exit``

#### 游戏规则
正统的欢乐斗地主~

## 加入划水俱乐部 Join our QQ group
QQ群 ``948365095``，寻找一起划水的玩伴！

## 更新 Update
 - [更新日志](https://github.com/ainilili/ratel/blob/master/UPDATE.md)

## 相关 Correlation
 - [go-ratel-client](https://github.com/ZuoFuhong/go-ratel)
 - [javafx-ratel-client](https://github.com/marmot-z/javafx-ratel-client)
 
## Blog 
 - [Ratel浅析] (https://github.com/HelloGitHub-Team/Article/blob/master/contents/Java/landlords/content.md)
 - [Ratel玩法视频教学] (https://www.bilibili.com/video/av97603585)

## 最后 End
 - [Serverlist.json](https://github.com/ainilili/ratel/blob/master/serverlist.json) 是当前的服务器列表, 如果你的服务器部署着当前最新版本的服务端并且分享给大家，可以通过PR提交给我们!
 - 如果您想贡献代码，非常欢迎提``PR``，我们将会合并优秀的代码.
 - 如果您发现了``Bug``，非常欢迎提``Issue``给我们.
 - 欢迎扩展其他语言的客户端.
 - 联系我们请发邮件到 ``ainililia@163.com``.
