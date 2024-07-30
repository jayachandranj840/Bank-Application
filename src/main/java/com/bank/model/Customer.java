package com.bank.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Customer {
	private String accountNo;
	private String fullName;
	private String address;
	private String mobileNo;
	private String email;
	private String accountType;
	private BigDecimal balance;
	private LocalDate dateOfBirth;
	private String idProof;
	private String password;

	public Customer() {
	}

	public Customer(String StrfullName) {
		this.fullName = StrfullName;
	}

	public Customer(String accountNo, String fullName, String address, String mobileNo, String email,
			String accountType, BigDecimal balance, LocalDate dateOfBirth, String idProof, String password) {
		this.accountNo = accountNo;
		this.fullName = fullName;
		this.address = address;
		this.mobileNo = mobileNo;
		this.email = email;
		this.accountType = accountType;
		this.balance = balance;
		this.dateOfBirth = dateOfBirth;
		this.idProof = idProof;
		this.password = password;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getIdProof() {
		return idProof;
	}

	public void setIdProof(String idProof) {
		this.idProof = idProof;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Customer{" + "accountNo='" + accountNo + '\'' + ", fullName='" + fullName + '\'' + ", address='"
				+ address + '\'' + ", mobileNo='" + mobileNo + '\'' + ", email='" + email + '\'' + ", accountType='"
				+ accountType + '\'' + ", balance=" + balance + ", dateOfBirth=" + dateOfBirth + ", idProof='" + idProof
				+ '\'' + '}';
	}
}
