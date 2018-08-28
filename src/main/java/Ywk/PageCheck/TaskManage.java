package Ywk.PageCheck;


import Ywk.Api.HltApi;
import Ywk.Api.WordData;
import Ywk.Data.Info;
import Ywk.Data.Keyword;
import Ywk.Data.XMLWriter;
import Ywk.UserInterface.Controller.HomeController;

public class TaskManage implements Runnable
        , PageCapture.RunningUpdateListener
        , CheckManage.TaskFinishListener
        , BaiduChecker.CheckResult {
    public static final int TASK_STATUS_NEW = 0;
    public static final int TASK_STATUS_RUNNING = 1;
    public static final int TASK_STATUS_RESUME = 2;
    public static final int TASK_STATUS_STOPPED = 3;
    public static final int TASK_STATUS_FINISHED = 4;


    private int type = Info.TYPE_PC;

    private int speed = 1;

    private String identity;

    private Keyword keyword;

    private CheckManage manage;

    private int total = 0;

    private int pcBingo = 0;
    private int mobileBingo = 0;

    private int runned = 0;

    private volatile int taskStatus;

    private BaiduCapture capture;

    private HomeController controller;

    public TaskManage(String identity) {
        this.identity = identity;
    }

    public TaskManage() {
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public void setController(HomeController controller) {
        this.controller = controller;
    }

    public int getRunned() {
        return runned;
    }

    private void incrementRunned() {
        this.runned++;
    }

    private void prepareKeyword() {
        if (keyword == null) {
//            String[] prefix = {"成都", "重庆", "肯尼亚"};
//            String[] main = {"CCIC", "CCIB", "CCIA"};
//            String[] suffix = {"怎么样", "如何"};

//        setTaskStatus(TaskManage.TASK_STATUS_NEW);

            HltApi api = HltApi.getInstance();
            WordData wordData = api.words();

            keyword = new Keyword(wordData.getData().getPrefix().toArray(new String[]{}),
                    wordData.getData().getMain().toArray(new String[]{}),
                    wordData.getData().getSuffix().toArray(new String[]{}));


//            keyword = new Keyword(prefix, main, suffix);
        }

        controller.setTotal(keyword.getTotal());
    }


    public int getTotal() {
        return total;
    }

    private void prepareCheckTool() {
        if (capture == null || manage == null) {
            XMLWriter writer = new XMLWriter();
            BaiduChecker checker = new BaiduChecker(identity, writer);
//            capture = new BaiduCapture(checker, getRunningSpeed());
            capture = new BaiduCapture(checker, controller.getClient());
            checker.setResultListener(this);

            PageCapture pageCapture = capture.getCapture();
            pageCapture.setListener(this);

            manage = new CheckManage(keyword, capture, writer, type);
            capture.setIdleHandler(manage);

            manage.setListener(this);
        } else {
            manage.setType(type);
        }
        capture.setSpeed(getRunningSpeed());
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    private int getRunningSpeed() {
        return speed * 5;
    }


    @Override
    public void run() {
        prepareKeyword();
        prepareCheckTool();

        if (taskStatus == TASK_STATUS_NEW) {
            prepareForNew();
        }

        setTaskStatus(TaskManage.TASK_STATUS_RUNNING);

        manage.run();
    }

    @Override
    public void updateRunningInfo() {
        incrementRunned();
        controller.updateRunned(getRunned());
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public synchronized void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
        controller.updateTaskStatus();
    }


    @Override
    public void finish() {
        setTaskStatus(TaskManage.TASK_STATUS_FINISHED);
    }

    @Override
    public boolean isStopped() {
        return taskStatus == TASK_STATUS_STOPPED;
    }

    @Override
    public void found(Info info) {
        if (info.getType() == Info.TYPE_PC) {
            pcBingo += 1;
        } else {
            mobileBingo += 1;
        }
        controller.addResult(info);

    }

    public void setRunned(int runned) {
        this.runned = runned;
    }

    public int getPcBingo() {
        return pcBingo;
    }

    public void setPcBingo(int pcBingo) {
        this.pcBingo = pcBingo;
    }

    public int getMobileBingo() {
        return mobileBingo;
    }

    public void setMobileBingo(int mobileBingo) {
        this.mobileBingo = mobileBingo;
    }

    public void stopAll() {
        capture.getCapture().stopAll();
    }

    private void prepareForNew() {
        keyword.setCurrent(-1, -1, -1);
    }
}
