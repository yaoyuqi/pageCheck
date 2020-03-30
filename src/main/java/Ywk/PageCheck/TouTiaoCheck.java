package Ywk.PageCheck;

import Ywk.Client.SearchPlatform;
import Ywk.Data.Toutiao;
import Ywk.Data.XMLWriter;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TouTiaoCheck extends ContentChecker {
    TouTiaoCheck(List<String> identities, XMLWriter writer, SearchPlatform platform, NewItemFound newItemFound) {
        super(identities, writer, platform, newItemFound);
    }

    @Override
    public void check(String content, String keyword, int page) {

        if (content != null && !content.isEmpty()) {
            Toutiao toutiao = new Gson().fromJson(content, Toutiao.class);
            String dom = toutiao.getDom();

            if (dom != null && getIdentities().stream().parallel().anyMatch(content::contains)) {
                Document doc = Jsoup.parse(dom);
                Elements elements = doc.select(".result-content");

                Map<String, List<String>> result = new HashMap<>();
                int loc = 0;
                for (Element element : elements) {
                    loc++;
                    String data = element.text();
                    Optional<String> match = getIdentities().stream().parallel().filter(data::contains).findAny();
                    if (match.isPresent()) {
                        String index = "" + loc;
                        checkMatched(result, match.get(), index);
                    }
                }

                writeResult(keyword, page, result);
            } else {
//            System.out.println(content);
                System.out.println(keyword + " toutiao check in pc no results");
            }
        }
    }

    @Override
    public void checkFail(String keyword, String url) {
        System.out.println(keyword + " toutiao check in [" + url + "] fail");
    }
}
