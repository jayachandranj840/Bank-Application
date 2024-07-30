package com.bank.model;

import java.util.Date;

public class Transaction {
	private int transactionId;
	private int accountNo;
	private Date transactionDate;
	private String transactionType;
	private double amount;
	private double balanceAfterTransaction;

	// Constructor
	public Transaction() {
		
	}

	public Transaction(int transactionId, int accountNo, Date transactionDate, String transactionType, double amount,
			double balanceAfterTransaction) {
		this.transactionId = transactionId;
		this.accountNo = accountNo;
		this.transactionDate = transactionDate;
		this.transactionType = transactionType;
		this.amount = amount;
		this.balanceAfterTransaction = balanceAfterTransaction;
	}

	// Getters and Setters
	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public int getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(int accountNo) {
		this.accountNo = accountNo;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getBalanceAfterTransaction() {
		return balanceAfterTransaction;
	}

	public void setBalanceAfterTransaction(double balanceAfterTransaction) {
		this.balanceAfterTransaction = balanceAfterTransaction;
	}

	@Override
	public String toString() {
		return "Transaction{" + "transactionId=" + transactionId + ", accountNo=" + accountNo + ", transactionDate="
				+ transactionDate + ", transactionType='" + transactionType + '\'' + ", amount=" + amount
				+ ", balanceAfterTransaction=" + balanceAfterTransaction + '}';
	}
}
