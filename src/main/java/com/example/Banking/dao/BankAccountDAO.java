package com.example.Banking.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.Banking.entities.BankAccount;
import com.example.Banking.model.BankAccountInfo;

// class này cung cấp các phương thức để truy vấn đến database 

@Repository   // khi nao ranh thi gg
public class BankAccountDAO {
	
	@Autowired  // thong bao cho spring la nhúng entityManager vào trong lớp dao
	private EntityManager entityManager; // dùng jpa để thao tác với dữ liệu nên dùng EntityManager
	
	public BankAccountDAO() {}
	
	public BankAccount findById(Long id) {   // tìm record bằng khóa chính id trên entity BankAccount
		return this.entityManager.find(BankAccount.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<BankAccountInfo> listBankAccountInfo() {
		// câu query trên đối tượng, hibernate hoặc JPA sẽ translate câu sql này sang sql trong database để thực thi  
		// select trên đối tượng BankAccount entity sau đó lấy dữ liệu đưa vào bankAccount info.
		// cụ thể: select 3 trường id, fullName, balance trên BankAccount entity sau đó truyền vào hàm tạo BankAccountInfo
		String sql = "Select new " + BankAccountInfo.class.getName() + "(e.id,e.fullName,e.balance) " 
					+ " from " + BankAccount.class.getName() + " e ";
		// dùng entityManager để tạo câu query
		Query query = entityManager.createQuery(sql, BankAccountInfo.class);
		
		// trả kết quả lại listBankAccountInfo 
		return query.getResultList();
	}
}
