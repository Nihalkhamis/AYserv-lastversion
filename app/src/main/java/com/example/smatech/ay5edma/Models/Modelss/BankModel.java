package com.example.smatech.ay5edma.Models.Modelss;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("bank_account_name_ar")
    @Expose
    private String bank_account_name_ar;
    @SerializedName("bank_account_name")
    @Expose
    private String bank_account_name;
    @SerializedName("bank_name")
    @Expose
    private String bankName;
   @SerializedName("bank_name_ar")
    @Expose
    private String bank_name_ar;
    @SerializedName("bank_account")
    @Expose
    private String bankAccount;
    @SerializedName("bank_ipan")
    @Expose
    private String bankIpan;
    @SerializedName("image")
    @Expose
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankIpan() {
        return bankIpan;
    }

    public void setBankIpan(String bankIpan) {
        this.bankIpan = bankIpan;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBank_account_name_ar() {
        return bank_account_name_ar;
    }

    public String getBank_name_ar() {
        return bank_name_ar;
    }

    public String getBank_account_name() {
        return bank_account_name;
    }
}
