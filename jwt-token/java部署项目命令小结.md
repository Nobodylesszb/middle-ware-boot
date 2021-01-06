前言: 暂无

### 零：java -h

   和很多linux命令一样，我们第一步先通过“java -h”命令查看java命令的使用语法，其输出如下

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```java
[root@wxapp203 basesoft]# java -h
Usage: java [-options] class [args...]
           (to execute a class)
   or  java [-options] -jar jarfile [args...]
           (to execute a jar file)
where options include:
    -d32          use a 32-bit data model if available
    -d64          use a 64-bit data model if available
    -server       to select the "server" VM
                  The default VM is server,
                  because you are running on a server-class machine.


    -cp <class search path of directories and zip/jar files>
    -classpath <class search path of directories and zip/jar files>
                  A : separated list of directories, JAR archives,
                  and ZIP archives to search for class files.
    -D<name>=<value>
                  set a system property
    -verbose:[class|gc|jni]
                  enable verbose output
    -version      print product version and exit
```

有图我们可知java有两种格式(对应两种功能)的使用方法，

功能一：执行一个class文件

功能二：执行一个jar文件

### 一：java [-options] -jar jarfile [args...]

  可选参数[-options] : 没使用过，但其介绍可猜测 

  必选参数 -jar     ：指定该命令是运行的是个jar或war文件

  必选参数 **jarfile  ：指定运行的文件

  可选参数[args.....] : 用过一个就是‘&’指定该jar文件后台挂起，下面介绍。

备注：可执行spring boot的war包项目

### 二：不指定任何参数运行jar文件

　　**java -jar \**\*jarFile**

　　该使用方式，当退出控制台jar项目也会退出(大多数情况都不实用)

### 三：通过在***jarFile文件后指定参数“&”

   **java -jar \**\*jarFile &**

   该方式启动jar项目，当退出通知台该jar项目也会一直运行,可解决二的问题(之前我也是这样启动的),但后来发现，即使我退出了控制台，项目里面打印的日志文件会输出到当前控制台（即两个控制台不是一个）。后经分析可知，该命令没有指定该进程的输出流到哪里，默认输出流(和项目里的日志输出不一样，即使输出的东西是一样的)是当前控制台。所以要解决该问题就要解决指定该输出流到哪里？故可参考如下命令

### 四：指定该项目（进程）的输出流

   **java -jar \**\*jarFile > ./test.log &**

   可选参数：> ./test.log (注意中间空格) 指定进程(项目)的输出了到当前目录下的test.log文件

​           备注>该参数只指定了标准输出流，并没有指定标准错误输出

   可选参数：’&’ 后台挂起

### 五 ：标准使用方式

   为保险起见我们也可以把标准错误输出流写到文件中去(也就是说如果上面进程出现错误了，还是会把错误信息输出到当前控制台的)，

​    **java -jar \**\*jarFile > ./test.log 2>&1 &**

   备注：2>&1 >2代表标准错误输出流 1代表标准输出流  &是合并的意思。0代表输入流

   备注：因为我们已经在项目中有输出日志(也就是说上面的命令会输出两个一样的日志内容 一个是项目中打印的日志(由你的项目决定)，一个就是该命令产生的日志test.log(有服务器系统决定))，为节省资源我们可以忽略服务器系统打印的日志。故我们可以使用如下命令

　　**java -jar \**\*jarFile > /dev/null  2>&1 &** 

​    备注：该命令把输出所有输出流输出到文件/dev/null下，该文件是系统设计的，即输入到该文件的东西立即被抛弃不会产生多余资源（即可变相实现不输出的效果）

### 六：附加  nohup (短语:后台运行)

   查看该语法使用  nohup --help 

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
[root@wxapp203 basesoft]# nohup  --help
Usage: nohup COMMAND [ARG]...
  or:  nohup OPTION
Run COMMAND, ignoring hangup signals.

      --help     display this help and exit
      --version  output version information and exit

If standard input is a terminal, redirect it from /dev/null.  //标准输入流  重定向到 /dev/null
If standard output is a terminal, append output to `nohup.out' if possible, //即没有指定输出流是，默认追加到当前目录下的nohup.out(无需自己创建)文件中。
`$HOME/nohup.out' otherwise.
If standard error is a terminal, redirect it to standard output.  //标准错误输出流，重定向到输出流中，即2>&1
To save output to FILE, use `nohup COMMAND > FILE'.      //也可以指定输出流到文件

NOTE: your shell may have its own version of nohup, which usually supersedes
the version described here.  Please refer to your shell's documentation
for details about the options it supports.
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

  故：我们可以使用  **nohup java -jar \**\*jarFile > /dev/null  &**  来替代标准五的使用

 例如  

```
$ nohup java -jar fwh_biz-0.0.1-SNAPSHOT.war > /dev/null &    //使用命令
[2] 10112                                                     //返回进程id
nohup: ignoring input and redirecting stderr to stdout        //提示我们 忽略了输入流 并且把标准错误输出流，重定向到输出流中了，而我们把输出流重定向到/dev/null中，故什么都不会输出
[1] Killed nohup java -jar fwh_biz-0.0.1-SNAPSHOT.war
备注：这样看来nohup的功能就是包装(从效果上看它包装了流的走向和一个提示功能)了我们之前的命令.
```

  参考资料：1.https://www.cnblogs.com/zq-inlook/p/3577003.html （博客）  

\2. http://www.runoob.com/linux/linux-shell-io-redirections.html（shell教程）

3.https://docs.oracle.com/javase/1.5.0/docs/tooldocs/solaris/java.html （官方文档）





```java
java
 -Djava.security.egd=file:/dev/./urandom
 -Duser.timezone=Asia/Shanghai
 -XX:+PrintGCDateStamps
 -XX:+PrintGCTimeStamps
 -XX:+PrintGCDetails
 -XX:+HeapDumpOnOutOfMemoryError
-jar
 demo.jar
 --spring.profiles.active=jiuzhou
 --deepexi.iam.sso.login.check-ip=true
 --spring.datasource.druid.url=jdbc:mysql://10.201.0.118:3306/deepexi_staff_iam?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8&allowMultiQueries=true
 --spring.datasource.druid.username=deepexi_client_iam
 --spring.datasource.druid.password=
 --spring.redis.host=10.201.0.118
 --spring.redis.port=6379
 --spring.redis.password=ODc4MGIwZ2
 --server.servlet.context-path=/
 --server.port=8080
--sso.auth.url= http://116.63.177.205:37777
--sso.target.url = www.baidu.com
--sso.acs.url == 客户端认证的地址
```

```sh
cd /data/app/iam-openapi
nohup java \
    -Duser.timezone=Asia/Shanghai \
    -XX:+PrintGCDateStamps \
    -XX:+PrintGCTimeStamps \
    -XX:+PrintGCDetails \
    -XX:+HeapDumpOnOutOfMemoryError \
    -Xloggc:logs/gc_$version.log \
    -jar ./deepexi-staff-iam-openapi-provider-3.1.0.jar \
    --spring.profiles.active=jiuzhou \
    --spring.datasource.url=jdbc:mysql://10.1.30.91:3306/deepexi_staff_iam?useUnicode=true\&characterEncoding=utf-8\&useSSL=false\&serverTimezone=GMT%2B8\&allowMultiQueries=true \
    --spring.datasource.username=root \
    --spring.datasource.password=TXJ2MVNvaGFV \
    --eureka.client.enabled=false \
    --sso.auth.url=http://10.1.30.91:10801 \
    --sso.target.url=http://10.1.30.91:10801/#/login-sso-jz \
    --sso.acs.url=http://10.1.30.91:10801/deepexi-staff-iam-openapi/deepexi-staff-iam-openapi/api/v1.0/sso \
    --sso.syncData.url=http://10.1.30.9:7080/services/IF_Service \
    --spring.redis.host=10.1.30.91 \
    --spring.redis.port=6379 \
    --spring.redis.password=ODc4MGIwZ2 >> iam-openapi.log 2>&1 &

```