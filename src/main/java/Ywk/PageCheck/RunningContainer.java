package Ywk.PageCheck;

import Ywk.Data.KeywordGenerator;
import Ywk.PageCheck.Capture.PageRunner;

import java.util.List;

/**
 * generate keywords then use running to crawl
 */
public class RunningContainer implements Runnable {
    private KeywordGenerator keyword;
    private List<PageRunner> runners;
    private volatile boolean finished = false;
    private TaskRunningFinishListener listener;

    RunningContainer(KeywordGenerator keyword, List<PageRunner> runners) {
        this.keyword = keyword;
        this.runners = runners;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setTaskFinishedListener(TaskRunningFinishListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        if (listener.isStopped()) {
            System.out.println("RunningContainer run stopped");
            return;
        }

        String[] next = keyword.nextChunk();
        if (next != null) {
            for (String word : next) {
                if (word != null && !word.isEmpty()) {
                    runners.stream().parallel().forEach(pageRunner -> pageRunner.run(word));
                }
            }
        } else {
            finished = true;
        }

        if (finished) {
            System.out.println("RunningContainer run finished");
            listener.pageCheckFinish();
        }

    }


    public interface TaskRunningFinishListener {
        void pageCheckFinish();

        boolean isStopped();
    }
}
