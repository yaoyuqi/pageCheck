package Ywk.Data;

import java.util.List;

public class Config {

    /**
     * status : 200
     * msg :
     * data : {"pageMax":3,"platform":[{"id":1,"name":"百度PC","urls":["https://www.baidu.com/s?ie=utf-8&wd=[#keyword]","https://www.baidu.com/s?ie=utf-8&wd=[#keyword]&pn=10","https://www.baidu.com/s?ie=utf-8&wd=[#keyword]&pn=20"],"patter":"div.result.c-container","isMobile":false},{"id":2,"name":"百度移动","urls":["https://m.baidu.com/s?ie=utf-8&wd=[#keyword]","https://m.baidu.com/s?ie=utf-8&wd=[#keyword]&pn=10","https://m.baidu.com/s?ie=utf-8&wd=[#keyword]&pn=20"],"patter":"#results div.result.c-result","isMobile":true},{"id":3,"name":"神马搜索","urls":["https://m.sm.cn/s?q=[#keyword]","https://m.sm.cn/s?q=[#keyword]&page=2","https://m.sm.cn/s?q=[#keyword]&page=3"],"patter":"div.sc.c-container","isMobile":true},{"id":4,"name":"头条搜索","urls":["https://m.toutiao.com/search?keyword=[#keyword]","https://m.toutiao.com/search?keyword=[#keyword]&offset=10&start_index=10","https://m.toutiao.com/search?keyword=[#keyword]&offset=20&start_index=20"],"patter":"div.result.c-container","isMobile":false}]}
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
        /**
         * pageMax : 3
         * platform : [{"id":1,"name":"百度PC","urls":["https://www.baidu.com/s?ie=utf-8&wd=[#keyword]","https://www.baidu.com/s?ie=utf-8&wd=[#keyword]&pn=10","https://www.baidu.com/s?ie=utf-8&wd=[#keyword]&pn=20"],"patter":"div.result.c-container","isMobile":false},{"id":2,"name":"百度移动","urls":["https://m.baidu.com/s?ie=utf-8&wd=[#keyword]","https://m.baidu.com/s?ie=utf-8&wd=[#keyword]&pn=10","https://m.baidu.com/s?ie=utf-8&wd=[#keyword]&pn=20"],"patter":"#results div.result.c-result","isMobile":true},{"id":3,"name":"神马搜索","urls":["https://m.sm.cn/s?q=[#keyword]","https://m.sm.cn/s?q=[#keyword]&page=2","https://m.sm.cn/s?q=[#keyword]&page=3"],"patter":"div.sc.c-container","isMobile":true},{"id":4,"name":"头条搜索","urls":["https://m.toutiao.com/search?keyword=[#keyword]","https://m.toutiao.com/search?keyword=[#keyword]&offset=10&start_index=10","https://m.toutiao.com/search?keyword=[#keyword]&offset=20&start_index=20"],"patter":"div.result.c-container","isMobile":false}]
         */

        private int pageMax;
        private int version;
        private boolean force;
        private List<PlatformBean> platform;

        public int getPageMax() {
            return pageMax;
        }

        public void setPageMax(int pageMax) {
            this.pageMax = pageMax;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public boolean isForce() {
            return force;
        }

        public void setForce(boolean force) {
            this.force = force;
        }

        public List<PlatformBean> getPlatform() {
            return platform;
        }

        public void setPlatform(List<PlatformBean> platform) {
            this.platform = platform;
        }

        public static class PlatformBean {
            /**
             * id : 1
             * name : 百度PC
             * urls : ["https://www.baidu.com/s?ie=utf-8&wd=[#keyword]","https://www.baidu.com/s?ie=utf-8&wd=[#keyword]&pn=10","https://www.baidu.com/s?ie=utf-8&wd=[#keyword]&pn=20"]
             * patter : div.result.c-container
             * isMobile : false
             */

            private int id;
            private String name;
            private String patter;
            private boolean isMobile;
            private List<String> urls;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPatter() {
                return patter;
            }

            public void setPatter(String patter) {
                this.patter = patter;
            }

            public boolean isIsMobile() {
                return isMobile;
            }

            public void setIsMobile(boolean isMobile) {
                this.isMobile = isMobile;
            }

            public List<String> getUrls() {
                return urls;
            }

            public void setUrls(List<String> urls) {
                this.urls = urls;
            }
        }
    }
}
