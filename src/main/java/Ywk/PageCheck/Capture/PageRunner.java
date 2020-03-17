package Ywk.PageCheck.Capture;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PageRunner {

    private PageSpider spider;

    private int checkDepth = 1;

    public PageRunner(PageSpider spider) {
        this.spider = spider;
    }

    private String makeUrl(String keyword, int page) {
        if (keyword != null && !keyword.isEmpty()) {
            try {
                keyword = URLEncoder.encode(keyword, "UTF-8");
//                return spider.getPlatform().getUrl() + keyword + "&pn=" + (page - 1) * 10;
                return spider.getPlatform().nextPageUrl(keyword, page);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void setCheckDepth(int checkDepth) {
        this.checkDepth = checkDepth;
    }

    public void setIdleHandler(Runnable handler) {
        spider.setIdleCallback(handler);
    }


    public void stop() {
        spider.stopAll();
    }


    public void run(String keyword) {
        for (int i = 1; i <= checkDepth; i++) {
            spider.run(makeUrl(keyword, i), keyword, i);

        }
    }

    public void setSpeed(int speed) {
        spider.setSpeed(speed);
    }

    public void setPageCheckedListener(PageSpider.PageChecked listener) {
        spider.setPageCheckedListener(listener);
    }
}
