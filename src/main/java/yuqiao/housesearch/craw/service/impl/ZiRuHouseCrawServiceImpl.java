package yuqiao.housesearch.craw.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import yuqiao.housesearch.craw.service.AbstractHouseCrawService;
import yuqiao.housesearch.craw.util.OKHttpUtil;
import yuqiao.housesearch.house.entity.House;
import yuqiao.housesearch.util.FileUtil;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 浦希成
 * 2018-12-18
 */
@Slf4j
@Component
public class ZiRuHouseCrawServiceImpl extends AbstractHouseCrawService {
    private String[] cities = {"bz", "sz", "sh", "hz", "tj", "wh", "nj", "gz", "cd"};
    private OKHttpUtil okHttpUtil = new OKHttpUtil();

    @Override
    public Integer crawHousesInfo(String city) {

        String url = String.format("http://%s.ziroom.com/z/nl/z3.html", city);
        String html = okHttpUtil.getHtml(url);
        Document doc = Jsoup.parse(html);
        Elements tags = doc.select("#houseList");
        String pattern = "<a target=\"_blank\" href=\"(.*)\" class=\"t1\"";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(tags.html());
        while (m.find()) {
            String detailUrl = "http:" + m.group().substring(25, 59);
            House house = getDetailInfo(detailUrl);
            insertIntoMysql(house);

        }
        return sum.get();
    }

    @Override
    public House getDetailInfo(String url) {
        String html = okHttpUtil.getHtml(url);
        Document doc = Jsoup.parse(html);

        String title = doc.select("body > div.area.clearfix > div.room_detail_right > div.room_name > h2").text();

        String number = doc.select("body > div.area.clearfix > div.room_detail_left > div.aboutRoom.gray-6 > h3").text().split("：")[1].trim();
        String houseId=doc.select("#house_id").val();
        String roomId=doc.select("#room_id").val();
        String price=getPrice(houseId,roomId);
        System.out.println(price);
//        Integer area = Integer.parseInt(doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(1) > div:nth-child(1) > label").text().replaceAll("[^0-9]", ""));
//        String type = doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(1) > div:nth-child(3) > label").text().split("：")[1];
//        Integer floor = Integer.parseInt(doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(2) > div:nth-child(2) > label").text().split("/")[0].replaceAll("[^0-9]", ""));
//        Integer totalFloor = Integer.parseInt(doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(2) > div:nth-child(2) > label").text().split("/")[1].replaceAll("[^0-9]", ""));
//        String direction = doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(2) > div:nth-child(1) > label").text().split("：")[1];
//        String[] subways = doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(2) > div:nth-child(4) > label").text().split("：");
//        String subway = "";
//        if (subways.length > 1) {
//            subway = subways[1];
//        }
//        StringBuilder tags = new StringBuilder();
//        Elements tagElements = doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-title > span");
//        for (Element tag : tagElements) {
//            tags.append(tag.text()).append(",");
//        }
//
//        House house = new House();
//        house.setTitle(title);
//        house.setNumber(number);
//        house.setPrice(price);
//        house.setArea(area);
//        house.setType(type);
//        house.setType(type);
//        house.setFloor(floor);
//        house.setTotalFloor(totalFloor);
//        house.setDirection(direction);
//        house.setSubway(subway);
//        house.setTag(tags.toString());
//        house.setOrigin(OriginEnun.DANKE.getCode());
        return null;
    }

    private String getPrice(String houseId, String roomId) {
        try {
            String tempFile="/Users/pxc/Downloads/test.png";
            OKHttpUtil okHttpUtil = new OKHttpUtil();
            JSONObject jsonObject = okHttpUtil.getJson(String.format("http://hz.ziroom.com/detail/info?id=%s&house_id=%s",roomId,houseId));
            String pricePicUrl="http:"+jsonObject.getJSONObject("data").getJSONArray("price").get(1).toString();
            InputStream imageFile = okHttpUtil.getFileStream(pricePicUrl);
            FileUtil.saveFile(imageFile,tempFile);
            Tesseract instance = new Tesseract();
            instance.setDatapath("/Users/pxc/Documents/java/house-search/tessdata");
            String result = instance.doOCR(new File(tempFile));
            char[] chars=result.toCharArray();
            JSONArray objects = jsonObject.getJSONObject("data").getJSONArray("price").getJSONArray(2);
            StringBuilder stringBuilder=new StringBuilder();
            for (Object object : objects) {
                stringBuilder.append(chars[Integer.parseInt(object+"")]);
            }
            System.out.println(stringBuilder);
        }catch (Exception e){
            log.error("获取自如的价格失败:{},{}",roomId,e);
        }
        return "";
    }

    @Override
    public boolean isExist(House house) {
        return false;
    }

    @Override
    public void start() {
        start(cities);
    }

    public static void main(String[] args) throws TesseractException, IOException {


        ZiRuHouseCrawServiceImpl ziRuHouseCrawService=new ZiRuHouseCrawServiceImpl();
        ziRuHouseCrawService.getDetailInfo("http://hz.ziroom.com/z/vr/61723899.html");


    }
}
