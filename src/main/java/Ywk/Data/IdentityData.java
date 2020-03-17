package Ywk.Data;

import java.util.List;

public class IdentityData {

    /**
     * status : 200
     * msg :
     * data : [{"name":"海量发","identity":"0vk8gf"},{"name":"AI海量发","identity":"KTme70"},{"name":"新闻中心","identity":"rQDIXT"},{"name":"产品中心","identity":"qi9vVv"},{"name":"超级云站","identity":"wKlbYK"},{"name":"牛站","identity":"JuWytz"}]
     */

    private int status;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : 海量发
         * identity : 0vk8gf
         */

        private String name;
        private String identity;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdentity() {
            return identity;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }
    }
}
