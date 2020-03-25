package Ywk.Client.RequestBuilder;

import Ywk.Client.UserAgents;
import okhttp3.Request;

public class BaiduBuilder extends AbstractBuilder {


    public Request build(String url) {
        return new Request.Builder()
                .get()
                .url(url)
                .header("Cookie", "BAIDUID=2A64D013895E90B23705652F8854A289:FG=1; PSTM=1570589428; BIDUPSID=94C23CF6E223E5C2AE01270CC928E79C; MSA_WH=375_667; BD_UPN=123253; BDUSS=kdrVEdWZEVOT05WRjM1WnQ5ZHZnM0V2OHF2Nm0zMVhCSk9hanpWWFVtcH5Zb3BlRVFBQUFBJCQAAAAAAAAAAAEAAAAwCXMxz8nIy9XGNDcxAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH~VYl5~1WJeT; H_WISE_SIDS=142080_142058_142113_100806_139964_143377_142209_143860_143879_141876_139057_141747_143161_143423_143448_141899_142780_131247_137746_138165_138883_141941_127969_142871_140066_142908_140593_143059_143491_140350_138425_141009_143469_143276_141930_131423_125695_107316_138595_143093_142803_143477_142427_142911_136753_110085; H_PS_PSSID=30975_1443_31117_21091_30903_30823_31086_22157; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; delPer=0; BD_CK_SAM=1; PSINO=1; H_PS_645EC=25c2xT4EdDqVS9weTiheh3jDLv33l9FYqfjdFTLm2UqJ6%2BfOcOju7qYHT24; COOKIE_SESSION=5_0_9_9_0_3_0_1_9_1_2_2_0_0_0_0_0_0_1584687345%7C9%238805509_15_1582089706%7C6")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
//                    .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accept-Language", "en,zh-CN;q=0.9,zh;q=0.8")
//                .header("User-Agent:", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36")
                .header("User-Agent:", UserAgents.randomUserAgent(false))
                .build();
    }

    @Override
    public String host() {
        return "https://www.baidu.com/";
    }


}
