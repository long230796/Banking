package com.example.Banking.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// cấu hình, mapping đến bank_account ở trong database 
@Entity // 1 annotation nói class này là 1 entity.
@Table(name = "Bank_Account")  // nói rằng entity này mapping đến table bank_account trong DB

public class BankAccount {
	@Id  // đây là id khóa chính
	@GeneratedValue // tự động generate value
	@Column(name = "id", nullable = false)  // mapping đến cột id trong table dattabase
	private Long id;
	
	@Column(name = "Full_Name", nullable = false, length = 128)  // maping trường fullname với cột Full_Name trong bảng bankAccount
	private String fullName;
	
	@Column(name = "Balance", nullable = false)
	private double balance;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}

	
	
	
	
}
