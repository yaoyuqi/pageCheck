package Ywk.PageCheck;

import Ywk.Client.SearchPlatform;

public interface Checker {
    SearchPlatform getPlatform();

    void check(String content, String keyword, int page);

    void checkFail(String keyword, String url);

    void setValidateListener(ContentChecker.PageValidate validateListener);
}
