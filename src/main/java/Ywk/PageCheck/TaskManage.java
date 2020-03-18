package Ywk.PageCheck;


import Ywk.Api.HttpClientWrapper;
import Ywk.Data.*;
import Ywk.PageCheck.Capture.PageRunner;
import Ywk.PageCheck.Capture.PageSpider;
import Ywk.UserInterface.Controller.HomeController;

import java.text.SimpleDateFormat;
import java.util.*;

public class TaskManage implements Runnable
        , PageSpider.PageChecked
        , RunningContainer.TaskRunningFinishListener
        , ContentChecker.NewItemFound {

    /**
     * 速度
     */
    private int speed = 1;

    /**
     * 页面检索深度
     */
    private int pageDepth = 1;

    /**
     * 产品列表
     */
    private List<String> identities;

    private KeywordGenerator generator;

    private RunningContainer runningContainer;

    /**
     * 已检索数
     */
    private int ranCnt = 0;

    /**
     * 是否自动上传
     */
    private boolean autoUpdate = false;

    /**
     * 跑词状态
     */
    private volatile TaskStatus taskStatus;

    /**
     * 上传状态
     */
    private volatile UploadStatus uploadStatus;
    private HomeController controller;
    private ApiWriter writer;
    /**
     * 遍历器
     */
    private List<PageRunner> runners;

    private Map<Integer, Integer> bingoCnt = new HashMap<>();


    public TaskManage(HomeController controller) {
        writer = new ApiWriter();
        writer.setHandler(this);
        this.controller = controller;
//        String[] prefix = {"成都", "重庆", "肯尼亚"};
//            String[] main = {"CCIC", "CCIB", "CCIA"};
//            String[] suffix = {"怎么样", "如何"};
//            generator = new KeywordGenerator(prefix, main, suffix);
        generator = KeywordGenerator.getInstance();
        identities = IdentityWrapper.getInstance().identities();
        initRunners();
        prepareCheckTool();
        runningContainer.setTaskFinishedListener(this);

        for (SearchPlatform platform : SearchPlatform.values()) {
            bingoCnt.put(platform.getId(), 0);
        }


    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public UploadStatus getUploadStatus() {
        return uploadStatus;
    }

    public void setAutoUpdate(boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
    }

    public void setPageDepth(int pageDepth) {
        this.pageDepth = pageDepth;
    }


    public int getTotal() {
        return generator.getTotal() * pageDepth * platFormSelectedRate();
    }

    private void initRunners() {
        runners = new ArrayList<>();

        SearchPlatform[] platforms = SearchPlatform.values();

        for (SearchPlatform platform :
                platforms) {
            runners.add(new PageRunner(new PageSpider(HttpClientWrapper.getClient(), new ContentChecker(identities, writer, platform, this))));
        }

        runners.forEach(pageRunner -> {
            pageRunner.setPageCheckedListener(this);
        });


    }

    private void updateRunnerConfig() {
        runners.forEach(absRunner -> {
            absRunner.setCheckDepth(pageDepth);
            absRunner.setSpeed(speed);
        });
    }

    private void prepareCheckTool() {

        updateRunnerConfig();

        runningContainer = new RunningContainer(generator, runners);
        runners.forEach(absRunner -> {
            absRunner.setIdleHandler(runningContainer);
        });

    }

    public void setSpeed(int speed) {
        this.speed = speed;
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
        if (generator == null) {
            controller.alertNetError();
            return;
        }

        //对于重新开始的，初始化keyword的起始值
        if (taskStatus == TaskStatus.NEW) {
            prepareForNew();
        }

        runningContainer.run();

    }

    @Override
    public void updateRunningInfo() {
        ranCnt++;
        controller.updateRanCnt(ranCnt);
    }


    public synchronized void start() {
        this.taskStatus = TaskStatus.RUNNING;
        controller.updateTaskStatus();
    }

    /**
     * 爬取完成 回调
     */
    @Override
    public void pageCheckFinish() {
        taskStatus = TaskStatus.FINISHED;
        //自动上传
        if (autoUpdate) {
            uploadStatus = UploadStatus.UPLOADING;
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
        return taskStatus != TaskStatus.RUNNING;
    }

    /**
     * 上传结果
     */
    public void uploadResult() {
        if (writer != null && (uploadStatus == UploadStatus.WAITING
                || uploadStatus == UploadStatus.FAIL)) {
            uploadStatus = UploadStatus.UPLOADING;
            writer.flush();
        }
    }

    /**
     * 上传成功回调
     */
    public void uploadSuccess() {
        uploadStatus = UploadStatus.SUCCESS;
        controller.uploadSuccess();
    }


    /**
     * 停止任务
     */
    public void stopAll() {
        taskStatus = TaskStatus.PAUSE;
        runners.forEach(PageRunner::stop);

    }

    public void newTask() {
        prepareForNew();
        taskStatus = TaskStatus.NEW;
    }

    /**
     * 对于重新开始的，初始化keyword的起始值
     */
    private void prepareForNew() {
        if (generator != null) {
            generator.setCurrent(-1, -1, -1);
            generator.setCurRun(0);
        }

        if (runningContainer != null) {
            runningContainer.setFinished(false);
        }

        ranCnt = 0;

        clearData();

    }

    /**
     * 回调 上传失败
     */
    public void uploadFail() {
        uploadStatus = UploadStatus.FAIL;
        controller.alertUploadError();
    }

    /**
     * 清除数据
     */
    private void clearData() {
        if (writer != null) {
            writer.clear();

            SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat ft2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            writer.setMark(ft.format(new Date()));
            writer.setDate(ft2.format(new Date()));
        }
    }


    public void setCustomKeywords(List<String> e) {
        generator.setCustom(e.toArray(new String[]{}));
    }

    public void setKeywordType(KeywordGenerator.MixType type) {
        generator.setType(type);
    }

    public void setKeywordMax(int max) {
        generator.setMaxRun(max);
    }

    private int platFormSelectedRate() {

        return 1;
    }

    public void setAvailablePlatforms(List<SearchPlatform> platforms) {
        runningContainer.setAvailablePlatforms(platforms);
    }

    @Override
    public void found(Info info) {
        bingoCnt.put(info.getPlatform().getId(), bingoCnt.get(info.getPlatform().getId()) + 1);
    }
}
