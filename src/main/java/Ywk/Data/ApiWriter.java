package Ywk.Data;

import Ywk.Api.HltApi;
import Ywk.Api.Result;
import Ywk.PageCheck.TaskManage;
import com.google.common.collect.Lists;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ApiWriter extends XMLWriter {

    private static final int STATUS_RUNNING = 1;
    private static final int STATUS_OK = 2;
    private static final int STATUS_FAIL = 3;
    private String mark;
    private String date;
    private TaskManage handler;
    private int[] apiResult;

    public ApiWriter() {
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat ft2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mark = ft.format(new Date());
        date = ft2.format(new Date());
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHandler(TaskManage handler) {
        this.handler = handler;
    }

    @Override
    public synchronized void add(Info info) {
        list.add(info);
    }

    @Override
    public synchronized void flush() {
//        if (list.isEmpty()) {
//            handler.setTaskStatus(handler.getTaskStatus() == TaskManage.TASK_UPLOAD_UNFINISHED_RUNNING ?
//                    TaskManage.TASK_UPLOAD_UNFINISHED_FINISHED
//                    : TaskManage.TASK_UPLOAD_FINISHED_FINISHED);
//
//            return;
//        }
        chunkAndSend(list, mark, date);
    }

    private void chunkAndSend(List<Info> all, String mark, String date) {
        HltApi api = HltApi.getInstance();
        List<List<Info>> chunks = Lists.partition(all, CHUNK_TO_WRITE_MAX);

        apiResult = new int[chunks.size()];
        Arrays.fill(apiResult, STATUS_RUNNING);

        for (int i = 0; i < chunks.size(); i++) {
            Result result = new Result(chunks.get(i), i + 1, mark);
            api.upload(result, this);
        }
    }

    public synchronized void handleResult(int part, boolean isOk) {
        apiResult[part - 1] = isOk ? STATUS_OK : STATUS_FAIL;

        boolean finished = true;

        for (int s :
                apiResult) {
            if (s == STATUS_RUNNING) {
                finished = false;
                break;
            }
        }

        if (finished) {
            boolean ok = true;
            for (int s : apiResult) {
                if (s == STATUS_FAIL) {
                    ok = false;
                    break;
                }
            }

            if (!ok) {
                handler.uploadFail();
            } else {
                handler.uploadSuccess();
            }
        }

    }

}
