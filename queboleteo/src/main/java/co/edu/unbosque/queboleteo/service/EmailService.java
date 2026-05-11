package co.edu.unbosque.queboleteo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.Servlet;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Value("$spring.mail.username")
	private String from;
	
	public void enviarEmailDeVerificacion(String email, Long idDeVerificacion) {
		String asunto = "¡Bienvenido a VirusTotal!";
		String path = "/user/verificar";
		String mensaje = "Haz click en el botón de abajo para verificar tu cuenta de virus total";
		enviarEmail(email, idDeVerificacion, asunto, path, mensaje);
		
	} 

	public void enviarEmail(String email, Long idUsuario, String asunto, String path, String mensaje) {
		try {
			String actionUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path(path).queryParam("id", idUsuario).toUriString();
			System.out.println(actionUrl);
			String contenido =  """
                    <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border-radius: 8px; background-color: #f9f9f9; text-align: center;">
                    <h2 style="color: #333;">%s</h2>
                    <p style="font-size: 16px; color: #555;">%s</p>
                    <a href="%s" style="display: inline-block; margin: 20px 0; padding: 10px 20px; font-size: 16px; color: #fff; background-color: #007bff; text-decoration: none; border-radius: 5px;">Continuar</a>
                    <p style="font-size: 14px; color: #777;">O copia y pega este link en tu navegador:</p>
                    <p style="font-Fsize: 14px; color: #007bff;">%s</p>
                    <p style="font-size: 12px; color: #aaa;">Este ha sido un mensaje autogenerado. Por favor no responder.</p>
                </div>
            """.formatted(asunto, mensaje, actionUrl, actionUrl);
			
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setTo(email);
			helper.setSubject(asunto);
			helper.setFrom(from);
			helper.setText(contenido, true );
			mailSender.send(mimeMessage);
			
		} catch (Exception e) {
			System.out.println("Error en el envío de email"+ e.getMessage());
		}
	}

}
