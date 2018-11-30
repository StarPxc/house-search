package yuqiao.housesearch.craw;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import yuqiao.housesearch.util.OKHttpUtil;

import java.io.IOException;

/**
 * @author 浦希成
 * 2018-11-30
 */
public class DanKeCrawServiceImpl implements DanKeCrawService {
    public static void main(String[] args) {
        OKHttpUtil util=new OKHttpUtil();
        String html=util.getHtml("https://www.dankegongyu.com/room/136992205.html");
        Document doc = Jsoup.parse(html);
        Elements label = doc.select("body > div.website-main > div.wrapper > div.room-detail > div.room-detail-right > div.room-list-box > div:nth-child(1) > div:nth-child(3) > label");
        System.out.println(label);
    }
}
