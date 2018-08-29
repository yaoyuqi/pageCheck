package Ywk.Data;

import Ywk.Api.HltApi;
import Ywk.Api.Result;
import Ywk.PageCheck.TaskManage;
import com.google.common.collect.Lists;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ApiWriter extends XMLWriter {

    private static final int STATUS_RUNNING = 1;
    private static final int STATUS_OK = 2;
    private static final int STATUS_FAIL = 3;


    private String mark;
    private String date;

    private TaskManage handler;


    private List<Integer> apiResultPc = new LinkedList<>();
    private List<Integer> apiResultMobile = new LinkedList<>();

    public ApiWriter() {
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat ft2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mark = ft.format(new Date());
        date = ft2.format(new Date());
    }

    public ApiWriter(String mark, String date) {
        this.mark = mark;
        this.date = date;
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
        if (info.getType() == Info.TYPE_PC) {
            listPc.add(info);
        } else {
            listMobile.add(info);
        }
    }

    @Override
    public synchronized void flush(int type) {
        if (listPc.isEmpty() && listMobile.isEmpty()) {
            handler.setTaskStatus(handler.getTaskStatus() == TaskManage.TASK_UPLOAD_UNFINISHED_RUNNING ?
                    TaskManage.TASK_UPLOAD_UNFINISHED_FINISHED
                    : TaskManage.TASK_UPLOAD_FINISHED_FINISHED);

            return;
        }
        if (type == Info.TYPE_PC) {
            apiResultPc.clear();
            chunkAndSend(listPc, Info.TYPE_PC, mark, date);
        } else {
            apiResultMobile.clear();
            chunkAndSend(listMobile, Info.TYPE_MOBILE, mark, date);
        }
    }

    private void chunkAndSend(List<Info> all, int type, String mark, String date) {
        HltApi api = HltApi.getInstance();
        List<List<Info>> chunks = Lists.partition(all, CHUNK_TO_WRITE_MAX);
        for (int i = 0; i < chunks.size(); i++) {
            if (type == Info.TYPE_PC) {
                apiResultPc.add(i, STATUS_RUNNING);
            } else {
                apiResultMobile.add(i, STATUS_RUNNING);
            }
            Result result = newResult(type, i + 1, mark, date);
            for (Info info :
                    chunks.get(i)) {
                List<Result.Data> data = result.getData();
                Result.Data item = new Result.Data();
                item.setLoc(info.getLocString());
                item.setTime(info.getTime());
                item.setWord(info.getKeyword());
                item.setPage(info.getPage());
                data.add(item);
            }
            result.setTotal(result.getData().size());
            api.upload(result, this);
        }
    }

    public synchronized void handleResult(int type, int part, boolean isOk) {
        if (type == Info.TYPE_PC) {
            apiResultPc.set(part - 1, isOk ? STATUS_OK : STATUS_FAIL);
        } else {
            apiResultMobile.set(part - 1, isOk ? STATUS_OK : STATUS_FAIL);
        }

        boolean finished = true;

        for (int s :
                apiResultPc) {
            if (s == STATUS_RUNNING) {
                finished = false;
                break;
            }
        }

        for (int s :
                apiResultMobile) {
            if (s == STATUS_RUNNING) {
                finished = false;
                break;
            }
        }

        if (finished) {
            boolean ok = true;
            for (int s : apiResultPc) {
                if (s == STATUS_FAIL) {
                    ok = false;
                    break;
                }
            }

            if (ok) {
                for (int s : apiResultMobile) {
                    if (s == STATUS_FAIL) {
                        ok = false;
                        break;
                    }
                }
            }

            if (!ok) {
                handler.uploadFail();
            } else {
                handler.uploadSuccess();
            }
        }
//        System.out.println("current part=" + part);
//
//        String log = "pc ==";
//        for (int s :
//                apiResultPc) {
//            log += s + " ";
//        }
//
//        log += " mobile==";
//        for (int s :
//                apiResultMobile) {
//            log += s + " ";
//        }
//        System.out.println(log);

    }

    private Result newResult(int type, int part, String mark, String date) {
        Result result = new Result();
        result.setCheck_time(date);
        result.setMark(mark);
        result.setType(type);
        result.setPart(part);
        result.setData(new ArrayList<>());
        return result;
    }


}
