package br.com.madrugas.picpaysimplificado.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.madrugas.picpaysimplificado.domain.user.User;
import br.com.madrugas.picpaysimplificado.dtos.NotificationDTO;

@Configuration
@Service
public class NotificationService {

	@Autowired
	private RestTemplate restTemplate;
	
	public void sendNotification(User user, String message) throws Exception {
		String email = user.getEmail();
		NotificationDTO nothificationRequest = new NotificationDTO(email, message);
		
		ResponseEntity<String> notificationResponse = restTemplate.postForEntity("http://o4d9z.mocklab.io/notify", nothificationRequest, String.class);
		
		if (!(notificationResponse.getStatusCode() == HttpStatus.OK)) {
			System.out.println("Erro ao enviar a notificação...");
			throw new Exception("Serviço de notificação fora do AR");
		}
	}
}
