package Ywk.UserInterface.Service;

import Ywk.PageCheck.TaskManage;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

public class BgrdService extends ScheduledService<TaskManage> {
    @Override
    protected Task<TaskManage> createTask() {
        return null;
    }
}
