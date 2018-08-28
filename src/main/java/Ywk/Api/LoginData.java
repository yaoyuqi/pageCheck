package Ywk.Api;

public class LoginData {

    /**
     * token_type : Bearer
     * expires_in : 31536000
     * access_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjQ2YzFmZjIyMmM2NDMzZjIyODA0NDk0YzgwNzllODNmYTBlNWNjOTZiOWUxYjIxYzAxNjFlMmJjOGM5ZGNiYTQ5N2M1YzM0MmRkNzhkNjZhIn0.eyJhdWQiOiIyIiwianRpIjoiNDZjMWZmMjIyYzY0MzNmMjI4MDQ0OTRjODA3OWU4M2ZhMGU1Y2M5NmI5ZTFiMjFjMDE2MWUyYmM4YzlkY2JhNDk3YzVjMzQyZGQ3OGQ2NmEiLCJpYXQiOjE1MzU0MjY5NDYsIm5iZiI6MTUzNTQyNjk0NiwiZXhwIjoxNTY2OTYyOTQ2LCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.jOHk-DGrCFV4w5HWWUXyd0Babez9wQq_ps9mJF2WH_pCoWQRcIxChC5ryZjXCwAD-TzoY95D5ykl6Q0fYLEfS6eUhTvl3QsR7Gm_ALgrJqzz53XUW4PkNuoxk5Z48okk1dU-j5nNsuUUv39bJd5JdVElE3uaAmHjooPFWkIzSQz7P2_Y76TWlBSTfIMgd4R93lLXmS_JAa4Gs0k8HwP36yxaUIJ4Qfe5ECHSXp1YM2RNvGrC00upCRoikKzgsZJoBD53Hy_BatwdNa0GFgmyJBt3NqUvs-4qEn1Y_xgAA-wmDXpRwc_c7rhWPWyrxiONoVqAb9z_aQtkbzzISgrq_3MLe9BoOTslPOduKyBtd3KJvi1FRbuWpzc8yW_oya_Z7pAYWDi_QMCvk8gYSFpQIRZwqWCLnxCfodvRwV_naVm0iMRWf-w0EX_3FXN58GQVa686YIezM3duI2wUNBEKfgJFtDAYda65C-pHF9fw0nnQOBDpGtGekDqimlPHgBB0y61wEACCMT-EZEJXHvKt4BmeFs7CV9qUrag8_WvXkIYGKzt3VrHzGmcLF8s__d8owvrnmFE6VqS4_dP4FZFl-itRP-3CkHargG8f1T-T8eCfUdFBt8EhT-EykgM71xmYMumRAQGUJ6pxmsTgEq1fvaFInEQc6Mo94h2cTlSSP9o
     * refresh_token : def50200bc57f3a72dd6a388659d2674cdee9b4660791668cb347655d5813f76de0729092f7ae30af625d1020ba71460eb51c5a99e1b6eeb914f7ad102b89858338742bfaaab1fbeea0b6ec4e18f76a56bba07707589848b4a161cb8945d23d21b537cbe5b53ba27123684219081ea2a24f590fe488d61bc4eef0abcb2df7c21583fabe796112a8dc1c97f6241e7ffd565c66cc837db37aa2b54a8eb4331c7064ac4be25549aacaf2cb0ace7b3b83813a3e10c6b630c3347f4e3c4a3b8c4bbb35613abe4c747eb5b6e67f64d346098c4fedd3713698b8bdc7dcc64092d5dd4d5ea5453856f7eecbac3cf611a4df1a636ad738f35a57c956a5074552360296d604dc393af23889e9489a87d5a490dbddb2689ac51695c5df8a97b7cbcc4783ba91b3423ace68198f585a53c0883e4c10694d761ca3fc42c2d7ed20f46806b61c3ada6060e4acae9ac2d56bdba24e4af9b7311ba0010778bb94a6b47a5ac9c42df85
     */

    private String token_type;
    private int expires_in;
    private String access_token;
    private String refresh_token;

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
