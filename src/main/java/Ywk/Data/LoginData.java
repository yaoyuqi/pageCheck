package Ywk.Data;

public class LoginData {

    /**
     * status : 200
     * msg :
     * data : {"token_type":"Bearer","expires_in":31536000,"access_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjIwY2EwMDU3YjgwYWNiOTFmZjMxZTkxZmRkNWY3MjFmNGIzZmU4MWIzNzg1OTc2NTAzMWQwYjZlOWQxYmY0ZmU3ZmQyZTBmZmM3OTFjNTQ5In0.eyJhdWQiOiIyIiwianRpIjoiMjBjYTAwNTdiODBhY2I5MWZmMzFlOTFmZGQ1ZjcyMWY0YjNmZTgxYjM3ODU5NzY1MDMxZDBiNmU5ZDFiZjRmZTdmZDJlMGZmYzc5MWM1NDkiLCJpYXQiOjE1MzU0NDk1MzUsIm5iZiI6MTUzNTQ0OTUzNSwiZXhwIjoxNTY2OTg1NTM1LCJzdWIiOiI5NiIsInNjb3BlcyI6WyJzaXRlLWFkbWluIl19.ZZ07C8FGCWg7oTRZgv7MPWEclVD2D1PJWtO-CIbkYFw7bFgXB4o6mqUMgfJkq-BY9o8JOweECogXj3c9njPn5qFfvvLZ3T1ZPhVkxI1X1atL4R04PootUbBhXZAPYk-X_HKlJtPz4ao7IGxQg0Fu9i_ZKodXeSZYS2yt9EqRe1l199axpK1DiiO-rW695FWOjJVuukV-G4CvvlYpPD2EYwh9WBdqKhDL1BN--RzaY8Gkoe-T7OvPIMdMacjX612wKVoLNwcEIszGrXuQrgT07FPwixuc9M0eH_PHvfqveBovK8OuO8-h_NUqXGMPMSAs5kyfiXC_HnqXXyibEhKK_U4uME3IKRQAn2CYh5nEUDWHxHKckY5hEa6z1uc89eeeYVT33vDYZe_tTv3eeCpF0mRF3_H4MZ-yJKug8IUxYvlqXBqe5OSo9_ap_FEwqIf3xUuxHf-sfVq9C2y5ShRXOsynN52BS5KccRo__6YHS5rFMvHp3U5h7q5xCTjwp1sE5FbqZ7NtHKucj22ZZcsu0-YH6VG4-Am1yZHWUprTtF77eg-x_5zQL_UmmHTqfBzcqrAAly2jj762DLxytnajOESldnF_o9iok29YfF99LslLbtdcMQD_ZyDA3E4Fy7ZotBCtu9kgwAYlSJU3JGBJFWvbeQHO7aXxAtm-g_cpF7I","refresh_token":""}
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
         * token_type : Bearer
         * expires_in : 31536000
         * access_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjIwY2EwMDU3YjgwYWNiOTFmZjMxZTkxZmRkNWY3MjFmNGIzZmU4MWIzNzg1OTc2NTAzMWQwYjZlOWQxYmY0ZmU3ZmQyZTBmZmM3OTFjNTQ5In0.eyJhdWQiOiIyIiwianRpIjoiMjBjYTAwNTdiODBhY2I5MWZmMzFlOTFmZGQ1ZjcyMWY0YjNmZTgxYjM3ODU5NzY1MDMxZDBiNmU5ZDFiZjRmZTdmZDJlMGZmYzc5MWM1NDkiLCJpYXQiOjE1MzU0NDk1MzUsIm5iZiI6MTUzNTQ0OTUzNSwiZXhwIjoxNTY2OTg1NTM1LCJzdWIiOiI5NiIsInNjb3BlcyI6WyJzaXRlLWFkbWluIl19.ZZ07C8FGCWg7oTRZgv7MPWEclVD2D1PJWtO-CIbkYFw7bFgXB4o6mqUMgfJkq-BY9o8JOweECogXj3c9njPn5qFfvvLZ3T1ZPhVkxI1X1atL4R04PootUbBhXZAPYk-X_HKlJtPz4ao7IGxQg0Fu9i_ZKodXeSZYS2yt9EqRe1l199axpK1DiiO-rW695FWOjJVuukV-G4CvvlYpPD2EYwh9WBdqKhDL1BN--RzaY8Gkoe-T7OvPIMdMacjX612wKVoLNwcEIszGrXuQrgT07FPwixuc9M0eH_PHvfqveBovK8OuO8-h_NUqXGMPMSAs5kyfiXC_HnqXXyibEhKK_U4uME3IKRQAn2CYh5nEUDWHxHKckY5hEa6z1uc89eeeYVT33vDYZe_tTv3eeCpF0mRF3_H4MZ-yJKug8IUxYvlqXBqe5OSo9_ap_FEwqIf3xUuxHf-sfVq9C2y5ShRXOsynN52BS5KccRo__6YHS5rFMvHp3U5h7q5xCTjwp1sE5FbqZ7NtHKucj22ZZcsu0-YH6VG4-Am1yZHWUprTtF77eg-x_5zQL_UmmHTqfBzcqrAAly2jj762DLxytnajOESldnF_o9iok29YfF99LslLbtdcMQD_ZyDA3E4Fy7ZotBCtu9kgwAYlSJU3JGBJFWvbeQHO7aXxAtm-g_cpF7I
         * refresh_token :
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
}
