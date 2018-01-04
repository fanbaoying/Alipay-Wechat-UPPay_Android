package com.ucmed.hn.xiangya.bean;

/**
 * Created by fby on 2017/5/23.
 */

public class ChargeWXBean {


    private ResultWXBean result;

    private String respCode;
    private String message;

    public ResultWXBean getResult() {
        return result;
    }

    public void setResult(ResultWXBean result) {
        this.result = result;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public static class ResultWXBean {


        private String TrxNo;
        private CredentialWXBean credential;

        public String getTrxNo() {
            return TrxNo;
        }

        public void setTrxNo(String trxNo) {
            TrxNo = trxNo;
        }

        public CredentialWXBean getCredential() {
            return credential;
        }

        public void setCredential(CredentialWXBean credential) {
            this.credential = credential;
        }

        public static class CredentialWXBean{

            private String packages;
            private String appid;
            private String sign;
            private String partnerid;
            private String prepayid;
            private String noncestr;
            private String timestamp;

            public String getPackages() {
                return packages;
            }

            public void setPackages(String packages) {
                this.packages = packages;
            }

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }
        }

    }
}
