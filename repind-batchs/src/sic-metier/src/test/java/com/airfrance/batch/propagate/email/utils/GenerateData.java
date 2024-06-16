package com.airfrance.batch.propagate.email.utils;

import com.airfrance.repind.entity.adresse.Email;
import com.airfrance.repind.entity.individu.AccountData;

import java.util.ArrayList;
import java.util.List;

public class GenerateData {

    public static AccountData buildAccount() {
        AccountData account = new AccountData();
        account.setSgin("400519037644");
        account.setFbIdentifier("009011780326");
        account.setAccountIdentifier("467213AR");
        return account;
    }

    public static List<AccountData> buildAccountList() {
        AccountData account1 = new AccountData();
        account1.setSgin("400519037644");
        account1.setFbIdentifier("009011780326");
        account1.setAccountIdentifier("467213AR");

        AccountData account2 = new AccountData();
        account2.setSgin("000000000673");
        account2.setFbIdentifier("001024800194");
        account2.setAccountIdentifier("419431AB");

        List<AccountData> accountDataList = new ArrayList<>();
        accountDataList.add(account1);
        accountDataList.add(account2);

        return accountDataList;
    }

    public static Email buildEmail() {
        Email email = new Email();
        email.setSgin("test_sgin");
        email.setEmail("test@example.com");
        return email;
    }

    public static List<Email> buildListEmails() {
        List<Email> emails = new ArrayList<>();
        Email email1 = new Email();
        email1.setSgin("test_sgin");
        email1.setEmail("test1@example.com");

        Email email2 = new Email();
        email2.setSgin("test_sgin");
        email2.setEmail("test2@example.com");

        emails.add(email1);
        emails.add(email2);

        return emails;
    }
}
