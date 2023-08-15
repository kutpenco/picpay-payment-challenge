package br.com.madrugas.picpaysimplificado.services;

import java.math.BigDecimal;

import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.madrugas.picpaysimplificado.domain.user.User;
import br.com.madrugas.picpaysimplificado.domain.user.UserType;
import br.com.madrugas.picpaysimplificado.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository repository;
	
	public void validateTransaction(User sender, BigDecimal amount) throws Exception {
		if(sender.getUserType() == UserType.MERCHANT) {
			throw new Exception("Usuario do tipo Lojista não está autorizado a realizar a Transação.");
		}
		
		if(sender.getBalance().compareTo(amount) < 0) {
			throw new Exception("Saldo Insulficiente.");
		}
	}
	
	public User findUserById(Long Id) throws Exception {
		return this.repository.findUserById(Id).orElseThrow(() -> new Exception("Usuario não encontrado."));
	}
	
	public void saveUser (User user) {
		this.repository.save(user);
	}
}
