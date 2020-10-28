# week-2

# 第三课
- 使用 GCLogAnalysis.java 自己演练一遍串行/并行/CMS/G1的案例。
   - 串行GC
```java
➜  [/Users/caiguangming/my-work/my-dome] java -XX:+UseSerialGC -Xmx512m -Xms512m -XX:+PrintGCDetails GCLogAnalysis
正在执行...
[GC (Allocation Failure) [DefNew: 139776K->17472K(157248K), 0.1000500 secs] 139776K->44734K(506816K), 0.1004154 secs] [Times: user=0.03 sys=0.02, real=0.10 secs]
[GC (Allocation Failure) [DefNew: 157039K->17471K(157248K), 0.1114547 secs] 184301K->86999K(506816K), 0.1115198 secs] [Times: user=0.03 sys=0.03, real=0.11 secs]
[GC (Allocation Failure) [DefNew: 157247K->17469K(157248K), 0.0682341 secs] 226775K->133093K(506816K), 0.0684038 secs] [Times: user=0.03 sys=0.03, real=0.07 secs]
[GC (Allocation Failure) [DefNew: 157245K->17470K(157248K), 0.0697470 secs] 272869K->181052K(506816K), 0.0698144 secs] [Times: user=0.03 sys=0.03, real=0.07 secs]
[GC (Allocation Failure) [DefNew: 157246K->17470K(157248K), 0.0675195 secs] 320828K->225141K(506816K), 0.0675853 secs] [Times: user=0.03 sys=0.02, real=0.07 secs]
[GC (Allocation Failure) [DefNew: 157110K->17472K(157248K), 0.0754083 secs] 364781K->273111K(506816K), 0.0754813 secs] [Times: user=0.02 sys=0.02, real=0.08 secs]
[GC (Allocation Failure) [DefNew: 157248K->17471K(157248K), 0.0685106 secs] 412887K->319813K(506816K), 0.0685642 secs] [Times: user=0.03 sys=0.03, real=0.07 secs]
执行结束!共生成对象次数:3789
Heap
 def new generation   total 157248K, used 23484K [0x00000007a0000000, 0x00000007aaaa0000, 0x00000007aaaa0000)
  eden space 139776K,   4% used [0x00000007a0000000, 0x00000007a05df638, 0x00000007a8880000)
  from space 17472K,  99% used [0x00000007a9990000, 0x00000007aaa9fd40, 0x00000007aaaa0000)
  to   space 17472K,   0% used [0x00000007a8880000, 0x00000007a8880000, 0x00000007a9990000)
 tenured generation   total 349568K, used 302341K [0x00000007aaaa0000, 0x00000007c0000000, 0x00000007c0000000)
   the space 349568K,  86% used [0x00000007aaaa0000, 0x00000007bd1e16f8, 0x00000007bd1e1800, 0x00000007c0000000)
 Metaspace       used 2712K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 294K, capacity 386K, committed 512K, reserved 1048576K
```
- 并行GC
```java
[/Users/caiguangming/my-work/my-dome] java -XX:+UseParallelGC -Xmx512m -Xms512m -XX:+PrintGCDetails GCLogAnalysis
正在执行...
[GC (Allocation Failure) [PSYoungGen: 131584K->21503K(153088K)] 131584K->47344K(502784K), 0.0328446 secs] [Times: user=0.03 sys=0.06, real=0.03 secs]
[GC (Allocation Failure) [PSYoungGen: 153087K->21500K(153088K)] 178928K->93644K(502784K), 0.0453416 secs] [Times: user=0.04 sys=0.09, real=0.05 secs]
[GC (Allocation Failure) [PSYoungGen: 153084K->21501K(153088K)] 225228K->136162K(502784K), 0.0378360 secs] [Times: user=0.03 sys=0.05, real=0.03 secs]
[GC (Allocation Failure) [PSYoungGen: 153085K->21497K(153088K)] 267746K->174112K(502784K), 0.0313887 secs] [Times: user=0.04 sys=0.05, real=0.03 secs]
[GC (Allocation Failure) [PSYoungGen: 153081K->21495K(153088K)] 305696K->219708K(502784K), 0.0385827 secs] [Times: user=0.04 sys=0.05, real=0.03 secs]
[GC (Allocation Failure) [PSYoungGen: 153079K->21487K(80384K)] 351292K->260339K(430080K), 0.0323173 secs] [Times: user=0.04 sys=0.06, real=0.03 secs]
[GC (Allocation Failure) [PSYoungGen: 80367K->34915K(116736K)] 319219K->279143K(466432K), 0.0157139 secs] [Times: user=0.03 sys=0.00, real=0.01 secs]
[GC (Allocation Failure) [PSYoungGen: 93509K->45258K(116736K)] 337737K->295664K(466432K), 0.0207930 secs] [Times: user=0.03 sys=0.01, real=0.02 secs]
[GC (Allocation Failure) [PSYoungGen: 103965K->55722K(116736K)] 354371K->315010K(466432K), 0.0192759 secs] [Times: user=0.03 sys=0.01, real=0.02 secs]
[GC (Allocation Failure) [PSYoungGen: 114457K->38783K(116736K)] 373745K->330786K(466432K), 0.0321332 secs] [Times: user=0.04 sys=0.04, real=0.03 secs]
[Full GC (Ergonomics) [PSYoungGen: 38783K->0K(116736K)] [ParOldGen: 292002K->237618K(349696K)] 330786K->237618K(466432K), [Metaspace: 2706K->2706K(1056768K)], 0.0587419 secs] [Times: user=0.12 sys=0.01, real=0.06 secs]
[GC (Allocation Failure) [PSYoungGen: 58880K->21417K(116736K)] 296498K->259035K(466432K), 0.0050316 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]
[GC (Allocation Failure) [PSYoungGen: 80011K->20907K(116736K)] 317629K->278355K(466432K), 0.0092429 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]
[GC (Allocation Failure) [PSYoungGen: 79787K->20025K(116736K)] 337235K->297141K(466432K), 0.0089684 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]
[GC (Allocation Failure) [PSYoungGen: 78843K->21889K(116736K)] 355959K->318609K(466432K), 0.0156235 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]
[GC (Allocation Failure) [PSYoungGen: 80769K->23212K(116736K)] 377489K->341209K(466432K), 0.0257085 secs] [Times: user=0.03 sys=0.03, real=0.02 secs]
[Full GC (Ergonomics) [PSYoungGen: 23212K->0K(116736K)] [ParOldGen: 317997K->278376K(349696K)] 341209K->278376K(466432K), [Metaspace: 2706K->2706K(1056768K)], 0.0646989 secs] [Times: user=0.12 sys=0.01, real=0.07 secs]
[GC (Allocation Failure) [PSYoungGen: 58880K->19785K(116736K)] 337256K->298162K(466432K), 0.0051187 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
执行结束!共生成对象次数:5292
Heap
 PSYoungGen      total 116736K, used 45446K [0x00000007b5580000, 0x00000007c0000000, 0x00000007c0000000)
  eden space 58880K, 43% used [0x00000007b5580000,0x00000007b6e8f3d8,0x00000007b8f00000)
  from space 57856K, 34% used [0x00000007bc780000,0x00000007bdad2570,0x00000007c0000000)
  to   space 57856K, 0% used [0x00000007b8f00000,0x00000007b8f00000,0x00000007bc780000)
 ParOldGen       total 349696K, used 278376K [0x00000007a0000000, 0x00000007b5580000, 0x00000007b5580000)
  object space 349696K, 79% used [0x00000007a0000000,0x00000007b0fda3a8,0x00000007b5580000)
 Metaspace       used 2712K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 294K, capacity 386K, committed 512K, reserved 1048576K
```

- CMS
```java
[/Users/caiguangming/my-work/my-dome] java -XX:+UseConcMarkSweepGC -Xmx2g -Xms2g -XX:+PrintGCDetails GCLogAnalysis
正在执行...
[GC (Allocation Failure) [ParNew: 272640K->34047K(306688K), 0.0692821 secs] 272640K->85913K(2063104K), 0.0693647 secs] [Times: user=0.07 sys=0.10, real=0.07 secs]
[GC (Allocation Failure) [ParNew: 306687K->34048K(306688K), 0.1326270 secs] 358553K->162405K(2063104K), 0.1326880 secs] [Times: user=0.10 sys=0.09, real=0.14 secs]
[GC (Allocation Failure) [ParNew: 306688K->34048K(306688K), 0.1242867 secs] 435045K->243090K(2063104K), 0.1243529 secs] [Times: user=0.20 sys=0.06, real=0.12 secs]
[GC (Allocation Failure) [ParNew: 306688K->34048K(306688K), 0.1162809 secs] 515730K->324954K(2063104K), 0.1163439 secs] [Times: user=0.19 sys=0.07, real=0.12 secs]
执行结束!共生成对象次数:4182
Heap
 par new generation   total 306688K, used 92597K [0x0000000740000000, 0x0000000754cc0000, 0x0000000754cc0000)
  eden space 272640K,  21% used [0x0000000740000000, 0x000000074392d7d0, 0x0000000750a40000)
  from space 34048K, 100% used [0x0000000750a40000, 0x0000000752b80000, 0x0000000752b80000)
  to   space 34048K,   0% used [0x0000000752b80000, 0x0000000752b80000, 0x0000000754cc0000)
 concurrent mark-sweep generation total 1756416K, used 290906K [0x0000000754cc0000, 0x00000007c0000000, 0x00000007c0000000)
 Metaspace       used 2712K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 294K, capacity 386K, committed 512K, reserved 1048576K
```

   - G1 GC
```java
➜  [/Users/caiguangming/my-work/my-dome] java -XX:+UseG1GC -Xmx2g -Xms2g -XX:+PrintGC GCLogAnalysis
正在执行...
[GC pause (G1 Evacuation Pause) (young) 130M->46M(2048M), 0.0264860 secs]
[GC pause (G1 Evacuation Pause) (young) 167M->79M(2048M), 0.0210932 secs]
[GC pause (G1 Evacuation Pause) (young) 185M->114M(2048M), 0.0188396 secs]
[GC pause (G1 Evacuation Pause) (young) 223M->149M(2048M), 0.0215511 secs]
[GC pause (G1 Evacuation Pause) (young) 267M->185M(2048M), 0.0178127 secs]
[GC pause (G1 Evacuation Pause) (young) 302M->216M(2048M), 0.0143631 secs]
[GC pause (G1 Evacuation Pause) (young) 329M->245M(2048M), 0.0157924 secs]
[GC pause (G1 Evacuation Pause) (young) 400M->291M(2048M), 0.0709040 secs]
[GC pause (G1 Evacuation Pause) (young) 429M->324M(2048M), 0.0270835 secs]
[GC pause (G1 Evacuation Pause) (young) 493M->364M(2048M), 0.0481355 secs]
[GC pause (G1 Evacuation Pause) (young) 535M->417M(2048M), 0.0739904 secs]
执行结束!共生成对象次数:5224
➜  [/Users/caiguangming/my-work/my-dome] java -XX:+UseG1GC -Xmx1g -Xms1g -XX:+PrintGC GCLogAnalysis
正在执行...
[GC pause (G1 Evacuation Pause) (young) 59M->16M(1024M), 0.0127156 secs]
[GC pause (G1 Evacuation Pause) (young) 74M->39M(1024M), 0.0121011 secs]
[GC pause (G1 Evacuation Pause) (young) 91M->54M(1024M), 0.0202576 secs]
[GC pause (G1 Evacuation Pause) (young) 109M->70M(1024M), 0.0111425 secs]
[GC pause (G1 Evacuation Pause) (young) 141M->93M(1024M), 0.0138599 secs]
[GC pause (G1 Evacuation Pause) (young) 274M->148M(1024M), 0.0314973 secs]
[GC pause (G1 Evacuation Pause) (young) 234M->176M(1024M), 0.0202315 secs]
[GC pause (G1 Evacuation Pause) (young) 317M->226M(1024M), 0.0264474 secs]
[GC pause (G1 Evacuation Pause) (young) 373M->262M(1024M), 0.0281615 secs]
[GC pause (G1 Evacuation Pause) (young) 459M->309M(1024M), 0.0371239 secs]
执行结束!共生成对象次数:5041
➜  [/Users/caiguangming/my-work/my-dome] java -XX:+UseG1GC -Xmx4g -Xms4g -XX:+PrintGC GCLogAnalysis
正在执行...
[GC pause (G1 Evacuation Pause) (young) 204M->67M(4096M), 0.0547709 secs]
[GC pause (G1 Evacuation Pause) (young) 245M->114M(4096M), 0.0358818 secs]
[GC pause (G1 Evacuation Pause) (young) 292M->165M(4096M), 0.0439562 secs]
[GC pause (G1 Evacuation Pause) (young) 343M->222M(4096M), 0.0414579 secs]
[GC pause (G1 Evacuation Pause) (young) 400M->278M(4096M), 0.0400997 secs]
[GC pause (G1 Evacuation Pause) (young) 456M->326M(4096M), 0.0396844 secs]
[GC pause (G1 Evacuation Pause) (young) 504M->382M(4096M), 0.0400979 secs]
[GC pause (G1 Evacuation Pause) (young) 560M->437M(4096M), 0.0458699 secs]
[GC pause (G1 Evacuation Pause) (young) 615M->496M(4096M), 0.1102783 secs]
执行结束!共生成对象次数:5964
➜  [/Users/caiguangming/my-work/my-dome] java -XX:+UseG1GC -Xmx6g -Xms6g -XX:+PrintGC GCLogAnalysis
正在执行...
[GC pause (G1 Evacuation Pause) (young) 306M->102M(6144M), 0.0641026 secs]
[GC pause (G1 Evacuation Pause) (young) 368M->181M(6144M), 0.0788356 secs]
[GC pause (G1 Evacuation Pause) (young) 447M->265M(6144M), 0.0590324 secs]
[GC pause (G1 Evacuation Pause) (young) 531M->348M(6144M), 0.0588834 secs]
[GC pause (G1 Evacuation Pause) (young) 614M->436M(6144M), 0.1823734 secs]
执行结束!共生成对象次数:5021
```
- 使用压测工具(wrk或sb)，演练gateway-server-0.0.1-SNAPSHOT.jar 示例。
   - 串行GC
```java
java -jar -XX:+UseSerialGC -Xms512m -Xmx512m gateway-server-0.0.1-SNAPSHOT.jar

➜  [/Users/caiguangming] wrk -t4 -c40 -d30s http://127.0.0.1:8088/api/hello
Running 30s test @ http://127.0.0.1:8088/api/hello
  4 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    24.03ms   49.31ms 673.14ms   91.71%
    Req/Sec     0.94k   538.42     3.28k    66.41%
  110529 requests in 30.09s, 13.20MB read
Requests/sec:   3673.24
Transfer/sec:    449.06KB
```

   - 并行GC
```java
java -jar -XX:+UseParallelGC -Xms512m -Xmx512m gateway-server-0.0.1-SNAPSHOT.jar

➜  [/Users/caiguangming] wrk -t4 -c40 -d30s http://127.0.0.1:8088/api/hello
Running 30s test @ http://127.0.0.1:8088/api/hello
  4 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    16.83ms   38.50ms 641.91ms   92.38%
    Req/Sec     1.36k   698.19     4.00k    63.44%
  161022 requests in 30.08s, 19.22MB read
Requests/sec:   5352.25
Transfer/sec:    654.33KB
```

   - CMS
```java
java -jar -XX:+UseConcMarkSweepGC -Xms512m -Xmx512m gateway-server-0.0.1-SNAPSHOT.jar

➜  [/Users/caiguangming] wrk -t4 -c40 -d30s http://127.0.0.1:8088/api/hello
Running 30s test @ http://127.0.0.1:8088/api/hello
  4 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    20.79ms   41.34ms 520.70ms   91.26%
    Req/Sec     1.06k   505.33     3.09k    62.38%
  125877 requests in 30.09s, 15.03MB read
Requests/sec:   4183.61
Transfer/sec:    511.46KB
--------------------------------------
java -jar -XX:+UseConcMarkSweepGC -Xms1g -Xmx1g gateway-server-0.0.1-SNAPSHOT.jar

➜  [/Users/caiguangming] wrk -t4 -c40 -d30s http://127.0.0.1:8089/api/hello
Running 30s test @ http://127.0.0.1:8089/api/hello
  4 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    22.54ms   49.97ms 600.90ms   92.20%
    Req/Sec     1.11k   568.50     2.86k    62.85%
  130926 requests in 30.09s, 15.63MB read
Requests/sec:   4350.44
Transfer/sec:    531.86KB
```
- G1
```java
java -jar -XX:+UseG1GC -Xms512m -Xmx512m gateway-server-0.0.1-SNAPSHOT.jar

➜  [/Users/caiguangming] wrk -t4 -c40 -d30s http://127.0.0.1:8089/api/hello
Running 30s test @ http://127.0.0.1:8089/api/hello
  4 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     4.30ms    4.53ms 136.88ms   93.15%
    Req/Sec     2.26k   774.02     6.05k    65.71%
  269412 requests in 30.10s, 32.16MB read
Requests/sec:   8950.50
Transfer/sec:      1.07MB
----------------
java -jar -XX:+UseG1GC -Xms1g -Xmx1g gateway-server-0.0.1-SNAPSHOT.jar

➜  [/Users/caiguangming] wrk -t4 -c40 -d30s http://127.0.0.1:8089/api/hello
Running 30s test @ http://127.0.0.1:8089/api/hello
  4 threads and 40 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     4.00ms    4.17ms 124.60ms   92.99%
    Req/Sec     2.42k   769.52     5.62k    66.67%
  288747 requests in 30.10s, 34.47MB read
Requests/sec:   9593.92
```


# 第四课

- 写一段代码，使用HttpClient或OkHttp访问 [http://localhost:8801，代码提交到](http://localhost:8801%EF%BC%8C%E4%BB%A3%E7%A0%81%E6%8F%90%E4%BA%A4%E5%88%B0) github。
```java
package com.hsf.example.hsfexampleclient.geettime.week_2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.alibaba.dts.shade.org.apache.commons.params.HttpMethodParams;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.commons.httpclient.HttpStatus;

/**
 * @Author:wb-cgm503374
 * @Description:
 * @Date:Created in 2020/10/24 18:23
 */

public class HttpClintDemo {
    private static final String URL = "http://localhost:8088/test";
    private static final String URL2 = "http://localhost:8088/test2";

    public static void main(String[] args) throws IOException {
        System.out.println(httpClientGet(URL));
        System.out.println(defaultHttpClient(URL2));

    }

    private static Object httpClientGet(String url) throws IOException {
        org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
        GetMethod getMethod = new GetMethod(url);
        client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        int status = client.executeMethod(getMethod);
        if (HttpStatus.SC_OK == status) {
            return getMethod.getResponseBodyAsString();
        } else {
            return "errorCode:" + status;
        }
    }

    private static Object defaultHttpClient(String url) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);
        int code = response.getStatusLine().getStatusCode();
        if (HttpStatus.SC_OK == code) {
            return EntityUtils.toString(response.getEntity());
        } else {
            return "errorCode:" + code;
        }
    }

}

```
- NIO实现聊天室
```java
package com.hsf.example.hsfexampleclient.geettime.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author:wb-cgm503374
 * @Description: NIO聊天室
 * { @link https://www.cnblogs.com/tong-yuan/p/11886807.html}
 * @Date:Created in 2020/10/25 19:05
 */

public class WeChatDemo {

    public static void main(String[] args) {
        try (Selector selector = Selector.open(); ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();) {

            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 8880));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                System.out.println("block..");
                int select = selector.select();
                System.out.println("recv.." + select);
                Set<SelectionKey> keys = selector.keys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        // 接受就绪
                        System.out.println("接受就绪");
                        ServerSocketChannel socketChannel = (ServerSocketChannel)key.channel();
                        SocketChannel channel = socketChannel.accept();
                        if (null == channel) {
                            continue;
                        }
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_READ);
                        ChatHolder.join(channel);
                    } else if (key.isReadable()) {
                        // 读就绪
                        System.out.println("读就绪");
                        SocketChannel socketChannel = (SocketChannel)key.channel();
                        ByteBuffer buf = ByteBuffer.allocate(1024);
                        if ((socketChannel.read(buf)) != -1) {
                            buf.flip();
                            byte[] bytes = new byte[buf.remaining()];
                            // 将数据读入到byte数组中
                            buf.get(bytes);

                            String content = new String(bytes, StandardCharsets.UTF_8).replace("\r\n", "");
                            System.out.println("receive msg: " + content);
                            ChatHolder.propagate(socketChannel, content);
                            if (content.equalsIgnoreCase("quit")) {
                                ChatHolder.quit(socketChannel);
                                key.cancel();
                                socketChannel.close();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static class ChatHolder {
        static final ConcurrentHashMap<SocketChannel, String> map = new ConcurrentHashMap<>();

        /**
         * 加入-接受就绪
         * @param channel
         */
        static void join(SocketChannel channel) {
            String userId = "用户" + ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
            send(channel, "你的用户ID：" + userId);
            for (SocketChannel socketChannel : map.keySet()) {
                send(socketChannel, userId + "加入聊天室");
            }
            map.put(channel, userId);
        }

        /**
         * 退出
         * @param channel
         */
        static void quit(SocketChannel channel) {
            String userId = map.get(channel);
            send(channel, "你退出聊天室");
            map.remove(channel);
            for (SocketChannel socketChannel : map.keySet()) {
                send(socketChannel, userId + "退出聊天室");
            }
        }

        /**
         * 群发
         * @param channel
         * @param content
         */
        static void propagate(SocketChannel channel, String content) {
            String userId = map.get(channel);
            for (SocketChannel socketChannel : map.keySet()) {
                send(socketChannel, userId + ":" + content);
            }
        }

        private static void send(SocketChannel channel, String content) {
            try {
                content += "\n\r";
                ByteBuffer buf = ByteBuffer.allocate(1024);
                buf.clear();
                buf.put(content.getBytes());
                buf.flip();
                while (buf.hasRemaining()) {
                    channel.write(buf);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


```
