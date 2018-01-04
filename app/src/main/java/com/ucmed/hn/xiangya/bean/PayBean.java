package com.ucmed.hn.xiangya.bean;

/**
 * 作者：王海洋
 * 时间：2017/3/10 18:21
 */

public class PayBean {

    private String payChannel;
    private String orderTime;
    private String merchantNo;
    private String merchantOrderNo;
    private String productName;
    private String amount;
    private String returnUrl;
    private String notifyUrl;
    private String orderPeriod;
    private String desc;
    private String trxType;
    private String payType;
    private String fundIntoType;

    public String getPayChannel() {return payChannel;}

    public void setPayChannel(String payChannel) {this.payChannel = payChannel;}

    public String getOrderTime(){return orderTime;}

    public void setOrderTime(String orderTime){this.orderTime = orderTime;}

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {this.merchantNo = merchantNo;}

    public String getMerchantOrderNo(){return merchantOrderNo;}

    public void setMerchantOrderNo(String merchantOrderNo){this.merchantOrderNo = merchantOrderNo;}

    public String getProductName(){return productName;}

    public void setProductName(String productName){this.productName = productName;}

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getOrderPeriod() {
        return orderPeriod;
    }

    public void setOrderPeriod(String orderPeriod) {
        this.orderPeriod = orderPeriod;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTrxType() {
        return trxType;
    }

    public void setTrxType(String trxType) {this.trxType = trxType;}

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getFundIntoType() {
        return fundIntoType;
    }

    public void setFundIntoType(String fundIntoType) {
        this.fundIntoType = fundIntoType;
    }

}
