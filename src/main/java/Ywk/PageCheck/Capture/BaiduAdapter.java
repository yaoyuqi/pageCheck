package Ywk.PageCheck.Capture;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaiduAdapter {

    private List<String> keys = new LinkedList<>();

    public static void main(String[] args) {
        String content = "bds.comm.rightResultExist = false;\n" +
                "    \tbds.comm.protectNum = 0;\n" +
                "    \tbds.comm.zxlNum = 0;\n" +
                "        bds.comm.pageNum = parseInt('2')||1;\n" +
                "\n" +
                "\t\t\n" +
                "        bds.comm.pageSize = parseInt('10')||10;\n" +
                "\tbds.comm.encTn = '2b630M8M0TQNX5OvSPPfdeaEfmreUq1+mYhZlh0b76H03lNAtjWUON59S6ovUq25SPlv';\t\t\n" +
                "        bds.se.mon = {'loadedItems':[],'load':function(){},'srvt':-1};\n" +
                "        try {\n" +
                "            bds.se.mon.srvt = parseInt(document.cookie.match(new RegExp(\"(^| )BDSVRTM=([^;]*)(;|$)\"))[2]);\n" +
                "            document.cookie=\"BDSVRTM=;expires=Sat, 01 Jan 2000 00:00:00 GMT\";\n" +
                "        }catch(e){\n" +
                "            bds.se.mon.srvt=-1;\n" +
                "        }\n" +
                "\n" +
                "        bdUser        = bds.comm.user?bds.comm.user:null;\n" +
                "        bdQuery       = bds.comm.query;\n" +
                "        bdUseFavo     = bds.comm.useFavo;\n" +
                "        bdFavoOn      = bds.comm.favoOn;\n" +
                "        bdCid         = bds.comm.cid;";


        BaiduAdapter adapter = new BaiduAdapter();
//        adapter.parse(content);
    }

    public void parse(String content, String url) {

        String key = "";
        Pattern pattern = Pattern.compile("bds\\.comm\\.encTn\\s*=\\s*'(\\S+)'");

        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
//            keys.add(matcher.group(1));
            key = matcher.group(1);

        }

//        if (!key.isEmpty()) {
//            try {
//                URI uri = new URI(url);
//                ConcurrentHashMap<String, List<Cookie>> store =  CookiesStore.getCookieStore();
//                List<Cookie> cookies = store.get(uri.getHost());
//                cookies.add(Cookie.parse(HttpUrl.get(uri), key));
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            }
//
//        }
        keys.add(key);
        System.out.println(keys.toString());


    }

    public String key() {
        return keys.isEmpty() ? "" : keys.remove(0);
    }
}
