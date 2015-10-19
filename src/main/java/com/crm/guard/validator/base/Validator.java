package com.crm.guard.validator.base;

public interface Validator<FORM> {

    public void validate(FORM form, Messages messages);

}
