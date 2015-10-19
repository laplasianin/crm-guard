package com.crm.guard.binder;

import com.crm.guard.entity.Contract;
import com.crm.guard.service.api.ContractService;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;


public class ContractEditor extends PropertyEditorSupport {

    private ContractService contractService;

    public ContractEditor(ContractService contractService) {
        this.contractService = contractService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isEmpty(text)) {
            setValue(null);
            return;
        }
        setValue(contractService.getContractById(text));
    }

    @Override
    public String getAsText() {
        Contract contract = (Contract) getValue();
        if (contract == null) {
            return null;
        } else {
            return String.valueOf(contract.getId());
        }
    }

}
