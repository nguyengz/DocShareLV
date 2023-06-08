package com.example.demo.service.impl;

import com.example.demo.model.Users;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.IUserService;

import net.bytebuddy.utility.RandomString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	IUserRepository userRepository;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
    GoogleFileManager googleFileManager;

	@Override
	public Optional<Users> findByUsername(String name) {
		return userRepository.findByUsername(name);
	}

	@Override
	public Boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public Users save(Users users) {
		return userRepository.save(users);
	}

	@Override
	public void register(Users user, String siteURL) throws UnsupportedEncodingException, MessagingException {
		String randomCode = RandomString.make(64);
		user.setVerificationCode(randomCode);
		user.setEnabled(false);

		userRepository.save(user);
	
		sendVerificationEmail(user, siteURL);
	}


	private void sendVerificationEmail(Users user, String siteURL)
			throws MessagingException, UnsupportedEncodingException {
		String toAddress = user.getEmail();
		String fromAddress = "sender@example.com"; // Replace with your actual email address
		String senderName = "DocShare";
		String subject = "Please verify your registration";
		String content = "Dear [[name]],<br>"
				+"Thank you for registering with our website. To complete the registration process,<br>"
				+ "Please click the link below to verify your registration:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
				+ "Thank you,<br>"
				+ "DocShare.";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);

		content = content.replace("[[name]]", user.getName());
		String verifyURL = siteURL + "/api/auth/verify?code=" + user.getVerificationCode();

		content = content.replace("[[URL]]", verifyURL);

		helper.setText(content, true);
	
		mailSender.send(message);

		System.out.println("Email has been sent");
	}

	@Override
	public Boolean verify(String verificationCode) {
		Users user = userRepository.findByVerificationCode(verificationCode);

		if (user == null || user.isEnabled()) {
			return false;
		} else {
			user.setVerificationCode(null);
			user.setEnabled(true);
			try {
				googleFileManager.getFolderId(user.getUsername());
			} catch (Exception e) {
				e.printStackTrace();
			}
			userRepository.save(user);
			return true;
		}

	}

	@Override
	public Optional<Users> findById(Long id) {
		return userRepository.findById(id);
		
	}

	@Override
	public List<Users> getAllUser() {
		return userRepository.findAll();
	}
	
}
