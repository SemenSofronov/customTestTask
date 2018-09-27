package com.company.testtask.core;

import com.company.testtask.core.numerals.Russian;
import com.company.testtask.entity.Contract;
import com.company.testtask.entity.Status;
import com.haulmont.bpm.entity.ProcActor;
import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.app.EmailerAPI;
import com.haulmont.cuba.core.global.EmailInfo;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.security.entity.User;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

@Component("testtask_ContractReportHelper")
public class ContractReportHelper {

    @Inject
    private Persistence persistence;

    @Inject
    protected Messages messages;

    @Inject
    protected EmailerAPI emailerAPI;

    public String vatFormat(Object vat) {
        BigDecimal vatFormat = (BigDecimal) vat;
        if (vatFormat.intValue() == 0) {
            return "не облагается в виду применения Исполнителем упрощенной системы налогообложения";
        } else {
            return vatFormat.toString();
        }
    }

    public String numeralsToString(Object numeral) {
        return new Russian().amount(new BigDecimal(numeral.toString()));
    }

    public String dateFormat(Object date) {
        Date dateFormat = (Date) date;

        Calendar calendar = Calendar.getInstance(new Locale("ru"));
        calendar.setTime(dateFormat);

        return DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
                .withLocale(new Locale("ru"))
                .format(LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1,
                        calendar.get(Calendar.DAY_OF_MONTH)));
    }

    public void setStatus(UUID entityId, Long code, UUID bpmProcInstanceId) {
        try (Transaction tx = persistence.getTransaction()) {
            EntityManager entityManager = persistence.getEntityManager();
            Contract contract = entityManager.find(Contract.class, entityId);


            if (contract != null) {
                TypedQuery<Status> statusTypedQuery =
                        entityManager.createQuery("select s from testtask$Status s where s.code = ?1", Status.class)
                        .setParameter(1, code);

                Status status = statusTypedQuery.getFirstResult();
                if (status != null) {
                    contract.setStatus(status);

                    ProcInstance procInstance = entityManager.find(ProcInstance.class, bpmProcInstanceId);
                    if (procInstance != null) {
                        Set<ProcActor> procActors = procInstance.getProcActors();
                        if (procActors != null) {
                            for (ProcActor procActor : procActors) {
                                User user = procActor.getUser();

                                if (user != null) {
                                    String caption = messages.getMessage("com/company/testtask/core/messages.properties",
                                            "message.caption");
                                    String text = messages.getMessage("com/company/testtask/core/messages.properties",
                                            "message.text");

                                    EmailInfo emailInfo = new EmailInfo(
                                            user.getEmail(),
                                            caption,
                                            text + " " + status.getName()
                                    );

                                    emailerAPI.sendEmailAsync(emailInfo);
                                }

                            }
                        }
                    }
                }
            }

            tx.commit();
        }
    }
}
