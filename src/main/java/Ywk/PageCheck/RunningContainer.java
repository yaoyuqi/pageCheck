package Ywk.PageCheck;

import Ywk.Data.KeywordGenerator;
import Ywk.Data.SearchPlatform;
import Ywk.PageCheck.Capture.PageRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * generate keywords then use running to crawl
 */
public class RunningContainer implements Runnable {
    private KeywordGenerator keyword;
    private final List<PageRunner> allRunners;
    private List<PageRunner> availableRunners = new ArrayList<>();
    private volatile boolean finished = false;
    private TaskRunningFinishListener listener;

    RunningContainer(KeywordGenerator keyword, List<PageRunner> runners) {
        this.keyword = keyword;
        this.allRunners = runners;
        availableRunners.addAll(this.allRunners);
    }

    void setAvailablePlatforms(List<SearchPlatform> availablePlatforms) {
        availableRunners.clear();
        availableRunners.addAll(
                allRunners.stream()
                        .filter(pageRunner -> availablePlatforms.stream().anyMatch(pageRunner::isPlatform))
                        .collect(Collectors.toList())
        );
    }

    void setFinished(boolean finished) {
        this.finished = finished;
    }

    void setTaskFinishedListener(TaskRunningFinishListener listener) {
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
                    availableRunners.stream().parallel()
                            .forEach(pageRunner -> pageRunner.run(word));
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
