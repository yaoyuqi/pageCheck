package Ywk.PageCheck;


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
    public static final int TASK_STATUS_PAUSE = 2;
    public static final int TASK_STATUS_STOPPED = 3;
    public static final int TASK_STATUS_FINISHED = 4;


    private int type = Info.TYPE_PC;

    private int speed = 1;

    private String identity;

    private Keyword keyword;

    private CheckManage manage;

    private int total = 0;

    private int runned = 0;

    private volatile int taskStatus;

    private HomeController controller;

    public TaskManage(String identity) {
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
        String[] prefix = {"成都", "重庆", "肯尼亚"};
        String[] main = {"CCIC", "CCIB", "CCIA"};
        String[] suffix = {"怎么样", "如何"};

//        setTaskStatus(TaskManage.TASK_STATUS_NEW);

        keyword = new Keyword(prefix, main, suffix);

        controller.setTotal(keyword.getTotal());
    }


    public int getTotal() {
        return total;
    }

    private void prepareCheckTool() {
        XMLWriter writer = new XMLWriter();
        BaiduChecker checker = new BaiduChecker(identity, writer);
        BaiduCapture capture = new BaiduCapture(checker, getRunningSpeed());
        checker.setResultListener(this);

        PageCapture pageCapture = capture.getCapture();
        pageCapture.setListener(this);

        manage = new CheckManage(keyword, capture, writer, type);
        capture.setIdleHandler(manage);

        manage.setListener(this);
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
        return speed * 2;
    }


    @Override
    public void run() {
        setTaskStatus(TaskManage.TASK_STATUS_RUNNING);
        prepareKeyword();
        prepareCheckTool();

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
    public void found(Info info) {
        controller.addResult(info);
    }
}
