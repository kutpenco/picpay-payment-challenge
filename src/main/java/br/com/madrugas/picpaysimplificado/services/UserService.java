package br.com.madrugas.picpaysimplificado.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.madrugas.picpaysimplificado.domain.user.User;
import br.com.madrugas.picpaysimplificado.domain.user.UserType;
import br.com.madrugas.picpaysimplificado.dtos.UserDTO;
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
	
	public User createUser(UserDTO data) {
		User newUser = new User(data);
		this.saveUser(newUser);
		return newUser;
	}
	
	public List<User> getAllUsers(){
		return this.repository.findAll();
	}
	
	public void saveUser (User user) {
		this.repository.save(user);
	}
}
