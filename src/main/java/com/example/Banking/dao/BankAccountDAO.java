package com.example.Banking.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;


import com.example.Banking.entities.BankAccount;
import com.example.Banking.exception.BankTransactionException;
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
	// MANDATORY: bắt buộc phải có Transaction đã được tạo trước đó.(kế thừa từ transaction đã được khởi tạo trước đó)
	@org.springframework.transaction.annotation.Transactional(propagation = Propagation.MANDATORY)
	public void addAmount(Long id, double amount) throws BankTransactionException { // có lỗi nào thì nó ném hết cho BankTransactionException
		BankAccount account = this.findById(id);
		if(account == null) {
			throw new BankTransactionException("Acount not found " + id);  // có lỗi thì ném cho BankTransaction
		}
		double newBalance = account.getBalance() + amount;
		if(account.getBalance() + amount < 0) {
			throw new BankTransactionException("the money in the account '" + id + "' is not enough (" + account.getBalance() + ")");
		}
		account.setBalance(newBalance);
	}
	
	
	// không bắt ngoại lệ BankTransaction trong phương thưc 
	// khởi tạo mới 1 transaction, rollback khi quá trình transaction bị lỗi (transtaction trên và dưới cùng chung 1 giao dịch)
	@org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
	public void sendMoney(Long fromAccountId, Long toAccountId, double amount) throws BankTransactionException	 {
		addAmount(toAccountId, amount);
		addAmount(fromAccountId, -amount);
	}
}
