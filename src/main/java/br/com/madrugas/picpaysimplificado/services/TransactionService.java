package br.com.madrugas.picpaysimplificado.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.madrugas.picpaysimplificado.domain.user.User;
import br.com.madrugas.picpaysimplificado.dtos.TransactionDTO;
import br.com.madrugas.picpaysimplificado.repositories.TransactionRepository;

@Service
public class TransactionService {
	@Autowired
	private UserService userService;
	@Autowired
	private TransactionRepository repository;
	@Autowired
	private RestTemplate restTemplate;
	
	public void createTransaction(TransactionDTO transaction) throws Exception{
		User sender = this.userService.findUserById(transaction.senderID());
		User receiver = this.userService.findUserById(transaction.ReceiverID());
	
		userService.validateTransaction(sender, transaction.value());
	}
	
	public boolean authorizeTransaction(User sender, BigDecimal value) {
		
	}
}
