package com.company.testtask.listener;

import com.company.testtask.entity.Contract;
import com.company.testtask.entity.Stage;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component("testtask_ContractEntityListener")
public class ContractEntityListener implements BeforeInsertEntityListener<Contract> {

    @Inject
    protected Metadata metadata;

    @Override
    public void onBeforeInsert(Contract contract, EntityManager entityManager) {
        List<Stage> stages = contract.getStages();
        if (stages == null || stages.isEmpty()) {

            Stage stage = metadata.create(Stage.class);
            stage.setName("Common");
            stage.setAmount(contract.getAmount());
            stage.setDateFrom(contract.getDateFrom());
            stage.setDateTo(contract.getDateTo());
            stage.setVat(contract.getVat());
            stage.setTotalAmount(contract.getTotalAmount());
            stage.setContract(contract);

            entityManager.persist(stage);
        }
    }

}