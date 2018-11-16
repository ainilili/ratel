## What Is This?
This is a game against landlords

The difference is

It is done from the command line
## How To Begin?
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

## Looking For Players

Join the qq group ``948365095``

## How To Play?
You can create a server by yourself, or you can connect to public server with ip ``39.105.65.8`` by your client !

![demo](demo.gif)

#### Choose Poker
```
┌──┐──┐──┐──┐──┐──┐──┐──┐──┐──┐──┐──┐──┐──┐──┐
│3 |4 |5 |6 |7 |8 |9 |10|J |Q |K |A |2 |S |X |
│♦ |♦ |♦ |♦ |♦ |♦ |♦ |♦ |♦ |♦ |♦ |♦ |♦ |  |  |
└──┘──┘──┘──┘──┘──┘──┘──┘──┘──┘──┘──┘──┘──┘──┘
```
Above is the result of sorting the 3 to big ACES in small to big order

If you want to choose **Link**
```
┌──┐──┐──┐──┐──┐
│7 |8 |9 |10|J |
│♦ |♦ |♦ |♦ |♦ |
└──┘──┘──┘──┘──┘
```
You can enter ``7890j`` or ``789tj``

If you want to choose **The Joker Bomb**
```
┌──┐──┐
│S |X |
│  |  |
└──┘──┘
```
You can enter ``sx``

This is a table for poker alias
```
poker-> │3 |4 |5 |6 |7 |8 |9 |10   |J  |Q  |K  |A    |2 |S  |X  |
alias-> │3 |4 |5 |6 |7 |8 |9 |T t 0|J j|Q q|K k|A a 1|2 |S s|X x|
```
- If you don't want to play in a turn, you can enter ``pass``
- If you want to quit during a round, you can enter ``exit``

#### Play the rules
Here's the retel backed poker combination
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
## Update
 - [Update Log](https://github.com/ainilili/ratel/blob/master/UPDATE.md)
 
## End
 - [Serverlist.json](https://github.com/ainilili/ratel/blob/master/serverlist.json) is the current server list, If your server is currently deployed with the latest version of the server and Shared with everyone, you can submit it to us through PR!
 - If you have good code, you are welcome to submit it to us and we will incorporate good code
 - If you find some bugs in use, please mention issue to us
 - Ratel designs cross-platform and welcomes client rewrite in other languages
 - Contact us by email ``ainililia@163.com``