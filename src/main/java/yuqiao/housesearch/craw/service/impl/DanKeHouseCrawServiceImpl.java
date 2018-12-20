package yuqiao.housesearch.craw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yuqiao.housesearch.common.constants.Constants;
import yuqiao.housesearch.common.enums.OriginEnun;
import yuqiao.housesearch.craw.service.AbstractHouseCrawService;
import yuqiao.housesearch.craw.util.OKHttpUtil;
import yuqiao.housesearch.house.entity.House;
import yuqiao.housesearch.house.mapper.HouseMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 浦希成
 * 2018-12-15
 */
@Slf4j
@Service
public class DanKeHouseCrawServiceImpl extends AbstractHouseCrawService {

    private OKHttpUtil okHttpUtil = new OKHttpUtil();

    private String[] cities={"bz", "sz", "sh", "hz", "tj", "wh", "nj", "gz", "cd"};

    @Autowired
    public DanKeHouseCrawServiceImpl(HouseMapper houseMapper) {
        super.houseMapper = houseMapper;
    }

    @Override
    public Integer crawHousesInfo(String city) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "www.dankegongyu.com");
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        for (int i = 1; i <= 1500; i++) {
            log.info("正在 {} 爬取 {} 页", city, i);
            headers.put("Upgrade-Insecure-Requests", "1");
            String url = Constants.DNAKE_URL + "room/" + city + "?page=" + i;
            String html = okHttpUtil.getHtml(url, headers);
            if (html.contains("您查找的房源已售罄")) {
                log.info("爬取结束，最后一页是：{}", html);
                return sum.get();
            }
            Document doc = Jsoup.parse(html);
            Elements tags = doc.select(".r_lbx_cena");
            String pattern = "https://www.dankegongyu.com/room/(.*?).html";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(tags.html());
            while (m.find()) {
                String detailUrl = m.group();
                House house = getDetailInfo(detailUrl);
                insertIntoMysql(house);

            }
        }
        return sum.get();
    }




    @Override
    public House getDetailInfo(String detailUrl) {
        String html = okHttpUtil.getHtml(detailUrl);
        Document doc = Jsoup.parse(html);
        String title = doc.select("div.room-name > h1").text();

        String number;
        try {
            number = doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(1) > div:nth-child(2) > label").text().split("：")[1];
        } catch (Exception e) {
            log.error("编号获取失败:{}", detailUrl);
            number = "";
        }
        Integer price = Integer.parseInt(doc.select(".room-price-sale").text().replaceAll("[^0-9]", ""));
        Integer area = Integer.parseInt(doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(1) > div:nth-child(1) > label").text().replaceAll("[^0-9]", ""));
        String type = doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(1) > div:nth-child(3) > label").text().split("：")[1];
        Integer floor = Integer.parseInt(doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(2) > div:nth-child(2) > label").text().split("/")[0].replaceAll("[^0-9]", ""));
        Integer totalFloor = Integer.parseInt(doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(2) > div:nth-child(2) > label").text().split("/")[1].replaceAll("[^0-9]", ""));
        String direction = doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(2) > div:nth-child(1) > label").text().split("：")[1];
        String[] subways = doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(2) > div:nth-child(4) > label").text().split("：");
        String subway = "";
        if (subways.length > 1) {
            subway = subways[1];
        }
        StringBuilder tags = new StringBuilder();
        Elements tagElements = doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-title > span");
        for (Element tag : tagElements) {
            tags.append(tag.text()).append(",");
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

    @Override
    public boolean isExist(House house) {
        //蛋壳是根据number来判断是否已经存在
        return houseMapper.selectOne(new QueryWrapper<House>().eq("number", house.getNumber())) != null;
    }

    @Override
    public void start() {
        super.start(cities);
    }
}
