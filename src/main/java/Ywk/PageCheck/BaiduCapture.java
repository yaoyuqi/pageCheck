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

    public void setIdleHandler(Runnable handler) {
        capture.setIdleCallback(handler);
    }

    public void run(String keyword, int type) {
        if (type == Info.TYPE_PC) {
            String hostPc = PC_URL + keyword;
            capture.pc(hostPc, keyword);

        }
        else {
            String hostMobile = MOBILE_URL + keyword;
            capture.mobile(hostMobile, keyword);
        }
    }

}
