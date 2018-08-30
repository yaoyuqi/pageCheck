package Ywk.PageCheck;

import Ywk.Data.Keyword;

public class CheckManage implements Runnable {
    private int type;
    private Keyword keyword;
    private BaiduCapture baiduCapture;

    public CheckManage(Keyword keyword, BaiduCapture baiduCapture, int type) {
        this.keyword = keyword;
        this.baiduCapture = baiduCapture;

        this.type = type;
    }

    private volatile boolean finished = false;

    private TaskFinishListener listener;

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setListener(TaskFinishListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        if (listener.isStopped()) {
            System.out.println("CheckManage run stopped");
            return;
        }

        String[] next = keyword.nextChunk();
        if (next != null) {
            for (String word : next) {
                if (word != null && !word.isEmpty()) {
                    baiduCapture.run(word, type);
                }
            }
        } else {
//            //如果完了，就要写文件
//            if (type == Info.TYPE_PC) {
//                writer.flush(Info.TYPE_PC);
//            } else if (type == Info.TYPE_MOBILE) {
//                writer.flush(Info.TYPE_MOBILE);
//            } else {
//                writer.flush(Info.TYPE_PC);
//                writer.flush(Info.TYPE_MOBILE);
//            }
            finished = true;
        }

        if (finished) {
            System.out.println("CheckManage run finished");
            listener.finish();
        }

    }

    public boolean isFinished() {
        return finished;
    }

    public void setType(int type) {
        this.type = type;
    }

    public interface TaskFinishListener {
        public void finish();

        public boolean isStopped();
    }
}
