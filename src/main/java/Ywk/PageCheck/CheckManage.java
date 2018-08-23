package Ywk.PageCheck;

import Ywk.Data.Info;
import Ywk.Data.Keyword;
import Ywk.Data.XMLWriter;

public class CheckManage implements Runnable {
    private Keyword keyword;

    private BaiduCapture baiduCapture;

    private XMLWriter writer;

    private final int type;

    private volatile boolean finished = false;

    public CheckManage(Keyword keyword, BaiduCapture baiduCapture, XMLWriter writer, int type) {
        this.keyword = keyword;
        this.baiduCapture = baiduCapture;
        this.writer = writer;

        this.type = type;
    }

    @Override
    public void run() {
        String[] next = keyword.nextChunk();
        if (next != null) {
            for (String word : next) {
                if (!word.isEmpty()) {
                    baiduCapture.run(word, type);
                }
            }
        } else {
            //如果完了，就要写文件
            if (type == Info.TYPE_PC) {
                writer.flush(Info.TYPE_PC);
            } else {
                writer.flush(Info.TYPE_MOBILE);
            }
            finished = true;
        }

    }

    public boolean isFinished() {
        return finished;
    }
}
