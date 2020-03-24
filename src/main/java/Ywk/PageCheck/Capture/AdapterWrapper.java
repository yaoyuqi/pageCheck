package Ywk.PageCheck.Capture;

public class AdapterWrapper {

    private static BaiduAdapter instance;

    private AdapterWrapper() {
    }

    public static BaiduAdapter getInstance() {
        if (instance == null) {
            instance = new BaiduAdapter();
        }
        return instance;
    }
}
