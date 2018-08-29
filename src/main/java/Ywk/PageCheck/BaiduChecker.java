package Ywk.PageCheck;

import Ywk.Data.Info;
import Ywk.Data.XMLWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BaiduChecker {
    private String identity;

    private XMLWriter writer;

    private CheckResult resultListener;

    public BaiduChecker(String identity, XMLWriter writer) {
        this.identity = identity;
        this.writer = writer;
    }

    public void setResultListener(CheckResult resultListener) {
        this.resultListener = resultListener;
    }

    public void checkPC(String content, String keyword, int page) {
        if (content.contains(identity)) {
            Document doc = Jsoup.parse(content);

            ArrayList<String> list = new ArrayList<>();

            Elements results = doc.select("div.result.c-container");
            for (Element result : results) {
                String data = result.text();
                if (data.contains(identity)) {
                    String index = result.id();
//                    String url = result.selectFirst("a[href]").attr("href");
//                    System.out.println(identity + " loc=" + index + " url=" + url);
//                    writer.add(keyword,  Info.TYPE_PC, url, index, searchUrl, "");
                    list.add(index);
                }
            }
            if (!list.isEmpty()) {
                SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                writer.add(keyword, Info.TYPE_PC, list.toArray(new String[list.size()]), ft.format(new Date()));
                System.out.println(keyword + " check in pc ok");

                Info info = new Info();
                info.setKeyword(keyword);
                info.setType(Info.TYPE_PC);
                info.setTime(ft.format(new Date()));
                info.setLoc(list.toArray(new String[list.size()]));
                info.setPage(page);
                writer.add(info);
                resultListener.found(info);


            }

        } else {
            System.out.println(keyword + " check in pc no results");
        }

    }

    public void checkPcFail(String keyword) {
        System.out.println(keyword + " check in pc fail");
    }

    public void checkMobileFail(String keyword) {
        System.out.println(keyword + " check in mobile fail");
    }

    public void checkMobile(String content, String keyword, int page) {
        if (content.contains(identity)) {
            Document doc = Jsoup.parse(content);

            Elements results = doc.select("#results div.result.c-result");
            /*
                baidu移动端解析出来分成很多块，首先检查下层div
                ec_ad_results  > c-container ec_resitem ec-new2  ec_wise_pp  =>这个是广告栏每个小块的class
                result c-result c-clk-recommend > c-container =>这个是推荐的部分  order 表明位置
                result c-result =>这个相关推荐一般是多个并排
             */

            ArrayList<String> list = new ArrayList<>();


            for (Element result : results) {
                String data = result.text();
                if (data.contains(identity)) {
                    String index = result.attr("order");
                    list.add(index);
//                    String log = result.attr("data-log");
//                    log = log.replace("\'", "\"");
//                    JSONParser parser = new JSONParser();
//                    try {
//                        Object obj = parser.parse(log);
//                        JSONObject object = (JSONObject) obj;
//                        String url = (String) object.get("mu");
//                        System.out.println(identity + " loc=" + index + " url=" + url);
////                        writer.add(keyword, Info.TYPE_MOBILE, url, index, searchUrl, "");
//
//                    }
//                    catch (ParseException e) {
//                        e.printStackTrace();
//                    }
                }
            }

            if (!list.isEmpty()) {
                System.out.println(keyword + " check in mobile ok");

                SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//                writer.add(keyword, Info.TYPE_MOBILE, list.toArray(new String[list.size()]), ft.format(new Date()));
                Info info = new Info();
                info.setKeyword(keyword);
                info.setType(Info.TYPE_MOBILE);
                info.setTime(ft.format(new Date()));
                info.setLoc(list.toArray(new String[list.size()]));
                info.setPage(page);
                writer.add(info);
                resultListener.found(info);
            }


        } else {
            System.out.println(keyword + " check in mobile no results");

        }
    }

    public interface CheckResult {
        public void found(Info info);
    }


}
