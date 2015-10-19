package com.crm.guard.form.delivery.message;

public class SmsMessageForm extends MessageForm {

    private String mobileNumber;

    public SmsMessageForm() {
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
