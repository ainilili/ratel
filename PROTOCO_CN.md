[toc]
# Ratel-Client开发文档
## 介绍
### 什么是Ratel
[Ratel](https://github.com/ainilili/ratel) 是一个可以在命令行中玩斗地主的项目，可以使用小巧的jar包在拥有JVM环境的终端中进行游戏，同时支持人人对战和人机对战两种模式，丰富你的空闲时间！

Ratel使用Java语言开发，[Netty 4.x](https://github.com/netty/netty)网络框架搭配[protobuf](https://github.com/protocolbuffers/protobuf)数据协议，可以支持多客户端同时游戏。

### Ratel-Client扩展
Ratel面向响应式架构，通过事件码进行通讯，对于Ratel-Client，支持跨平台扩展，换一种说法，任何后端语言都可以开发Ratel-Client！

在开发Ratel-Client前应该知道：
- Ratel Server-Client交互网络协议为``TCP/IP``。
- Ratel Server-Client交互数据协议为[protobuf](https://github.com/protocolbuffers/protobuf)。
- Ratel以事件为驱动，在Client将各个环节串联起来。
- Ratel所有的文案都由Client显示。

## 对接
### 架构你的客户端
我们可以使用任何后端语言就重写Ratel客户端，Ratel默认的客户端的由Java语言编写，如果你想使用其他语言重写，以下是我们推荐的一种架构：
![](https://user-gold-cdn.xitu.io/2018/11/17/16720fa649dfaea0?w=898&h=500&f=png&s=33655)

这种架构对于事件的处理非常友好，你可以设计一个抽象的``Event-Listener``接口，然后开发不同的实现去处理不同``CODE``对应的响应数据，例如一个而简单的例子——Poker的显示，以下是我们的处理流程的伪代码:
```
1、对服务端响应数据解码 -> 

decode(msg)

2、解码后的数据 ->

ClientData{
    String code;
    String data;
}

3、通过code寻找对应的EventListener ->

showPokersEventListener = Map<code, EventListener>.get(code)

4、处理响应数据 ->

showPokersEventListener.call(server, data){
    show(data);
    server.write(msg);
}
```
以上只是简单的``Server-Client``的交互流程，有时候可能会出现``Client-Client``的场景，例如客户端的选项面板的显示，在我们从A层切换到B层，再从B层返回到A层，这时就需要做``Client-Client``的交互。

当然，大多数的交互重点集中在``Server-Client``，而另一方面，客户端大多都是在处理并显示服务端响应的数据，对于真正的业务交互很少，这对于客户端来说，只要将事件绝对的丰富，那么客户端的流程就会绝对的灵活。因此，Ratel的客户端响应事件相比于服务端的响应事件数量多了几倍有余，所以这对于客户端的架构要求就是要有足够的灵活性，能够支持以下两个业务流：
- Server-Client-Server
- Server-Client-Client-Server

之后，我们进入下一步！
### 定义数据实体
对于客户端和服务端交互的数据，为了承载，我们需要设计两个类去存放编解码后的数据，值得一提的是，客户端和服务端的数据结构一样，都由``CODE``、``DATA``和``INFO``三个字段组成:
- CODE - 对应的事件
- DATA - 传递的数据
- INFO - 信息(暂时用不到)

我们的编解码方式为``Protobuf``序列化，参考文件请看[这里](https://github.com/ainilili/ratel/tree/master/protoc-resource)。

### 对接协议
在我们做好对接的准备工作之后，可以通过以下协议文档开始实现客户端的业务!

#### 客户端事件
##### 连接成功事件
 - ``CODE`` - CODE_CLIENT_CONNECT
 - ``TYPE`` - TEXT
 - ``DATA`` - 客户端被分配的ID

##### 退出房间事件
 - ``CODE`` - CODE_CLIENT_EXIT
 - ``TYPE`` - JSON
 - ``DATA`` - 如下

字段名 | 含义
---|---
roomId | 房间ID
exitClientId | 退出者ID
exitClientNickname | 退出者昵称

参考数据
```
{"roomId":14,"exitClientId":64330,"exitClientNickname":"nico"}
```

##### 客户端踢出事件
 - ``CODE`` - CODE_CLIENT_KICK
 - ``TYPE`` - Text
 - ``DATA`` - NULL

##### 设置昵称事件
 - ``CODE`` - CODE_CLIENT_NICKNAME_SET
 - ``TYPE`` - JSON
 - ``DATA`` - 如下

字段名 | 含义
---|---
invalidLength | 有效长度，当设置昵称超过10个字节会返回此字段

参考数据：
```
{"invalidLength":10}
```

##### 抢地主-地主诞生事件
 - ``CODE`` - CODE_GAME_LANDLORD_CONFIRM
 - ``TYPE`` - JSON
 - ``DATA`` - 如下

字段名 | 含义
---|---
roomId | 房间ID
roomOwner | 房间所有者昵称
roomClientCount | 房间人数
landlordNickname | 地主昵称
landlordId | 地主ID
additionalPokers | 额外的三张牌

参考数据：
```
{"roomId":14,"roomOwner":"nico","roomClientCount":3,"landlordNickname":"robot_2","landlordId":-8,"additionalPokers":[{"level":"LEVEL_5","type":"DIAMOND"},{"level":"LEVEL_6","type":"CLUB"},{"level":"LEVEL_A","type":"DIAMOND"}]}
```

##### 抢地主-大家都没抢事件
 - ``CODE`` - CODE_GAME_LANDLORD_CYCLE
 - ``TYPE`` - TEXT
 - ``DATA`` - NULL

TIP：该事件触发后会连续触发重新游戏事件

##### 抢地主-抢地主决策事件
 - ``CODE`` - CODE_GAME_LANDLORD_ELECT
 - ``TYPE`` - JSON
 - ``DATA`` - 如下

字段名 | 含义
---|---
roomId | 房间ID
roomOwner | 房间所有者昵称
roomClientCount | 房间人数
preClientNickname | 上一个客户端的昵称
nextClientNickname | 下一个客户端的昵称
nextClientId | 下一个客户端的ID

参考数据：
```
{"roomId":14,"roomOwner":"nico","roomClientCount":3,"preClientNickname":"nico1","nextClientNickname":"nico2","nextClientId":2}
```

##### 游戏结束事件
 - ``CODE`` - CODE_GAME_OVER
 - ``TYPE`` - JSON
 - ``DATA`` - 如下

字段名 | 含义
---|---
winnerNickname | 获胜者昵称
winnerType | 获胜者类型（地主？农民）

参考数据：
```
{"winnerNickname":"nico","winnerType":"LANDLORD?PEASANT"}
```












