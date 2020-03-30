package Ywk.Client.RequestBuilder;

import Ywk.Client.UserAgents;
import okhttp3.Request;

public class BaiduMBuilder extends AbstractBuilder {
    @Override
    public String host() {
        return "https://m.baidu.com";
    }

    @Override
    public Request build(String url) {
        return new Request.Builder()
                .get()
                .url(url)
                .header("Cookie", "BAIDUID=2A64D013895E90B23705652F8854A289:FG=1; PSTM=1570589428; BIDUPSID=94C23CF6E223E5C2AE01270CC928E79C; BDUSS=kdrVEdWZEVOT05WRjM1WnQ5ZHZnM0V2OHF2Nm0zMVhCSk9hanpWWFVtcH5Zb3BlRVFBQUFBJCQAAAAAAAAAAAEAAAAwCXMxz8nIy9XGNDcxAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH~VYl5~1WJeT; MSA_WH=320_568; MSA_PBT=146; MSA_ZOOM=1056; COOKIE_SESSION=8462_0_0_8_1_w1_8_8_1_0_0_2_19_1585030483%7C9%230_0_0_0_0_0_0_0_1585020624%7C1; FC_MODEL=-1_0_5_0_0_0_1_0_0_0_0_-1_8_8_8_4_0_1585125636673_1585030483942%7C9%230_-1_-1_8_8_1585125636673_1585030483942%7C9; H_PS_PSSID=1443_31117_21091_31187_30903_30823_31086_22157; H_WISE_SIDS=144017_142209_144238_143377_143877_144367_141910_100805_139964_142113_143860_143879_141875_139057_141744_143161_143423_144419_141899_142780_144483_131247_137746_138883_141941_127969_142871_140066_140593_143059_143491_140350_141009_143469_143923_131423_144289_143709_144006_125695_107312_138595_144113_143477_142427_142911_136753_110085; rsv_i=b18ciFJnYAFekK%2Foxp1FBHtp3v3h2fZmSdoPAidmTTmZDq6MJ6Rj%2BHn%2BBxLc86VnLtLLJiWuJCK5nJ6vVIMki4XB8RdoEjI; BDSVRBFE=Go; BDSVRTM=42; plus_lsv=f197ee21ffd230fd; BDORZ=AE84CDB3A529C0F8A2B9DCDD1D18B695; plus_cv=1::m:49a3f4a6; Hm_lvt_12423ecbc0e2ca965d84259063d35238=1585548164; Hm_lpvt_12423ecbc0e2ca965d84259063d35238=1585548164; __bsi=11987481358287502930_00_48_N_R_20_0303_c02f_Y; SE_LAUNCH=5%3A26425802_14%3A26425802")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
//                    .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accept-Language", "en,zh-CN;q=0.9,zh;q=0.8")
//                .header("User-Agent:", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36")
                .header("User-Agent:", UserAgents.randomUserAgent(true))
                .build();


    }
}
