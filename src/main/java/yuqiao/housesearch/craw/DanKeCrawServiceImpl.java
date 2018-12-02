package yuqiao.housesearch.craw;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yuqiao.housesearch.common.constants.Constants;
import yuqiao.housesearch.common.enums.OriginEnun;
import yuqiao.housesearch.house.entity.House;
import yuqiao.housesearch.house.mapper.HouseMapper;
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
@Slf4j
@Service
public class DanKeCrawServiceImpl implements DanKeCrawService {
    @Autowired
    private HouseMapper houseMapper;
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
        // 按指定模式在字符串查找
        String pattern = "https://www.dankegongyu.com/room/(.*?).html";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(tags.html());
        while (m.find( )) {
            String detailUrl=m.group();
            log.info("爬取：{}",detailUrl);
            houses.add(getDetailInfo(detailUrl));

        }


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
        String[] subways = doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(2) > div:nth-child(4) > label").text().split("：");
        String subway="";
        if (subways.length>1){
            subway=subways[1];
        }
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

}
