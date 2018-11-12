## What is this?
This is a game against landlords

The difference is

It is done from the command line
## How to begin?
Maven packaging
```
git clone https://github.com/ainilili/ratel.git
cd ratel
mvn install package
```
Then, Perform ``landlords-client`` and ``landlords-server`` jars folder inside the ``target`` directory
```
java -jar landlords-server/target/landlords-server-#{version}.jar -p 1024
java -jar landlords-client/target/landlords-client-#{version}.jar -p 1024 -h 127.0.0.1
```
Start a fun game !
## How to play?
You can create a server by yourself, or you can connect to public server with ip ``39.105.65.8`` by your client !

## Pull request
Push your code to the master branch, and we'll merge good code

Contact us by email ``ainililia@163.com``