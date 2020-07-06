package Ywk.Client.RequestBuilder;

import Ywk.Client.UserAgents;
import okhttp3.Request;

public class BaiduBuilder extends AbstractBuilder {


    public Request build(String url) {
        return new Request.Builder()
                .get()
                .url(url)
                .header("Cookie", "AIDUID=2A64D013895E90B23705652F8854A289:FG=1; PSTM=1570589428; BIDUPSID=94C23CF6E223E5C2AE01270CC928E79C; BDUSS=kdrVEdWZEVOT05WRjM1WnQ5ZHZnM0V2OHF2Nm0zMVhCSk9hanpWWFVtcH5Zb3BlRVFBQUFBJCQAAAAAAAAAAAEAAAAwCXMxz8nIy9XGNDcxAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH~VYl5~1WJeT; MSA_WH=320_568; H_WISE_SIDS=144017_142209_144238_143377_143877_144367_141910_100805_139964_142113_143860_143879_141875_139057_141744_143161_143423_144419_141899_142780_144483_131247_137746_138883_141941_127969_142871_140066_140593_143059_143491_140350_141009_143469_143923_131423_144289_143709_144006_125695_107312_138595_144113_143477_142427_142911_136753_110085; BD_UPN=123253; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; BDSFRCVID=gH0OJeC62mf7fgcuprcqMBkgnHqOK7jTH6aoo_Gk6eKH-Sudqc6PEG0PSM8g0KAb9Bq5ogKKXgOTHw0F_2uxOjjg8UtVJeC6EG0Ptf8g0f5; H_BDCLCKID_SF=tb4f_DI2tIL3qnnpK44V5bD_-UoHet6-bITKQbK8Kb7Vbn3SDMnkbfJBDxcht-5MK5TjQJOhBloOenrSKfnvjPI7yajK2-Rd3K0q0lvdHxOhe-jnjl3pQT8r0PFOK5Oi0Cu85tDaab3vOUnTXpO15T8zBN5thURB2DkO-4bCWJ5TMl5jDh3Mb6ksDMDtqtJHKbDHVCDKJMK; BDRCVFR[feWj1Vr5u3D]=mk3SLVN4HKm; delPer=0; BD_CK_SAM=1; PSINO=1; H_PS_PSSID=31905_1443_31325_32140_32046_32231_31708_32109_22157; COOKIE_SESSION=193_0_5_9_0_2_0_0_4_2_0_1_0_0_0_0_0_0_1593760060%7C9%2311669096_19_1593758802%7C7; H_PS_645EC=6c32gYpM4Pp4KBpCrgOMhpFQt1vzUYvH4TBqFUVuyaH%2FOOWio6dLwcGGU2Jzrkq%2FAljN")
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
