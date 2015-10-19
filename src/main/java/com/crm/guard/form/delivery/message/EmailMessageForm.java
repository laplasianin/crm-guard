package com.crm.guard.form.delivery.message;

public class EmailMessageForm extends MessageForm {

    private String from;
    private String to;

    private EmailMessageForm(Builder builder) {
        setFrom(builder.from);
        setTo(builder.to);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public static final class Builder {
        private String from;
        private String to;

        private Builder() {
        }

        public Builder from(String from) {
            this.from = from;
            return this;
        }

        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public EmailMessageForm build() {
            return new EmailMessageForm(this);
        }
    }
}
