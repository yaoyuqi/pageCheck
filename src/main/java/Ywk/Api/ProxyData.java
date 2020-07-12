package Ywk.Api;

import java.util.List;

public class ProxyData {

    /**
     * status : 200
     * msg :
     * data : {"proxy":["xxxxx"]}
     */

    private int status;
    private String msg;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<String> proxy;

        public List<String> getProxy() {
            return proxy;
        }

        public void setPrefix(List<String> proxy) {
            this.proxy = proxy;
        }
    }
}
