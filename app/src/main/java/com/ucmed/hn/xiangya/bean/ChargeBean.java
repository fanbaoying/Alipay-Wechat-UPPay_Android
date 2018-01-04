package com.ucmed.hn.xiangya.bean;

/**
 * 作者：王海洋
 * 时间：2017/3/10 18:39
 */

public class ChargeBean {

    private ResultBean result;

    private String respCode;
    private String message;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
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



    public static class ResultBean {

        private String TrxNo;
        private Object credential;

        public String getTrxNo() {
            return TrxNo;
        }

        public void setTrxNo(String trxNo) {
            TrxNo = trxNo;
        }

        public Object getCredential() {
            return credential;
        }

        public void setCredential(Object credential) {
            this.credential = credential;
        }



    }

}
