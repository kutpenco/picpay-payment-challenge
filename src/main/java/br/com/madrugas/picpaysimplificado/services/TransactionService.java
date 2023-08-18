package br.com.madrugas.picpaysimplificado.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.madrugas.picpaysimplificado.domain.transaction.Transaction;
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
		
		boolean isAuthorized = this.authorizeTransaction(sender, transaction.value());
		if(!isAuthorized) {
			throw new Exception("Transação não autorizada.");
		}
		
		//se tudo certo, executa a operação
		Transaction newTransaction = new Transaction();
		newTransaction.setAmount(transaction.value());
		newTransaction.setSender(sender);
		newTransaction.setReceiver(receiver);
		newTransaction.setTimestamp(LocalDateTime.now());
		
		sender.setBalance(sender.getBalance().subtract(transaction.value()));
		receiver.setBalance(receiver.getBalance().add(transaction.value()));
		
		this.repository.save(newTransaction);
		this.userService.saveUser(sender);
		this.userService.saveUser(receiver);
		
	}
	
	public boolean authorizeTransaction(User sender, BigDecimal value) {
		ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6", Map.class);
		
		if (authorizationResponse.getStatusCode() == HttpStatus.OK) {
			String message = (String) authorizationResponse.getBody().get("message");
			return "Autorizado".equalsIgnoreCase(message);
		}
	}
}
