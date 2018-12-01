package yuqiao.housesearch.craw;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import yuqiao.housesearch.common.constants.Constants;
import yuqiao.housesearch.common.enums.OriginEnun;
import yuqiao.housesearch.house.entity.House;
import yuqiao.housesearch.util.OKHttpUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author 浦希成
 * 2018-11-30
 */
public class DanKeCrawServiceImpl implements DanKeCrawService {
    private OKHttpUtil okHttpUtil = new OKHttpUtil();

    public List<House> getHouseInfo(String city) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "www.dankegongyu.com");
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        headers.put("Upgrade-Insecure-Requests", "1");
        String url = Constants.DNAKE_URL + "room/" + city;
        String html = okHttpUtil.getHtml(url, headers);
        Document doc = Jsoup.parse(html);
        Elements tags = doc.select(".r_lbx_cena");
        List<House> houses = new ArrayList<>(1000);

        return houses;
    }

    private House getDetailInfo(String detailUrl) {

        String html = okHttpUtil.getHtml(detailUrl);
        Document doc = Jsoup.parse(html);
        String title = doc.select("div.room-name > h1").text();
        String number = doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(1) > div:nth-child(2) > label").text().split("：")[1];
        Integer price = Integer.parseInt(doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div:nth-child(3) > div.room-price.hot > div:nth-child(1) > div").text().replaceAll("[^0-9]", ""));
        Integer area = Integer.parseInt(doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(1) > div:nth-child(1) > label").text().replaceAll("[^0-9]", ""));
        String type = doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(1) > div:nth-child(3) > label").text().split("：")[1];
        Integer floor = Integer.parseInt(doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(2) > div:nth-child(2) > label").text().split("/")[0].replaceAll("[^0-9]", ""));
        Integer totalFloor = Integer.parseInt(doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(2) > div:nth-child(2) > label").text().split("/")[1].replaceAll("[^0-9]", ""));
        String direction = doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(2) > div:nth-child(1) > label").text().split("：")[1];
        String subway = doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(2) > div:nth-child(4) > label").text().split("：")[1];
        StringBuffer tags = new StringBuffer();
        Elements tagElements = doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-title > span");
        for (Element tag : tagElements) {
            tags.append(tag.text() + ",");
        }

        House house = new House();
        house.setTitle(title);
        house.setNumber(number);
        house.setPrice(price);
        house.setArea(area);
        house.setType(type);
        house.setType(type);
        house.setFloor(floor);
        house.setTotalFloor(totalFloor);
        house.setDirection(direction);
        house.setSubway(subway);
        house.setTag(tags.toString());
        house.setOrigin(OriginEnun.DANKE.getCode());

        return house;
    }

    public static void main(String[] args) {
//        DanKeCrawServiceImpl test = new DanKeCrawServiceImpl();
//        List<House> houses=test.getHouseInfo("hz");
//        System.out.println(houses);
        String test="/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/bin/java \"-javaagent:/Users/pxc/Library/Application Support/JetBrains/Toolbox/apps/IDEA-U/ch-0/183.4284.148/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=51317:/Users/pxc/Library/Application Support/JetBrains/Toolbox/apps/IDEA-U/ch-0/183.4284.148/IntelliJ IDEA.app/Contents/bin\" -Dfile.encoding=UTF-8 -classpath /Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/charsets.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/deploy.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/cldrdata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/dnsns.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/jaccess.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/jfxrt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/localedata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/nashorn.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/sunec.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/sunjce_provider.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/sunpkcs11.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/ext/zipfs.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/javaws.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jce.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jfr.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jfxswt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/jsse.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/management-agent.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/plugin.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/resources.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre/lib/rt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/ant-javafx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/dt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/javafx-mx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/jconsole.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/packager.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/sa-jdi.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/lib/tools.jar:/Users/pxc/Documents/java/house-search/target/classes:/Users/pxc/mavenJar/org/jsoup/jsoup/1.9.2/jsoup-1.9.2.jar:/Users/pxc/mavenJar/com/baomidou/mybatis-plus-boot-starter/3.0.1/mybatis-plus-boot-starter-3.0.1.jar:/Users/pxc/mavenJar/com/baomidou/mybatis-plus/3.0.1/mybatis-plus-3.0.1.jar:/Users/pxc/mavenJar/com/baomidou/mybatis-plus-extension/3.0.1/mybatis-plus-extension-3.0.1.jar:/Users/pxc/mavenJar/com/baomidou/mybatis-plus-core/3.0.1/mybatis-plus-core-3.0.1.jar:/Users/pxc/mavenJar/com/baomidou/mybatis-plus-annotation/3.0.1/mybatis-plus-annotation-3.0.1.jar:/Users/pxc/mavenJar/com/baomidou/mybatis-plus-generator/3.0.1/mybatis-plus-generator-3.0.1.jar:/Users/pxc/mavenJar/org/apache/velocity/velocity-engine-core/2.0/velocity-engine-core-2.0.jar:/Users/pxc/mavenJar/org/freemarker/freemarker/2.3.28/freemarker-2.3.28.jar:/Users/pxc/mavenJar/org/springframework/boot/spring-boot-autoconfigure/2.1.0.RELEASE/spring-boot-autoconfigure-2.1.0.RELEASE.jar:/Users/pxc/mavenJar/org/springframework/boot/spring-boot-starter-jdbc/2.1.0.RELEASE/spring-boot-starter-jdbc-2.1.0.RELEASE.jar:/Users/pxc/mavenJar/com/zaxxer/HikariCP/3.2.0/HikariCP-3.2.0.jar:/Users/pxc/mavenJar/org/springframework/spring-jdbc/5.1.2.RELEASE/spring-jdbc-5.1.2.RELEASE.jar:/Users/pxc/mavenJar/org/springframework/boot/spring-boot-configuration-processor/2.1.0.RELEASE/spring-boot-configuration-processor-2.1.0.RELEASE.jar:/Users/pxc/mavenJar/org/apache/poi/poi/3.15/poi-3.15.jar:/Users/pxc/mavenJar/org/apache/commons/commons-collections4/4.1/commons-collections4-4.1.jar:/Users/pxc/mavenJar/org/apache/poi/poi-ooxml/3.15/poi-ooxml-3.15.jar:/Users/pxc/mavenJar/org/apache/poi/poi-ooxml-schemas/3.15/poi-ooxml-schemas-3.15.jar:/Users/pxc/mavenJar/org/apache/xmlbeans/xmlbeans/2.6.0/xmlbeans-2.6.0.jar:/Users/pxc/mavenJar/stax/stax-api/1.0.1/stax-api-1.0.1.jar:/Users/pxc/mavenJar/com/github/virtuald/curvesapi/1.04/curvesapi-1.04.jar:/Users/pxc/mavenJar/com/squareup/okhttp3/okhttp/3.9.1/okhttp-3.9.1.jar:/Users/pxc/mavenJar/com/squareup/okio/okio/1.13.0/okio-1.13.0.jar:/Users/pxc/mavenJar/com/ibeetl/beetl/2.8.5/beetl-2.8.5.jar:/Users/pxc/mavenJar/org/antlr/antlr4-runtime/4.2/antlr4-runtime-4.2.jar:/Users/pxc/mavenJar/org/abego/treelayout/org.abego.treelayout.core/1.0.1/org.abego.treelayout.core-1.0.1.jar:/Users/pxc/mavenJar/org/antlr/antlr4-annotations/4.2/antlr4-annotations-4.2.jar:/Users/pxc/mavenJar/ch/qos/logback/logback-core/1.1.6/logback-core-1.1.6.jar:/Users/pxc/mavenJar/ch/qos/logback/logback-classic/1.1.6/logback-classic-1.1.6.jar:/Users/pxc/mavenJar/org/slf4j/slf4j-api/1.7.25/slf4j-api-1.7.25.jar:/Users/pxc/mavenJar/commons-codec/commons-codec/1.10/commons-codec-1.10.jar:/Users/pxc/mavenJar/org/apache/commons/commons-lang3/3.4/commons-lang3-3.4.jar:/Users/pxc/mavenJar/commons-fileupload/commons-fileupload/1.3.1/commons-fileupload-1.3.1.jar:/Users/pxc/mavenJar/commons-io/commons-io/2.2/commons-io-2.2.jar:/Users/pxc/mavenJar/org/springframework/boot/spring-boot-starter-data-redis/2.1.0.RELEASE/spring-boot-starter-data-redis-2.1.0.RELEASE.jar:/Users/pxc/mavenJar/org/springframework/boot/spring-boot-starter/2.1.0.RELEASE/spring-boot-starter-2.1.0.RELEASE.jar:/Users/pxc/mavenJar/org/springframework/boot/spring-boot-starter-logging/2.1.0.RELEASE/spring-boot-starter-logging-2.1.0.RELEASE.jar:/Users/pxc/mavenJar/org/apache/logging/log4j/log4j-to-slf4j/2.11.1/log4j-to-slf4j-2.11.1.jar:/Users/pxc/mavenJar/org/slf4j/jul-to-slf4j/1.7.25/jul-to-slf4j-1.7.25.jar:/Users/pxc/mavenJar/javax/annotation/javax.annotation-api/1.3.2/javax.annotation-api-1.3.2.jar:/Users/pxc/mavenJar/org/yaml/snakeyaml/1.23/snakeyaml-1.23.jar:/Users/pxc/mavenJar/org/springframework/data/spring-data-redis/2.1.2.RELEASE/spring-data-redis-2.1.2.RELEASE.jar:/Users/pxc/mavenJar/org/springframework/data/spring-data-keyvalue/2.1.2.RELEASE/spring-data-keyvalue-2.1.2.RELEASE.jar:/Users/pxc/mavenJar/org/springframework/data/spring-data-commons/2.1.2.RELEASE/spring-data-commons-2.1.2.RELEASE.jar:/Users/pxc/mavenJar/org/springframework/spring-tx/5.1.2.RELEASE/spring-tx-5.1.2.RELEASE.jar:/Users/pxc/mavenJar/org/springframework/spring-oxm/5.1.2.RELEASE/spring-oxm-5.1.2.RELEASE.jar:/Users/pxc/mavenJar/org/springframework/spring-aop/5.1.2.RELEASE/spring-aop-5.1.2.RELEASE.jar:/Users/pxc/mavenJar/org/springframework/spring-context-support/5.1.2.RELEASE/spring-context-support-5.1.2.RELEASE.jar:/Users/pxc/mavenJar/io/lettuce/lettuce-core/5.1.2.RELEASE/lettuce-core-5.1.2.RELEASE.jar:/Users/pxc/mavenJar/io/projectreactor/reactor-core/3.2.2.RELEASE/reactor-core-3.2.2.RELEASE.jar:/Users/pxc/mavenJar/org/reactivestreams/reactive-streams/1.0.2/reactive-streams-1.0.2.jar:/Users/pxc/mavenJar/io/netty/netty-common/4.1.29.Final/netty-common-4.1.29.Final.jar:/Users/pxc/mavenJar/io/netty/netty-transport/4.1.29.Final/netty-transport-4.1.29.Final.jar:/Users/pxc/mavenJar/io/netty/netty-handler/4.1.29.Final/netty-handler-4.1.29.Final.jar:/Users/pxc/mavenJar/org/springframework/boot/spring-boot-starter-web/2.1.0.RELEASE/spring-boot-starter-web-2.1.0.RELEASE.jar:/Users/pxc/mavenJar/org/springframework/boot/spring-boot-starter-json/2.1.0.RELEASE/spring-boot-starter-json-2.1.0.RELEASE.jar:/Users/pxc/mavenJar/com/fasterxml/jackson/core/jackson-databind/2.9.7/jackson-databind-2.9.7.jar:/Users/pxc/mavenJar/com/fasterxml/jackson/datatype/jackson-datatype-jdk8/2.9.7/jackson-datatype-jdk8-2.9.7.jar:/Users/pxc/mavenJar/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/2.9.7/jackson-datatype-jsr310-2.9.7.jar:/Users/pxc/mavenJar/com/fasterxml/jackson/module/jackson-module-parameter-names/2.9.7/jackson-module-parameter-names-2.9.7.jar:/Users/pxc/mavenJar/org/springframework/boot/spring-boot-starter-tomcat/2.1.0.RELEASE/spring-boot-starter-tomcat-2.1.0.RELEASE.jar:/Users/pxc/mavenJar/org/apache/tomcat/embed/tomcat-embed-core/9.0.12/tomcat-embed-core-9.0.12.jar:/Users/pxc/mavenJar/org/apache/tomcat/embed/tomcat-embed-el/9.0.12/tomcat-embed-el-9.0.12.jar:/Users/pxc/mavenJar/org/apache/tomcat/embed/tomcat-embed-websocket/9.0.12/tomcat-embed-websocket-9.0.12.jar:/Users/pxc/mavenJar/org/hibernate/validator/hibernate-validator/6.0.13.Final/hibernate-validator-6.0.13.Final.jar:/Users/pxc/mavenJar/org/jboss/logging/jboss-logging/3.3.2.Final/jboss-logging-3.3.2.Final.jar:/Users/pxc/mavenJar/org/springframework/spring-web/5.1.2.RELEASE/spring-web-5.1.2.RELEASE.jar:/Users/pxc/mavenJar/org/springframework/spring-beans/5.1.2.RELEASE/spring-beans-5.1.2.RELEASE.jar:/Users/pxc/mavenJar/org/springframework/spring-webmvc/5.1.2.RELEASE/spring-webmvc-5.1.2.RELEASE.jar:/Users/pxc/mavenJar/org/springframework/spring-context/5.1.2.RELEASE/spring-context-5.1.2.RELEASE.jar:/Users/pxc/mavenJar/org/springframework/spring-expression/5.1.2.RELEASE/spring-expression-5.1.2.RELEASE.jar:/Users/pxc/mavenJar/org/springframework/boot/spring-boot-devtools/2.1.0.RELEASE/spring-boot-devtools-2.1.0.RELEASE.jar:/Users/pxc/mavenJar/org/springframework/boot/spring-boot/2.1.0.RELEASE/spring-boot-2.1.0.RELEASE.jar:/Users/pxc/mavenJar/mysql/mysql-connector-java/8.0.13/mysql-connector-java-8.0.13.jar:/Users/pxc/mavenJar/org/elasticsearch/elasticsearch/6.5.1/elasticsearch-6.5.1.jar:/Users/pxc/mavenJar/org/elasticsearch/elasticsearch-core/6.5.1/elasticsearch-core-6.5.1.jar:/Users/pxc/mavenJar/org/elasticsearch/elasticsearch-secure-sm/6.5.1/elasticsearch-secure-sm-6.5.1.jar:/Users/pxc/mavenJar/org/elasticsearch/elasticsearch-x-content/6.5.1/elasticsearch-x-content-6.5.1.jar:/Users/pxc/mavenJar/com/fasterxml/jackson/core/jackson-core/2.9.7/jackson-core-2.9.7.jar:/Users/pxc/mavenJar/com/fasterxml/jackson/dataformat/jackson-dataformat-smile/2.9.7/jackson-dataformat-smile-2.9.7.jar:/Users/pxc/mavenJar/com/fasterxml/jackson/dataformat/jackson-dataformat-yaml/2.9.7/jackson-dataformat-yaml-2.9.7.jar:/Users/pxc/mavenJar/com/fasterxml/jackson/dataformat/jackson-dataformat-cbor/2.9.7/jackson-dataformat-cbor-2.9.7.jar:/Users/pxc/mavenJar/org/apache/lucene/lucene-core/7.5.0/lucene-core-7.5.0.jar:/Users/pxc/mavenJar/org/apache/lucene/lucene-analyzers-common/7.5.0/lucene-analyzers-common-7.5.0.jar:/Users/pxc/mavenJar/org/apache/lucene/lucene-backward-codecs/7.5.0/lucene-backward-codecs-7.5.0.jar:/Users/pxc/mavenJar/org/apache/lucene/lucene-grouping/7.5.0/lucene-grouping-7.5.0.jar:/Users/pxc/mavenJar/org/apache/lucene/lucene-highlighter/7.5.0/lucene-highlighter-7.5.0.jar:/Users/pxc/mavenJar/org/apache/lucene/lucene-join/7.5.0/lucene-join-7.5.0.jar:/Users/pxc/mavenJar/org/apache/lucene/lucene-memory/7.5.0/lucene-memory-7.5.0.jar:/Users/pxc/mavenJar/org/apache/lucene/lucene-misc/7.5.0/lucene-misc-7.5.0.jar:/Users/pxc/mavenJar/org/apache/lucene/lucene-queries/7.5.0/lucene-queries-7.5.0.jar:/Users/pxc/mavenJar/org/apache/lucene/lucene-queryparser/7.5.0/lucene-queryparser-7.5.0.jar:/Users/pxc/mavenJar/org/apache/lucene/lucene-sandbox/7.5.0/lucene-sandbox-7.5.0.jar:/Users/pxc/mavenJar/org/apache/lucene/lucene-spatial/7.5.0/lucene-spatial-7.5.0.jar:/Users/pxc/mavenJar/org/apache/lucene/lucene-spatial-extras/7.5.0/lucene-spatial-extras-7.5.0.jar:/Users/pxc/mavenJar/org/apache/lucene/lucene-spatial3d/7.5.0/lucene-spatial3d-7.5.0.jar:/Users/pxc/mavenJar/org/apache/lucene/lucene-suggest/7.5.0/lucene-suggest-7.5.0.jar:/Users/pxc/mavenJar/org/elasticsearch/elasticsearch-cli/6.5.1/elasticsearch-cli-6.5.1.jar:/Users/pxc/mavenJar/net/sf/jopt-simple/jopt-simple/5.0.2/jopt-simple-5.0.2.jar:/Users/pxc/mavenJar/com/carrotsearch/hppc/0.7.1/hppc-0.7.1.jar:/Users/pxc/mavenJar/joda-time/joda-time/2.10.1/joda-time-2.10.1.jar:/Users/pxc/mavenJar/com/tdunning/t-digest/3.2/t-digest-3.2.jar:/Users/pxc/mavenJar/org/hdrhistogram/HdrHistogram/2.1.9/HdrHistogram-2.1.9.jar:/Users/pxc/mavenJar/org/apache/logging/log4j/log4j-api/2.11.1/log4j-api-2.11.1.jar:/Users/pxc/mavenJar/org/elasticsearch/jna/4.5.1/jna-4.5.1.jar:/Users/pxc/mavenJar/org/elasticsearch/client/transport/6.5.1/transport-6.5.1.jar:/Users/pxc/mavenJar/org/elasticsearch/plugin/transport-netty4-client/6.5.1/transport-netty4-client-6.5.1.jar:/Users/pxc/mavenJar/io/netty/netty-buffer/4.1.29.Final/netty-buffer-4.1.29.Final.jar:/Users/pxc/mavenJar/io/netty/netty-codec/4.1.29.Final/netty-codec-4.1.29.Final.jar:/Users/pxc/mavenJar/io/netty/netty-codec-http/4.1.29.Final/netty-codec-http-4.1.29.Final.jar:/Users/pxc/mavenJar/io/netty/netty-resolver/4.1.29.Final/netty-resolver-4.1.29.Final.jar:/Users/pxc/mavenJar/org/elasticsearch/plugin/reindex-client/6.5.1/reindex-client-6.5.1.jar:/Users/pxc/mavenJar/org/elasticsearch/client/elasticsearch-rest-client/6.5.1/elasticsearch-rest-client-6.5.1.jar:/Users/pxc/mavenJar/org/apache/httpcomponents/httpclient/4.5.6/httpclient-4.5.6.jar:/Users/pxc/mavenJar/org/apache/httpcomponents/httpcore/4.4.10/httpcore-4.4.10.jar:/Users/pxc/mavenJar/org/apache/httpcomponents/httpasyncclient/4.1.4/httpasyncclient-4.1.4.jar:/Users/pxc/mavenJar/org/apache/httpcomponents/httpcore-nio/4.4.10/httpcore-nio-4.4.10.jar:/Users/pxc/mavenJar/org/elasticsearch/plugin/lang-mustache-client/6.5.1/lang-mustache-client-6.5.1.jar:/Users/pxc/mavenJar/com/github/spullara/mustache/java/compiler/0.9.3/compiler-0.9.3.jar:/Users/pxc/mavenJar/org/elasticsearch/plugin/percolator-client/6.5.1/percolator-client-6.5.1.jar:/Users/pxc/mavenJar/org/elasticsearch/plugin/parent-join-client/6.5.1/parent-join-client-6.5.1.jar:/Users/pxc/mavenJar/org/elasticsearch/plugin/rank-eval-client/6.5.1/rank-eval-client-6.5.1.jar:/Users/pxc/mavenJar/org/projectlombok/lombok/1.18.2/lombok-1.18.2.jar:/Users/pxc/mavenJar/net/bytebuddy/byte-buddy/1.9.3/byte-buddy-1.9.3.jar:/Users/pxc/mavenJar/org/springframework/spring-core/5.1.2.RELEASE/spring-core-5.1.2.RELEASE.jar:/Users/pxc/mavenJar/org/springframework/spring-jcl/5.1.2.RELEASE/spring-jcl-5.1.2.RELEASE.jar:/Users/pxc/mavenJar/com/alibaba/fastjson/1.2.33/fastjson-1.2.33.jar:/Users/pxc/mavenJar/cn/afterturn/easypoi-base/3.0.3/easypoi-base-3.0.3.jar:/Users/pxc/mavenJar/com/google/guava/guava/16.0.1/guava-16.0.1.jar:/Users/pxc/mavenJar/javax/validation/validation-api/2.0.1.Final/validation-api-2.0.1.Final.jar:/Users/pxc/mavenJar/cn/afterturn/easypoi-web/3.0.3/easypoi-web-3.0.3.jar:/Users/pxc/mavenJar/io/springfox/springfox-swagger2/2.8.0/springfox-swagger2-2.8.0.jar:/Users/pxc/mavenJar/io/swagger/swagger-annotations/1.5.14/swagger-annotations-1.5.14.jar:/Users/pxc/mavenJar/io/swagger/swagger-models/1.5.14/swagger-models-1.5.14.jar:/Users/pxc/mavenJar/com/fasterxml/jackson/core/jackson-annotations/2.9.0/jackson-annotations-2.9.0.jar:/Users/pxc/mavenJar/io/springfox/springfox-spi/2.8.0/springfox-spi-2.8.0.jar:/Users/pxc/mavenJar/io/springfox/springfox-core/2.8.0/springfox-core-2.8.0.jar:/Users/pxc/mavenJar/io/springfox/springfox-schema/2.8.0/springfox-schema-2.8.0.jar:/Users/pxc/mavenJar/io/springfox/springfox-swagger-common/2.8.0/springfox-swagger-common-2.8.0.jar:/Users/pxc/mavenJar/io/springfox/springfox-spring-web/2.8.0/springfox-spring-web-2.8.0.jar:/Users/pxc/mavenJar/org/reflections/reflections/0.9.11/reflections-0.9.11.jar:/Users/pxc/mavenJar/org/javassist/javassist/3.21.0-GA/javassist-3.21.0-GA.jar:/Users/pxc/mavenJar/com/fasterxml/classmate/1.4.0/classmate-1.4.0.jar:/Users/pxc/mavenJar/org/springframework/plugin/spring-plugin-core/1.2.0.RELEASE/spring-plugin-core-1.2.0.RELEASE.jar:/Users/pxc/mavenJar/org/springframework/plugin/spring-plugin-metadata/1.2.0.RELEASE/spring-plugin-metadata-1.2.0.RELEASE.jar:/Users/pxc/mavenJar/org/mapstruct/mapstruct/1.2.0.Final/mapstruct-1.2.0.Final.jar:/Users/pxc/mavenJar/io/springfox/springfox-swagger-ui/2.8.0/springfox-swagger-ui-2.8.0.jar:/Users/pxc/mavenJar/io/springfox/springfox-bean-validators/2.8.0/springfox-bean-validators-2.8.0.jar:/Users/pxc/mavenJar/cn/afterturn/easypoi-annotation/3.0.3/easypoi-annotation-3.0.3.jar:/Users/pxc/mavenJar/com/github/pagehelper/pagehelper-spring-boot-starter/1.2.9/pagehelper-spring-boot-starter-1.2.9.jar:/Users/pxc/mavenJar/org/mybatis/spring/boot/mybatis-spring-boot-starter/1.3.2/mybatis-spring-boot-starter-1.3.2.jar:/Users/pxc/mavenJar/org/mybatis/spring/boot/mybatis-spring-boot-autoconfigure/1.3.2/mybatis-spring-boot-autoconfigure-1.3.2.jar:/Users/pxc/mavenJar/org/mybatis/mybatis/3.4.6/mybatis-3.4.6.jar:/Users/pxc/mavenJar/org/mybatis/mybatis-spring/1.3.2/mybatis-spring-1.3.2.jar:/Users/pxc/mavenJar/com/github/pagehelper/pagehelper-spring-boot-autoconfigure/1.2.9/pagehelper-spring-boot-autoconfigure-1.2.9.jar:/Users/pxc/mavenJar/com/github/pagehelper/pagehelper/5.1.7/pagehelper-5.1.7.jar:/Users/pxc/mavenJar/com/github/jsqlparser/jsqlparser/1.2/jsqlparser-1.2.jar yuqiao.housesearch.craw.DanKeCrawServiceImpl\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <span class=\"location\">1</span> \n" +
                " <a href=\"https://www.dankegongyu.com/room/136992205.html\" key=\"0\" xiaoqu=\"景瑞申花壹号院\" target=\"_blank\" title=\"申花  景瑞申花壹号院 4室1厅\"> 申花 景瑞申花壹号院 4室1厅 </a> \n" +
                " <div class=\"r_lbx_cena\"> \n" +
                "  <div class=\"sub_img\"></div> 距2号线古翠路站3050米 \n" +
                " </div> \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <div class=\"sub_img\"></div> 距2号线古翠路站3050米 \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <span class=\"location\">2</span> \n" +
                " <a href=\"https://www.dankegongyu.com/room/738804999.html\" key=\"1\" xiaoqu=\"拱苑小区一期\" target=\"_blank\" title=\"申花  拱苑小区一期 4室1厅\"> 申花 拱苑小区一期 4室1厅 </a> \n" +
                " <div class=\"r_lbx_cena\"> \n" +
                "  <div class=\"sub_img\"></div> 距2号线古翠路站1800米 \n" +
                " </div> \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <div class=\"sub_img\"></div> 距2号线古翠路站1800米 \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <span class=\"location\">3</span> \n" +
                " <a href=\"https://www.dankegongyu.com/room/1110245194.html\" key=\"2\" xiaoqu=\"泰和苑\" target=\"_blank\" title=\"潮鸣  泰和苑 4室1厅\"> 潮鸣 泰和苑 4室1厅 </a> \n" +
                " <div class=\"r_lbx_cena\"> \n" +
                "  <div class=\"sub_img\"></div> 距2号线,5号线建国北路站550米 \n" +
                " </div> \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <div class=\"sub_img\"></div> 距2号线,5号线建国北路站550米 \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <span class=\"location\">4</span> \n" +
                " <a href=\"https://www.dankegongyu.com/room/890649608.html\" key=\"3\" xiaoqu=\"泰和苑\" target=\"_blank\" title=\"潮鸣  泰和苑 4室1厅\"> 潮鸣 泰和苑 4室1厅 </a> \n" +
                " <div class=\"r_lbx_cena\"> \n" +
                "  <div class=\"sub_img\"></div> 距2号线,5号线建国北路站550米 \n" +
                " </div> \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <div class=\"sub_img\"></div> 距2号线,5号线建国北路站550米 \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <span class=\"location\">5</span> \n" +
                " <a href=\"https://www.dankegongyu.com/room/660616186.html\" key=\"4\" xiaoqu=\"悦麟美寓\" target=\"_blank\" title=\"客运中心  悦麟美寓 3室1厅\"> 客运中心 悦麟美寓 3室1厅 </a> \n" +
                " <div class=\"r_lbx_cena\"> \n" +
                "  <div class=\"sub_img\"></div> 距1号线九堡站1750米 \n" +
                " </div> \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <div class=\"sub_img\"></div> 距1号线九堡站1750米 \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <span class=\"location\">6</span> \n" +
                " <a href=\"https://www.dankegongyu.com/room/1782969810.html\" key=\"5\" xiaoqu=\"拱苑小区一期\" target=\"_blank\" title=\"申花  拱苑小区一期 4室1厅\"> 申花 拱苑小区一期 4室1厅 </a> \n" +
                " <div class=\"r_lbx_cena\"> \n" +
                "  <div class=\"sub_img\"></div> 距2号线古翠路站1800米 \n" +
                " </div> \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <div class=\"sub_img\"></div> 距2号线古翠路站1800米 \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <span class=\"location\">7</span> \n" +
                " <a href=\"https://www.dankegongyu.com/room/998722783.html\" key=\"6\" xiaoqu=\"中企艮山府\" target=\"_blank\" title=\"景芳  中企艮山府 3室1厅\"> 景芳 中企艮山府 3室1厅 </a> \n" +
                " <div class=\"r_lbx_cena\"> \n" +
                "  <div class=\"sub_img\"></div> 距1号线,4号线火车东站站1300米 \n" +
                " </div> \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <div class=\"sub_img\"></div> 距1号线,4号线火车东站站1300米 \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <span class=\"location\">8</span> \n" +
                " <a href=\"https://www.dankegongyu.com/room/1357595413.html\" key=\"7\" xiaoqu=\"长江西苑\" target=\"_blank\" title=\"长河路  长江西苑 4室1厅\"> 长河路 长江西苑 4室1厅 </a> \n" +
                " <div class=\"r_lbx_cena\"> \n" +
                "  <div class=\"sub_img\"></div> 距5号线,6号线长河路站2200米 \n" +
                " </div> \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <div class=\"sub_img\"></div> 距5号线,6号线长河路站2200米 \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <span class=\"location\">9</span> \n" +
                " <a href=\"https://www.dankegongyu.com/room/1532519284.html\" key=\"8\" xiaoqu=\"长江西苑\" target=\"_blank\" title=\"长河路  长江西苑 4室1厅\"> 长河路 长江西苑 4室1厅 </a> \n" +
                " <div class=\"r_lbx_cena\"> \n" +
                "  <div class=\"sub_img\"></div> 距5号线,6号线长河路站2200米 \n" +
                " </div> \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <div class=\"sub_img\"></div> 距5号线,6号线长河路站2200米 \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <span class=\"location\">10</span> \n" +
                " <a href=\"https://www.dankegongyu.com/room/1138000091.html\" key=\"9\" xiaoqu=\"长江西苑\" target=\"_blank\" title=\"长河路  长江西苑 4室1厅\"> 长河路 长江西苑 4室1厅 </a> \n" +
                " <div class=\"r_lbx_cena\"> \n" +
                "  <div class=\"sub_img\"></div> 距5号线,6号线长河路站2200米 \n" +
                " </div> \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <div class=\"sub_img\"></div> 距5号线,6号线长河路站2200米 \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <span class=\"location\">11</span> \n" +
                " <a href=\"https://www.dankegongyu.com/room/2028080604.html\" key=\"10\" xiaoqu=\"长江西苑\" target=\"_blank\" title=\"长河路  长江西苑 4室1厅\"> 长河路 长江西苑 4室1厅 </a> \n" +
                " <div class=\"r_lbx_cena\"> \n" +
                "  <div class=\"sub_img\"></div> 距5号线,6号线长河路站2200米 \n" +
                " </div> \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <div class=\"sub_img\"></div> 距5号线,6号线长河路站2200米 \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <span class=\"location\">12</span> \n" +
                " <a href=\"https://www.dankegongyu.com/room/1667181891.html\" key=\"11\" xiaoqu=\"长江西苑\" target=\"_blank\" title=\"长河路  长江西苑 4室1厅\"> 长河路 长江西苑 4室1厅 </a> \n" +
                " <div class=\"r_lbx_cena\"> \n" +
                "  <div class=\"sub_img\"></div> 距5号线,6号线长河路站2200米 \n" +
                " </div> \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <div class=\"sub_img\"></div> 距5号线,6号线长河路站2200米 \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <span class=\"location\">13</span> \n" +
                " <a href=\"https://www.dankegongyu.com/room/1299103806.html\" key=\"12\" xiaoqu=\"三墩颐景园曲水苑\" target=\"_blank\" title=\"三墩  三墩颐景园曲水苑 4室1厅\"> 三墩 三墩颐景园曲水苑 4室1厅 </a> \n" +
                " <div class=\"r_lbx_cena\"> \n" +
                "  <div class=\"sub_img\"></div> 距2号线墩祥街站500米 \n" +
                " </div> \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <div class=\"sub_img\"></div> 距2号线墩祥街站500米 \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <span class=\"location\">14</span> \n" +
                " <a href=\"https://www.dankegongyu.com/room/1659660926.html\" key=\"13\" xiaoqu=\"宋都·晨光国际\" target=\"_blank\" title=\"沿江北  宋都·晨光国际 5室2厅\"> 沿江北 宋都·晨光国际 5室2厅 </a> \n" +
                " <div class=\"r_lbx_cena\"> \n" +
                "  <div class=\"sub_img\"></div> 距1号线下沙江滨站350米 \n" +
                " </div> \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <div class=\"sub_img\"></div> 距1号线下沙江滨站350米 \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <span class=\"location\">15</span> \n" +
                " <a href=\"https://www.dankegongyu.com/room/562911037.html\" key=\"14\" xiaoqu=\"滨江西溪之星\" target=\"_blank\" title=\"未来科技城  滨江西溪之星 4室1厅\"> 未来科技城 滨江西溪之星 4室1厅 </a> \n" +
                " <div class=\"r_lbx_cena\"> \n" +
                "  <div class=\"sub_img\"></div> \n" +
                " </div> \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <div class=\"sub_img\"></div> \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <span class=\"location\">16</span> \n" +
                " <a href=\"https://www.dankegongyu.com/room/687502492.html\" key=\"15\" xiaoqu=\"滨江西溪之星\" target=\"_blank\" title=\"未来科技城  滨江西溪之星 4室1厅\"> 未来科技城 滨江西溪之星 4室1厅 </a> \n" +
                " <div class=\"r_lbx_cena\"> \n" +
                "  <div class=\"sub_img\"></div> \n" +
                " </div> \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <div class=\"sub_img\"></div> \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <span class=\"location\">17</span> \n" +
                " <a href=\"https://www.dankegongyu.com/room/241818861.html\" key=\"16\" xiaoqu=\"滨江西溪之星\" target=\"_blank\" title=\"未来科技城  滨江西溪之星 5室1厅\"> 未来科技城 滨江西溪之星 5室1厅 </a> \n" +
                " <div class=\"r_lbx_cena\"> \n" +
                "  <div class=\"sub_img\"></div> \n" +
                " </div> \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <div class=\"sub_img\"></div> \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <span class=\"location\">18</span> \n" +
                " <a href=\"https://www.dankegongyu.com/room/1774206226.html\" key=\"17\" xiaoqu=\"滨江西溪之星\" target=\"_blank\" title=\"未来科技城  滨江西溪之星 5室1厅\"> 未来科技城 滨江西溪之星 5室1厅 </a> \n" +
                " <div class=\"r_lbx_cena\"> \n" +
                "  <div class=\"sub_img\"></div> \n" +
                " </div> \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <div class=\"sub_img\"></div> \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <span class=\"location\">19</span> \n" +
                " <a href=\"https://www.dankegongyu.com/room/1346198897.html\" key=\"18\" xiaoqu=\"滨江西溪之星\" target=\"_blank\" title=\"未来科技城  滨江西溪之星 5室1厅\"> 未来科技城 滨江西溪之星 5室1厅 </a> \n" +
                " <div class=\"r_lbx_cena\"> \n" +
                "  <div class=\"sub_img\"></div> \n" +
                " </div> \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <div class=\"sub_img\"></div> \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <span class=\"location\">20</span> \n" +
                " <a href=\"https://www.dankegongyu.com/room/572626998.html\" key=\"19\" xiaoqu=\"世茂江滨花园-瑞景湾\" target=\"_blank\" title=\"沿江北  世茂江滨花园-瑞景湾 3室1厅\"> 沿江北 世茂江滨花园-瑞景湾 3室1厅 </a> \n" +
                " <div class=\"r_lbx_cena\"> \n" +
                "  <div class=\"sub_img\"></div> 距1号线下沙江滨站700米 \n" +
                " </div> \n" +
                "</div>\n" +
                "<div class=\"r_lbx_cena\"> \n" +
                " <div class=\"sub_img\"></div> 距1号线下沙江滨站700米 \n" +
                "</div>\n" +
                "[]\n" +
                "\n" +
                "Process finished with exit code 0\n";
        // 按指定模式在字符串查找
        String pattern = "https://www.dankegongyu.com/room/(.*?).html";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(test);
        if (m.find( )) {
            System.out.println(m.groupCount());
        } else {
            System.out.println("NO MATCH");
        }
    }
}
