package yuqiao.housesearch.craw.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yuqiao.housesearch.common.enums.HouseStatusEnum;
import yuqiao.housesearch.common.enums.OriginEnun;
import yuqiao.housesearch.craw.service.AbstractHouseCrawService;
import yuqiao.housesearch.craw.util.OKHttpUtil;
import yuqiao.housesearch.house.entity.House;
import yuqiao.housesearch.house.mapper.HouseMapper;
import yuqiao.housesearch.util.FileUtil;
import yuqiao.housesearch.util.StringUtil;


import java.io.File;
import java.io.InputStream;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 浦希成
 * 2018-12-18
 */
@Slf4j
@Component
public class ZiRuHouseCrawServiceImpl extends AbstractHouseCrawService {


    private OKHttpUtil okHttpUtil = new OKHttpUtil();

    private String[] cities = {"bj", "sz", "sh", "hz", "tj", "wh", "nj", "gz", "cd"};

    @Autowired
    public ZiRuHouseCrawServiceImpl(HouseMapper houseMapper) {
        super.houseMapper = houseMapper;
    }

    @Override
    public Integer crawHousesInfo(String city) {
        String url;
        if ("bj".equalsIgnoreCase(city)) {
            url = "http://www.ziroom.com/z/nl/z3.html";

        } else {
            url = String.format("http://%s.ziroom.com/z/nl/z3.html", city);

        }
        String html = okHttpUtil.getHtml(url);
        int pageNum = Integer.parseInt(StringUtil.getMatcher("共(\\d{1,3})页", html));
        for (int i = 1; i <= pageNum; i++) {
            log.info("正在爬取 {} 的第{}页",city,i);
            Document doc = Jsoup.parse(okHttpUtil.getHtml(url + "?p=" + i));
            Elements tags = doc.select("#houseList");
            String pattern = "<a target=\"_blank\" href=\"(.*)\" class=\"t1\"";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(tags.html());
            while (m.find()) {
                String detailUrl = "http:" + m.group().substring(25, 59);
                House house = getDetailInfo(detailUrl);
                insertIntoMysql(house);
            }
        }
         log.info("{} 爬取结束",city);  

        return sum.get();
    }

    @Override
    public House getDetailInfo(String url) {
        OKHttpUtil okHttpUtil = new OKHttpUtil();

        String html = okHttpUtil.getHtml(url);
        Document doc = Jsoup.parse(html);
        String houseId = doc.select("#house_id").val();
        String roomId = doc.select("#room_id").val();

        //编号
        String number = doc.select("body > div.area.clearfix > div.room_detail_left > div.aboutRoom.gray-6 > h3").text().split("： ")[1];

        // 标题
        String title = doc.select("body > div.area.clearfix > div.room_detail_right > div.room_name > h2").text();

        //价格
        String price = getPrice(houseId, roomId);

        //面积
        Float area = Float.parseFloat(StringUtil.getNumber(doc.select("body > div.area.clearfix > div.room_detail_right > ul > li:nth-child(1)").text()));

        //户型
        String type = doc.select("body > div.area.clearfix > div.room_detail_right > ul > li:nth-child(3)").text().split("： ")[1];

        //楼层
        Integer floor = Integer.parseInt(StringUtil.getMatcher("(\\d{1,3}/\\d{1,3})", doc.select("body > div.area.clearfix > div.room_detail_right > ul > li:nth-child(4)").text()).split("/")[0]);

        //总楼层
        Integer totalFloor = Integer.parseInt(StringUtil.getMatcher("(\\d{1,3}/\\d{1,3})", doc.select("body > div.area.clearfix > div.room_detail_right > ul > li:nth-child(4)").text()).split("/")[1]);

        //城市
        String city = url.substring(7,9);

        //区域
        String district = doc.select("body > div.node_infor.area > a:nth-child(2)").text().split("合租")[0];

        //街道
        String street = doc.select("body > div.node_infor.area > a:nth-child(3)").text().split("公寓出租")[0];


        //所在小区
        String community = doc.select("body > div.node_infor.area > a:nth-child(4)").text().split("租房信息")[0];

        //房屋朝向
        String direction = doc.select("body > div.area.clearfix > div.room_detail_right > ul > li:nth-child(2)").text().split("： ")[1];

        //地铁信息
        String subway = doc.select("#lineList") == null ? "" : doc.select("#lineList").text();

        //标签
        String tag = StringUtils.join(doc.select("body > div.area.clearfix > div.room_detail_right > p").text().split(" "), ",");

        House house = new House();
        house.setTitle(title);
        house.setNumber(number);
        house.setPrice(Integer.parseInt(price));
        house.setArea(area);
        house.setType(type);
        house.setType(type);
        house.setFloor(floor);
        house.setTotalFloor(totalFloor);
        house.setDirection(direction);
        house.setSubway(subway);
        house.setTag(tag);
        house.setStatus(HouseStatusEnum.NORMAL.getCode());
        house.setCommunity(community);
        house.setStreet(street);
        house.setDistrict(district);
        house.setCity(city);
        house.setOrigin(OriginEnun.ZIRU.getCode());
        house.setUrl(url);
        return house;
    }

    private String getPrice(String houseId, String roomId) {
        try {
            String tempFile = String.format("/Users/pxc/Downloads/%s.png", houseId);
            OKHttpUtil okHttpUtil = new OKHttpUtil();
            JSONObject jsonObject = okHttpUtil.getJson(String.format("http://hz.ziroom.com/detail/info?id=%s&house_id=%s", roomId, houseId));
            String pricePicUrl = "http:" + jsonObject.getJSONObject("data").getJSONArray("price").get(1).toString();
            InputStream imageFile = okHttpUtil.getFileStream(pricePicUrl);
            FileUtil.saveFile(imageFile, tempFile);
            Tesseract instance = new Tesseract();
            instance.setDatapath("/Users/pxc/Documents/java/house-search/tessdata");
            String result = instance.doOCR(new File(tempFile));
            char[] chars = result.toCharArray();
            JSONArray objects = jsonObject.getJSONObject("data").getJSONArray("price").getJSONArray(2);
            StringBuilder stringBuilder = new StringBuilder();
            for (Object object : objects) {
                stringBuilder.append(chars[Integer.parseInt(object + "")]);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            log.error("获取自如的价格失败: {} , {}",roomId, e);
        }
        return "";
    }


    @Override
    public void start() {
        start(cities);
    }

    public static void main(String[] args) {
        ZiRuHouseCrawServiceImpl ziRuHouseCrawService=new ZiRuHouseCrawServiceImpl(null);
        ziRuHouseCrawService.getDetailInfo("http://tj.ziroom.com/z/vr/61687319.html");
    }
}
