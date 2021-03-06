package com.bustanil.easyaccounting.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Journal {

    private Date postingDate;
    private Date transactionDate;
    private String referenceNo;
    private List<JournalEntry> journalEntries;

    public Journal(Date transactionDate, Date postingDate) {
        this.transactionDate = transactionDate;
        this.postingDate = postingDate;
    }

    public List<JournalEntry> getEntries() {
        return journalEntries;
    }

    public JournalEntry getEntryByAccountCode(String accountCode) {
        for (JournalEntry journalEntry : journalEntries) {
            if(journalEntry.getAccount().getCode().equals(accountCode)){
                return journalEntry;
            }
        }
        throw new IllegalArgumentException("Entry not found for account code: " + accountCode);
    }

    public boolean isBalance() {
        BigDecimal drTotal = BigDecimal.ZERO;
        BigDecimal crTotal = BigDecimal.ZERO;
        for (JournalEntry journalEntry : journalEntries) {
            if(journalEntry.getDrCr() == DebitCredit.CR) {
                crTotal = crTotal.add(journalEntry.getAmount());
            }
            else {
                drTotal = drTotal.add(journalEntry.getAmount());
            }
        }
        return drTotal.compareTo(crTotal) == 0;
    }

    public void addEntry(AccountConfig accountConfig, Transaction transaction) {
        JournalEntry journalEntry = new JournalEntry(accountConfig.getAccount(), accountConfig.getDrCr(), transaction.getAmount());
        if(journalEntries == null){
            journalEntries = new ArrayList<JournalEntry>();
        }
        journalEntries.add(journalEntry);
    }
}
