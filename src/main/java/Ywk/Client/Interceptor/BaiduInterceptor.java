package Ywk.Client.Interceptor;

import okhttp3.Request;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BaiduInterceptor implements PageEncrypt {
    private static final int MAX_LENGTH = 100;
    private static final int USE_LIMIT = 10;
    private List<String> keys = new LinkedList<>();

    private int used = 0;

//    public static void main(String[] args) {
//        String content = "bds.comm.rightResultExist = false;\n" +
//                "    \tbds.comm.protectNum = 0;\n" +
//                "    \tbds.comm.zxlNum = 0;\n" +
//                "        bds.comm.pageNum = parseInt('2')||1;\n" +
//                "\n" +
//                "\t\t\n" +
//                "        bds.comm.pageSize = parseInt('10')||10;\n" +
//                "\tbds.comm.encTn = '2b630M8M0TQNX5OvSPPfdeaEfmreUq1+mYhZlh0b76H03lNAtjWUON59S6ovUq25SPlv';\t\t\n" +
//                "        bds.se.mon = {'loadedItems':[],'load':function(){},'srvt':-1};\n" +
//                "        try {\n" +
//                "            bds.se.mon.srvt = parseInt(document.cookie.match(new RegExp(\"(^| )BDSVRTM=([^;]*)(;|$)\"))[2]);\n" +
//                "            document.cookie=\"BDSVRTM=;expires=Sat, 01 Jan 2000 00:00:00 GMT\";\n" +
//                "        }catch(e){\n" +
//                "            bds.se.mon.srvt=-1;\n" +
//                "        }\n" +
//                "\n" +
//                "        bdUser        = bds.comm.user?bds.comm.user:null;\n" +
//                "        bdQuery       = bds.comm.query;\n" +
//                "        bdUseFavo     = bds.comm.useFavo;\n" +
//                "        bdFavoOn      = bds.comm.favoOn;\n" +
//                "        bdCid         = bds.comm.cid;";
//
//
//        BaiduAdapter adapter = new BaiduAdapter();
////        adapter.parse(content);
//    }

    public void parse(String content) {
        Pattern pattern = Pattern.compile("bds\\.comm\\.encTn\\s*=\\s*'(\\S+)'");

        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String key = matcher.group(1);

            if (!key.isEmpty()) {
                keys.add(key);
            }

        }

        if (keys.size() > MAX_LENGTH) {
            keys = keys.subList(keys.size() - MAX_LENGTH, keys.size());
        }
//        System.out.println(keys.toString());


    }

    public Request process(Request origin) {

        String cookie = origin.header("Cookie");

        if (keys.isEmpty()) {
            return origin;
        }

        if (cookie != null) {
            if (used > USE_LIMIT && keys.size() > 1) {
                keys.remove(0);
                used = 0;
            }

            String key = keys.get(0);

//
//            System.out.println("********************");
//            System.out.println(key);
//            System.out.println("oldCookie********************");
//            System.out.println(cookie);
            if (!key.isEmpty()) {
                List<String> cookies = Arrays.stream(cookie.split(";"))
                        .filter(item -> !item.contains("H_PS_645EC"))
                        .collect(Collectors.toList());

                try {
                    cookies.add(" H_PS_645EC=" + URLEncoder.encode(key, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
//                cookies.add(" H_PS_645EC=" + key);

                used++;
                return origin.newBuilder()
                        .header("Cookie", String.join(";", cookies))
                        .build();

            }

        }

        return origin;
    }

    @Override
    public boolean canHandle(URI uri) {
        try {
            return uri.getHost().equals(new URI("https://www.baidu.com/").getHost());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }
}
