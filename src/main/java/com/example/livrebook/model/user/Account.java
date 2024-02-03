package com.example.livrebook.model.user;

public class Account {
    private int id;
    private int amount;

    private int accountNumber;

    private int code;
    private int userId;

    public Account() {
    }

    public Account(int id, int amount, int accountNumber, int code, int userId) {
        this.id = id;
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.code = code;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", amount=" + amount +
                ", accountNumber=" + accountNumber +
                ", code=" + code +
                ", userId=" + userId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
