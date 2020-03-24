package Ywk.PageCheck;

import Ywk.Data.Info;
import Ywk.Data.SearchPlatform;
import Ywk.Data.XMLWriter;
import Ywk.PageCheck.Capture.AdapterWrapper;
import Ywk.PageCheck.Capture.BaiduAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * parse the content, find the matched results and write them to writer
 */
public class ContentChecker {
    private List<String> identities;

    private XMLWriter writer;

    private NewItemFound foundListener;
    private SearchPlatform platform;

    private PageValidate validateListener;

    public void setValidateListener(PageValidate validateListener) {
        this.validateListener = validateListener;
    }

    ContentChecker(List<String> identities, XMLWriter writer, SearchPlatform platform, NewItemFound newItemFound) {
        this.identities = identities;
        this.writer = writer;
        this.platform = platform;
        this.foundListener = newItemFound;
    }

    public SearchPlatform getPlatform() {
        return platform;
    }

    public void check(String content, String keyword, int page, String url) throws URISyntaxException {
        if (content != null
                && content.contains("安全验证")
        ) {
            System.out.println(keyword + " 出现安全验证");
//            System.out.println(content);
//            validateListener.validate(url);
            return;
        }

        BaiduAdapter adapter = AdapterWrapper.getInstance();

        adapter.parse(content, url);

//        if (!baiduEncrypedKey.isEmpty()) {
//            ConcurrentHashMap<String, List<Cookie>> cookieStore = CookiesStore.getCookieStore();
//            URI uri = new URI(url);
//            List<Cookie> list = cookieStore.getOrDefault(uri.getHost(), new ArrayList<>());
//
//            Optional<Cookie> hpsCookie = list.stream().filter(item -> item.name().equals("H_PS_645EC")).findAny();
//
//            list = list.stream().map(item -> {
//                if (!item.name().equals("H_PS_645EC")) {
//                    return item;
//                }
//                return new Cookie.Builder()
//                        .domain("www.baidu.com")
//                        .name("H_PS_645EC")
//                        .path("/")
//                        .value(baiduEncrypedKey)
//                        .expiresAt(2592000)
//                        .build();
//
//            }).collect(Collectors.toList());
//            cookieStore.put(uri.getHost(), list);
//        }

        if (content != null
                && identities.stream().parallel().anyMatch(content::contains)
        ) {
            Document doc = Jsoup.parse(content);
            Elements elements = doc.select(platform.getPattern());
            Map<String, List<String>> result = new HashMap<>();
            int loc = 0;
            for (Element element : elements) {
                loc++;
                String data = element.text();
                Optional<String> match = identities.stream().parallel().filter(data::contains).findAny();
                if (match.isPresent()) {
                    String index = "" + loc;
                    checkMatched(result, match.get(), index);
                }
            }

            writeResult(keyword, page, result);

        } else {
//            System.out.println(content);
            System.out.println(keyword + " check in pc no results");
        }

    }


    private void writeResult(String keyword, int page, Map<String, List<String>> result) {
        for (String identity : result.keySet()) {
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(keyword + " check in pc ok");

            Info info = new Info();
            info.setKeyword(keyword);
            info.setIdentity(identity);
            info.setPlatform(platform);
            info.setTime(ft.format(new Date()));
            info.setLoc(result.get(identity).toArray(new String[0]));
            info.setPage(page);
            writer.add(info);
            foundListener.found(info);
        }
    }

    private void checkMatched(Map<String, List<String>> result, String match, String index) {
        if (!result.containsKey(match)) {
            List<String> list = new ArrayList<>();
            list.add(index);
            result.put(match, list);
        } else {
            result.get(match).add(index);
        }
    }

    public void checkFail(String keyword, String url) {
        System.out.println(keyword + " check in [" + url + "] fail");
    }

    /**
     * Listener for new Bingo found
     */
    public interface NewItemFound {
        void found(Info info);
    }

    public interface PageValidate {
        void validate(String url);
    }
}
