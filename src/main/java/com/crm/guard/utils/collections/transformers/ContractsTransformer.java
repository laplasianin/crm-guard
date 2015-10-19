package com.crm.guard.utils.collections.transformers;

import com.crm.guard.entity.Contract;
import org.apache.commons.collections4.Transformer;

import java.text.SimpleDateFormat;

public class ContractsTransformer implements Transformer<Contract, String>{

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.mm.yyyy");
    private final StringBuilder builder = new StringBuilder();

    @Override
    public String transform(Contract contract) {
        return builder
                .append("№ ")
                .append(contract.getContractNumber())
                .append(" от ")
                .append(simpleDateFormat.format(contract.getContractStartDate()))
                .append(" г.")
                .toString();
    }
}
