package com.crm.guard.utils.collections.transformers;

import com.crm.guard.form.delivery.message.MessageForm;
import org.apache.commons.collections4.Transformer;

public class MobileNumberTransformer implements Transformer<MessageForm, Object> {
    @Override
    public Object transform(MessageForm input) {
        return input.getClient().getMobileNumber();
    }
}
