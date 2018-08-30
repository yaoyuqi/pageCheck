package Ywk.PageCheck;


import Ywk.Api.ApiErrorException;
import Ywk.Api.HltApi;
import Ywk.Api.WordData;
import Ywk.Data.ApiWriter;
import Ywk.Data.Info;
import Ywk.Data.Keyword;
import Ywk.UserInterface.Controller.HomeController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TaskManage implements Runnable
        , PageCapture.RunningUpdateListener
        , CheckManage.TaskFinishListener
        , BaiduChecker.CheckResult {
    public static final int TASK_STATUS_NEW = 0;
    public static final int TASK_STATUS_NEW_RUNNING = 1;
    public static final int TASK_STATUS_RESUME_RUNNING = 7;
    public static final int TASK_STATUS_RESUME = 2;
    public static final int TASK_STATUS_STOPPED = 3;
    public static final int TASK_STATUS_FINISHED = 4;
    public static final int TASK_UPLOAD_RUNNING = 5;
    public static final int TASK_UPLOAD_UNFINISHED_RUNNING = 8;
    public static final int TASK_UPLOAD_UNFINISHED_FINISHED = 9;
    public static final int TASK_UPLOAD_FINISHED_RUNNING = 10;
    public static final int TASK_UPLOAD_FINISHED_FINISHED = 11;
    public static final int TASK_UPLOAD_FINISHED = 6;

    static final int SPEED_RATE = 20;

    private int type = Info.TYPE_PC;

    private int speed = 1;

    private int pageDepth = 1;

    private String identity;

    private Keyword keyword;

    private CheckManage manage;

    private int total = 0;

    private int pcBingo = 0;
    private int mobileBingo = 0;

    private int runned = 0;

    private boolean autoUpdate = false;

    private volatile int taskStatus;

    private BaiduCapture capture;

    private HomeController controller;

    private ApiWriter writer;

    public void setAutoUpdate(boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
    }

    public TaskManage() {
    }

    public void setPageDepth(int pageDepth) {
        this.pageDepth = pageDepth;
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

    public void setRunned(int runned) {
        this.runned = runned;
    }

    private void incrementRunned() {
        this.runned++;
    }

    public void prepareKeyword() throws ApiErrorException {
        if (keyword == null) {
//            String[] prefix = {"成都", "重庆", "肯尼亚"};
//            String[] main = {"CCIC", "CCIB", "CCIA"};
//            String[] suffix = {"怎么样", "如何"};
            keyword = new Keyword();
            HltApi api = HltApi.getInstance();

            api.words(this);
        }


    }

    public int getTotal() {
        return keyword.getTotal() * pageDepth * platFormSelectedRate();
    }

    private void prepareCheckTool() {
        if (capture == null || manage == null || writer == null) {
//            XMLWriter writer = new XMLWriter();
            writer = new ApiWriter();
            writer.setHandler(this);
            BaiduChecker checker = new BaiduChecker(identity, writer);
//            capture = new BaiduCapture(checker, getRunningSpeed());
            capture = new BaiduCapture(checker, controller.getClient());
            capture.setCheckDepth(pageDepth);
            checker.setResultListener(this);

            PageCapture pageCapture = capture.getCapture();
            pageCapture.setListener(this);

            manage = new CheckManage(keyword, capture, type);
            capture.setIdleHandler(manage);

            manage.setListener(this);
        } else {
            manage.setType(type);
            capture.setCheckDepth(pageDepth);
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
        return speed * SPEED_RATE;
    }

    /**
     * 爬词
     */
    @Override
    public void run() {
        /*
            如果keyword为空，则初始化失败。
            让用户直接重新打开
         */
        if (keyword == null) {
            controller.alertNetError();
            return;
        }

        //准备工具
        prepareCheckTool();

        //对于重新开始的，初始化keyword的起始值
        if (taskStatus == TASK_STATUS_NEW_RUNNING) {
            prepareForNew();
        }

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

    /**
     * 爬取完成 回调
     */
    @Override
    public void finish() {
        setTaskStatus(TaskManage.TASK_STATUS_FINISHED);
        //自动上传
        if (autoUpdate) {
            setTaskStatus(TaskManage.TASK_UPLOAD_FINISHED_RUNNING);
            uploadResult();
        }
    }

    /**
     * 当前是否是停止状态
     *
     * @return boolean
     */
    @Override
    public boolean isStopped() {
        return taskStatus == TASK_STATUS_STOPPED
                || taskStatus == TASK_UPLOAD_FINISHED_FINISHED
                || taskStatus == TASK_UPLOAD_UNFINISHED_FINISHED
                || taskStatus == TASK_UPLOAD_UNFINISHED_RUNNING
                || taskStatus == TASK_UPLOAD_FINISHED_RUNNING
                || taskStatus == TASK_STATUS_FINISHED;
    }

    /**
     * 回调 检索出结果
     * @param info
     */
    @Override
    public void found(Info info) {
        if (info.getType() == Info.TYPE_PC) {
            pcBingo += 1;
        } else {
            mobileBingo += 1;
        }
        controller.addResult(info);

    }

    /**
     * 上传结果
     */
    public void uploadResult() {

        if (writer != null) {
            writer.flush(Info.TYPE_PC);
            writer.flush(Info.TYPE_MOBILE);
        } else {
            setTaskStatus(taskStatus == TaskManage.TASK_UPLOAD_UNFINISHED_RUNNING ?
                    TaskManage.TASK_UPLOAD_UNFINISHED_FINISHED
                    : TaskManage.TASK_UPLOAD_FINISHED_FINISHED);
        }

    }

    /**
     * 上传成功回调
     */
    public void uploadSuccess() {
        setTaskStatus(taskStatus == TaskManage.TASK_UPLOAD_UNFINISHED_RUNNING ?
                TaskManage.TASK_UPLOAD_UNFINISHED_FINISHED
                : TaskManage.TASK_UPLOAD_FINISHED_FINISHED);
        controller.uploadSuccess();
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

    /**
     * 停止任务
     */
    public void stopAll() {
        capture.getCapture().stopAll();
    }

    /**
     * 对于重新开始的，初始化keyword的起始值
     */
    private void prepareForNew() {
        if (keyword != null) {
            keyword.setCurrent(-1, -1, -1);
            keyword.setCurRun(0);
        }

        if (manage != null) {
            manage.setFinished(false);
        }


    }

    /**
     * 回调 上传失败
     */
    public void uploadFail() {
        setTaskStatus(taskStatus == TaskManage.TASK_UPLOAD_UNFINISHED_RUNNING ?
                TaskManage.TASK_STATUS_STOPPED
                : TaskManage.TASK_STATUS_FINISHED);

        controller.alertUploadError();
    }

    /**
     * 清除数据
     */
    public void clearData() {
        if (writer != null) {
            writer.clear();

            SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat ft2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            writer.setMark(ft.format(new Date()));
            writer.setDate(ft2.format(new Date()));


        }


    }

    public void initKeyword(WordData wordData) {
        if (wordData == null) {
            controller.alertVital();
        } else {
            if (keyword == null) {
                keyword = new Keyword();
            }
            keyword.setWords(wordData.getData().getPrefix().toArray(new String[]{}),
                    wordData.getData().getMain().toArray(new String[]{}),
                    wordData.getData().getSuffix().toArray(new String[]{}));
//            String[] prefix = {"成都", "重庆", "肯尼亚"};
//            String[] main = {"CCIC", "CCIB", "CCIA"};
//            String[] suffix = {"怎么样", "如何"};
//            keyword = new Keyword(prefix, main, suffix);
            controller.updateTotal();
        }
    }

    public void netError() {
        controller.alertNetError();
    }

    public void setCustomKeywords(List<String> e) {
        keyword.setCustom(e.toArray(new String[]{}));
    }

    public void setKeywordType(Keyword.MixType type) {
        keyword.setType(type);
    }

    public void setKeywordMax(int max) {
        keyword.setMaxRun(max);
    }

    private int platFormSelectedRate() {
        if (type == Info.TYPE_BOTH) {
            return 2;
        } else if (type == Info.TYPE_PC
                || type == Info.TYPE_MOBILE) {
            return 1;
        }
        return 0;
    }
}
