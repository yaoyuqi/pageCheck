package Ywk.PageCheck;

import Ywk.Data.Info;

public class BaiduCapture {
    private final static String PC_URL = "https://www.baidu.com/s?wd=";
    private final static String MOBILE_URL = "https://m.baidu.com/s?wd=";

    private PageCapture capture;

    public BaiduCapture(BaiduChecker check, int maxRequest) {
        capture = new PageCapture(maxRequest, maxRequest);
        capture.setChecker(check);
    }

    public static String makeUrl(int type, String keyword) {
        if (type == Info.TYPE_PC) {
            return PC_URL + keyword;

        } else {
            return MOBILE_URL + keyword;
        }
    }

    public PageCapture getCapture() {
        return capture;
    }

    public void setIdleHandler(Runnable handler) {
        capture.setIdleCallback(handler);
    }

    public void run(String keyword, int type) {
        if (type == Info.TYPE_PC) {
            capture.pc(makeUrl(type, keyword), keyword);
        } else if (type == Info.TYPE_MOBILE) {
            capture.mobile(makeUrl(type, keyword), keyword);
        } else {
            capture.pc(makeUrl(Info.TYPE_PC, keyword), keyword);
            capture.mobile(makeUrl(Info.TYPE_MOBILE, keyword), keyword);
        }
    }

}
