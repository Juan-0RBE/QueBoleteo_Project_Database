package co.edu.unbosque.queboleteo.service;

import java.math.BigDecimal;
import java.util.List;

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

	@Value("${spring.mail.username}")
	private String from;

	/**
	 * Envía el correo de verificación de cuenta al usuario recién registrado.
	 *
	 * @param correo Correo del usuario (PK en QueBoleteo)
	 */
	public void enviarEmailDeVerificacion(String correo) {
		String asunto = "¡Bienvenido a QueBoleteo!";
		String path = "/auth/verificar";
		String mensaje = "Haz click en el botón de abajo para verificar tu cuenta de QueBoleteo";
		enviarEmail(correo, asunto, path, mensaje);
	}

	/**
	 * Construye y envía un correo HTML al usuario.
	 *
	 * @param correo  Correo del usuario — usado como destino y como param de
	 *                verificación
	 * @param asunto  Asunto del correo
	 * @param path    Path del endpoint de verificación
	 * @param mensaje Mensaje a mostrar en el cuerpo del correo
	 */
	public void enviarEmail(String correo, String asunto, String path, String mensaje) {
		try {
			// ⚠️ El query param ahora es ?correo= en vez de ?id=
			String actionUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path(path)
					.queryParam("correo", correo).toUriString();

			System.out.println("URL de verificación generada: " + actionUrl);

			String contenido = """
					<div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border-radius: 8px; background-color: #f9f9f9; text-align: center;">
					    <h2 style="color: #333;">%s</h2>
					    <p style="font-size: 16px; color: #555;">%s</p>
					    <a href="%s" style="display: inline-block; margin: 20px 0; padding: 10px 20px; font-size: 16px; color: #fff; background-color: #007bff; text-decoration: none; border-radius: 5px;">Continuar</a>
					    <p style="font-size: 14px; color: #777;">O copia y pega este link en tu navegador:</p>
					    <p style="font-size: 14px; color: #007bff;">%s</p>
					    <p style="font-size: 12px; color: #aaa;">Este ha sido un mensaje autogenerado. Por favor no responder.</p>
					</div>
					"""
					.formatted(asunto, mensaje, actionUrl, actionUrl);

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setTo(correo);
			helper.setSubject(asunto);
			helper.setFrom(from);
			helper.setText(contenido, true);
			mailSender.send(mimeMessage);

		} catch (Exception e) {
			System.out.println("Error en el envío de email: " + e.getMessage());
		}
	}

	public void enviarCorreoCompra(String correo, String nombreUsuario, String nombreEvento, int cantidadBoletos,
			BigDecimal valorTotal, List<String> detallesBoletos) {

		try {

			StringBuilder detallesHtml = new StringBuilder();

			for (String detalle : detallesBoletos) {

				detallesHtml.append("""
						<li style="
							margin-bottom: 8px;
							color: #555;
							list-style: none;
						">
							%s
						</li>
						""".formatted(detalle));
			}

			String asunto = "Confirmación de compra - QueBoleteo";

			String contenido = """
					<div style="
					    font-family: Arial, sans-serif;
					    max-width: 600px;
					    margin: auto;
					    padding: 20px;
					    background-color: #f9f9f9;
					    border-radius: 10px;
					    border: 1px solid #ddd;
					">

					    <h2 style="color: #333;">
					        ¡Gracias por tu compra, %s!
					    </h2>

					    <p style="font-size: 16px; color: #555;">
					        Tu compra fue realizada exitosamente.
					    </p>

					    <hr>

					    <h3 style="color: #007bff;">
					        Detalles de la compra
					    </h3>

					    <table style="
					        width: 100%%;
					        border-collapse: collapse;
					    ">

					        <tr>
					            <td style="
					                padding: 8px;
					                font-weight: bold;
					            ">
					                Evento:
					            </td>

					            <td style="padding: 8px;">
					                %s
					            </td>
					        </tr>

					        <tr>
					            <td style="
					                padding: 8px;
					                font-weight: bold;
					            ">
					                Cantidad de boletos:
					            </td>

					            <td style="padding: 8px;">
					                %d
					            </td>
					        </tr>

					        <tr>
							    <td style="
							        padding: 8px;
							        font-weight: bold;
							        vertical-align: top;
							    ">
							        Boletos:
							    </td>

							    <td style="padding: 8px;">
							        <ul style="
							            padding-left: 20px;
							            margin: 0;
							        ">
							            %s
							        </ul>
							    </td>
							</tr>

					        <tr>
					            <td style="
					                padding: 8px;
					                font-weight: bold;
					            ">
					                Valor total:
					            </td>

					            <td style="padding: 8px;">
					                $ %s COP
					            </td>
					        </tr>

					    </table>

					    <hr>

					    <p style="
					        font-size: 14px;
					        color: #777;
					    ">
					        Gracias por confiar en QueBoleteo.
					    </p>

					    <p style="
					        font-size: 12px;
					        color: #aaa;
					    ">
					        Este ha sido un mensaje automático.
					    </p>

					</div>
					""".formatted(nombreUsuario, nombreEvento, cantidadBoletos, detallesHtml.toString(),
					valorTotal.toString());

			MimeMessage mimeMessage = mailSender.createMimeMessage();

			//MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			
			helper.setTo(correo);
			helper.setSubject(asunto);
			//helper.setFrom(from);
			helper.setFrom(from, "QueBoleteo");
			helper.setReplyTo(from); //NUEVO
			helper.setText(contenido, true);

			mailSender.send(mimeMessage);

		} catch (Exception e) {

			System.out.println("Error enviando correo de compra: " + e.getMessage());
		}
	}
}
