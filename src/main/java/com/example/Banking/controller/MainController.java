package com.example.Banking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.Banking.dao.BankAccountDAO;
import com.example.Banking.exception.BankTransactionException;
import com.example.Banking.form.SendMoneyForm;
import com.example.Banking.model.BankAccountInfo;

@Controller
public class MainController {
	
	@Autowired
	private BankAccountDAO bankAccountDAO;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showBankAccounts(Model model) {
		// gọi method listBankAccountInfo() trong BankAccountDAO và đưa vào list 
		List<BankAccountInfo> list = bankAccountDAO.listBankAccountInfo();
		
		// gửi list lên front-end thong qua attribute accountInfos
		model.addAttribute("accountInfos", list);
		
		return "accountsPage";  // gọi trang view
	}
	
	@RequestMapping(value="/sendMoney", method = RequestMethod.GET)
	public String viewSendMoney(Model model) {
		SendMoneyForm form = new SendMoneyForm(1L, 2L, 100d);
		
		model.addAttribute("sendMoneyForm", form);
		
		return "sendMoneyPage";
	}	
	
	@RequestMapping(value="/sendMoney", method = RequestMethod.POST)
	public String processSendMoney(Model model, SendMoneyForm sendMoneyForm) {   // truyen sendMoenyForm vao ham
		
		System.out.println("Send money: " + sendMoneyForm.getAmount());
		try {  // nếu trong này có lỗi thì nó sẽ bắt BankTransactionException 
			bankAccountDAO.sendMoney(sendMoneyForm.getFromAccountId(), 
					sendMoneyForm.getToAccountId(), 
					sendMoneyForm.getAmount());
			
		} catch (BankTransactionException e) {  // bắt lỗi và đưa lên front-end 
			model.addAttribute("errorMessage", "error" + e.getMessage());
			return "/sendMoneyPage";
		}
		return "redirect:/";
		
	}
	
}
