## 介绍
本项目是一个使用 `javafx` + `netty` 技术构建的一个桌面应用，
是对ratel应用命令行客户端的扩展，为ratel应用提供可视化界面操作方式。

### 系统架构
* 使用`netty`构建和**ratel**服务端的通讯
* 使用`javafx`构建`GUI`界面

## 快速启动
### 依赖
* `jdk`  
本项目是一个`java`应用，所以运行需要`java`环境，前往[oracle](https://www.oracle.com/java/technologies/java-downloads.html)下载最新版本jdk进行安装

* `maven`  
本项目由maven构建，构建本应用需要先安装maven，前往[apache maven](https://maven.apache.org)下载最新版本maven进行安装

### 安装
```powershell
git clone https://github.com/ainilili/ratel.git
cd ratel
mvn install package
```
#### 启动服务器
```shell
java -jar landlords-server/target/landlords-server-#{version}.jar -p 1024
```

#### 启动`javafx`客户端
```shell
java -jar landlords-client-javafx/target/landlords-client-javafx-#{version}.jar
```

## 使用
1. 选择服务器地址连接
2. 输入昵称
3. 选择模式
4. 选择房间
5. 开始游戏

## TODO List
-[X] PVP模式  
-[] 优化界面  
-[] PVE模式  
-[] 页面切换  
-[] 挂机检测

## 反馈与贡献
 - 如果您想贡献代码，非常欢迎提``PR``，我们将会合并优秀的代码.
 - 如果您发现了``Bug``或想提问，非常欢迎提[issue](https://github.com/marmot-z/ratel/issues)给我们.

## 联系方式
* [zhangxunweia@gmail.com](zhangxunweia@gmail.com)
* [15870664270@163.com](15870664270@163.com)

## 参考
* [NaiveChat](https://github.com/fuzhengwei/NaiveChat)
* [ratel部分协议](https://github.com/ainilili/ratel/blob/master/PROTOCO_CN.md)