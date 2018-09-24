package com.company.testtask.core;

import com.company.testtask.core.numerals.Russian;
import com.company.testtask.entity.Contract;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Component("testtask_ContractHelper")
public class ContractHelper {

    @Inject
    private Persistence persistence;

    public String vatFormat(Object vat) {
        String vatFormat = vat.toString();
        if (vatFormat.equals("0%")) {
            return "не облагается в виду применения Исполнителем упрощенной системы налогообложения";
        } else {
            return vatFormat;
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

    public void setStatus(UUID entityId, String status) {
        try (Transaction tx = persistence.getTransaction()) {
            EntityManager entityManager = persistence.getEntityManager();
            Contract contract = entityManager.find(Contract.class, entityId);

            if (contract != null) {
                contract.setStatus(status);
            }

            tx.commit();
        }
    }
}
