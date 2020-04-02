1. 客户端
  + 直接使用已经编译好的客户端
    - `docker` 运行客户端，并加入公网服务器 `39.105.65.8`
      ```
      docker run --rm -it kebyn/ratel:client
      ```
  + 自行编译 `docker` 客户端
    - 下载并编译
    ```
    git clone https://github.com/ainilili/ratel.git
    cd ratel/docker/client
    docker build -f Dockerfile -t ratel:client .
    ```
    - 使用 `docker` 运行客户端
    ```
    docker run --rm -it ratel:client
    ```
    等同于
    ```
    docker run --rm -it ratel:client -- 'java -jar *.jar -h 39.105.65.8'
    ```
2. 服务端
  + 直接使用已经编译好的服务端
    - `docker` 运行客户端，并加入公网服务器 `39.105.65.8`
      ```
      docker run --rm -d -p 1024:1024 kebyn/ratel:server
      ```
  + 自行编译 `docker` 客户端
    - 下载并编译
    ```
    git clone https://github.com/ainilili/ratel.git
    cd ratel/docker/server
    docker build -f Dockerfile -t ratel:server .
    ```
    - 使用 `docker` 运行客户端
    ```
    docker run --rm -d -p 1024:1024 ratel:server
    ```
    等同于
    ```
    docker run --rm -d -p 1024:1024 ratel:server -- 'java -jar *.jar -p 1024'
    ```
    > 确保服务器防火墙允许`1024`端口访问，并且没有其它应用占用`1024`端口
