package com.crm.guard.service.api;

import com.crm.guard.entity.EventType;

public interface EventTypeService {

    EventType findById(String id);

    String getClientSaveCode();
    String getContactSaveCode();
    String getContactUpdateCode();
    String getCallCode();
    String getSmsCode();
    String getEmailCode();
    String getTaskCode();
    String getRegisterCode();
    String getOperatorTakeClientCode();
    String getOperatorKickClientCode();
    String getClaimCode();

    String getDisableNotifyCode();

    String getDisabledCode();

    String getBillDuplicationCode();

    void createEventTypes();
}