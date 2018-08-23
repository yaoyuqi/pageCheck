package Ywk.PageCheck;

import java.util.concurrent.Callable;

public class TaskManage implements Callable<Boolean> {

    private CheckManage manage;

    public TaskManage(CheckManage manage) {
        this.manage = manage;
    }

    @Override
    public Boolean call() throws Exception {
        while (!manage.isFinished());
        return manage.isFinished();
    }
}
