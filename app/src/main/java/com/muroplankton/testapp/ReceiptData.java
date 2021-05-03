package com.muroplankton.testapp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ReceiptData extends RealmObject {
    @PrimaryKey
    private String receiptID;
    private String receiptName, receiptDate, receiptUri;

    public String getReceiptID() {
        return receiptID;
    }

    public void setReceiptID(String receiptID) {
        this.receiptID = receiptID;
    }

    public String getReceiptName() {
        return receiptName;
    }

    public void setReceiptName(String receiptName) {
        this.receiptName = receiptName;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getReceiptUri() {
        return receiptUri;
    }

    public void setReceiptUri(String receiptUri) {
        this.receiptUri = receiptUri;
    }
}
