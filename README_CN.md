## 这是什么?
这是一场对抗地主的游戏

不同的是

它可以在命令行中进行
## 如何入门?
Maven 打包
```
git clone https://github.com/ainilili/ratel.git
cd ratel
mvn install package
```
接下来，运行 ``landlords-client`` 和 ``landlords-server`` 的 ``target`` 文件夹下的jar包
```
java -jar landlords-server/target/landlords-server-#{version}.jar -p 1024
java -jar landlords-client/target/landlords-client-#{version}.jar -p 1024 -h 127.0.0.1
```
开始一个有趣的额游戏

## Looking For Players

Join the qq group ``948365095``

## 怎么玩?
你可以创建一个自己的服务器, 或者自己加入公网服务器 ``39.105.65.8`` !

![demo](demo.gif)

#### 选牌
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
这是Ratel支持的组合
```
3
sx
33
333
3334
3333
34567
33344
334455
333345
33344456
33334455
3334445566
333344445678
3333444455667788
```

## 更新
 - [更新日志](https://github.com/ainilili/ratel/blob/master/UPDATE.md)

## 最后
 - [Serverlist.json](https://github.com/ainilili/ratel/blob/master/serverlist.json) 是当前的服务器列表, 如果你的服务器部署着当前最新版本的服务端并且分享给大家，可以通过PR提交给我们!
 - 如果您想贡献代码，非常欢迎提Pull Request，我们将会合并优秀的代码
 - 如果您发现了BUG，非常欢迎提ISSUE给我们
 - 欢迎扩展其他语言的客户端
 - 联系我们请发邮件到 ``ainililia@163.com``