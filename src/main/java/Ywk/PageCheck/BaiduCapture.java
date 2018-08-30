package Ywk.PageCheck;

import Ywk.Data.Info;
import okhttp3.OkHttpClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class BaiduCapture {
    private final static String PC_URL = "https://www.baidu.com/s?ie=utf-8&wd=";
    private final static String MOBILE_URL = "https://m.baidu.com/s?ie=utf-8&wd=";

    private PageCapture capture;

    private int checkDepth = 1;

    public BaiduCapture(BaiduChecker check, int maxRequest) {
        capture = new PageCapture(maxRequest, maxRequest);
        capture.setChecker(check);
    }

    public BaiduCapture(BaiduChecker check, OkHttpClient client) {
        capture = new PageCapture(client);
        capture.setChecker(check);
    }

    public static String makeUrl(int type, String keyword, int page) {

        if (keyword != null && !keyword.isEmpty()) {
            try {
                keyword = URLEncoder.encode(keyword, "UTF-8");
                if (type == Info.TYPE_PC) {
                    return PC_URL + keyword + "&pn=" + (page - 1) * 10;

                } else {
                    return MOBILE_URL + keyword + "&pn=" + (page - 1) * 10;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return null;

    }

    public void setCheckDepth(int checkDepth) {
        this.checkDepth = checkDepth;
    }

    public PageCapture getCapture() {
        return capture;
    }

    public void setIdleHandler(Runnable handler) {
        capture.setIdleCallback(handler);
    }

    public void run(String keyword, int type) {
        for (int i = 1; i <= checkDepth; i++) {
            if (type == Info.TYPE_PC) {
                capture.pc(makeUrl(type, keyword, i), keyword, i);
            } else if (type == Info.TYPE_MOBILE) {
                capture.mobile(makeUrl(type, keyword, i), keyword, i);
            } else {
                capture.pc(makeUrl(Info.TYPE_PC, keyword, i), keyword, i);
                capture.mobile(makeUrl(Info.TYPE_MOBILE, keyword, i), keyword, i);
            }
        }

    }

    public void setSpeed(int speed) {
        capture.setSpeed(speed);
    }

}
