package yuqiao.housesearch.craw.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yuqiao.housesearch.common.constants.Constants;
import yuqiao.housesearch.common.enums.OriginEnun;
import yuqiao.housesearch.craw.service.AbstractHouseCrawService;
import yuqiao.housesearch.craw.util.OKHttpUtil;
import yuqiao.housesearch.es.entity.HouseEntity;
import yuqiao.housesearch.es.repository.HouseEntityRepository;
import yuqiao.housesearch.house.entity.House;
import yuqiao.housesearch.house.mapper.HouseMapper;

import java.util.*;
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

    private String[] cities = {"bj", "sz", "sh", "hz", "tj", "wh", "nj", "gz", "cd"};

    @Autowired
    public DanKeHouseCrawServiceImpl(HouseMapper houseMapper, HouseEntityRepository houseEntityRepository) {
        super.houseMapper = houseMapper;
        super.houseEntityRepository=houseEntityRepository;
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
                log.info("爬取结束，最后一页是：{}", url);
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
                //存入数据库
                insertIntoMysql(house);
                //存入es
                HouseEntity entity=new HouseEntity();
                BeanUtils.copyProperties(house,entity);
                houseEntityRepository.save(entity);

            }
        }
        return sum.get();
    }


    @Override
    public House getDetailInfo(String detailUrl) {
        String html = okHttpUtil.getHtml(detailUrl);
        Document doc = Jsoup.parse(html);
        String title = doc.select("div.room-name > h1").text();
        //编号
        String number;
        try {
            number = doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(1) > div:nth-child(2) > label").text().split("：")[1];
        } catch (Exception e) {
            log.error("编号获取失败:{}", detailUrl);
            number = "";
        }

        //价格
        Integer price = Integer.parseInt(doc.select(".room-price-sale").text().replaceAll("[^0-9]", ""));

        //地址
        Float area = Float.parseFloat(doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(1) > div:nth-child(1) > label").text().replaceAll("[^0-9]", ""));

        //类型
        String type = doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(1) > div:nth-child(3) > label").text().split("：")[1];

        //楼层
        Integer floor = Integer.parseInt(doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(2) > div:nth-child(2) > label").text().split("/")[0].replaceAll("[^0-9]", ""));

        //总楼层
        Integer totalFloor = Integer.parseInt(doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(2) > div:nth-child(2) > label").text().split("/")[1].replaceAll("[^0-9]", ""));

        //朝向
        String direction = doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(2) > div:nth-child(1) > label").text().split("：")[1];

        //地铁
        String[] subways = doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(2) > div:nth-child(4) > label").text().split("：");
        String subway = "";
        if (subways.length > 1) {
            subway = subways[1];
        }

        //标签
        StringBuilder tags = new StringBuilder();
        Elements tagElements = doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-title > span");
        for (Element tag : tagElements) {
            tags.append(tag.text()).append(",");
        }
        //城市
        String city = doc.select("body > div.website-main > div.wrapper > div.intro a:nth-child(2)").text().split("租房")[0];

        //地区
        String district = doc.select("body > div.website-main > div.wrapper > div.intro a:nth-child(3)").text().split(" ")[0];

        //街道
        String street = doc.select("body > div.website-main > div.wrapper > div.intro a:nth-child(4)").text().split("租房")[0];

        //小区
        String community = doc.select("body > div.website-main > div.wrapper > div.intro a:nth-child(5)").text().split("租房")[0];

        String pattern = "https://public.danke.com.cn/public-(.*?).jpg";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(html);
        StringBuilder imgUrls=new StringBuilder();
        int i=0;
        while (m.find()&&i<2) {
            i++;
            imgUrls.append(m.group());
        }
        House house = new House();
        house.setCrawTime(new Date());
        house.setDistrict(district);
        house.setStreet(street);
        house.setCommunity(community);
        house.setCity(city);
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
        house.setUrl(detailUrl);
        house.setImgUrls(imgUrls.toString());
        return house;
    }


    @Override
    public void start() {
        super.start(cities);
    }

}
