package com.example.Banking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.Banking.dao.BankAccountDAO;
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
	
}
