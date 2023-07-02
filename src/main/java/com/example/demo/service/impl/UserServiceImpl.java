package com.example.demo.service.impl;

import com.example.demo.model.File;
import com.example.demo.model.Users;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.IUserService;

import net.bytebuddy.utility.RandomString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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
				+ "Thank you for registering with our website. To complete the registration process,<br>"
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

	@Override
	public List<Users> getFollowing(Long user_id) {

		return userRepository.findAll();
	}

	@Override
	public long countUsers() {

		return userRepository.count();

	}

	@Override
	public Long getUserCount() {
		return userRepository.count();
	}

	private void sendActiceEmail(Users user) throws MessagingException, UnsupportedEncodingException {
		String toAddress = user.getEmail();
		String fromAddress = "sender@example.com"; // Replace with your actual email address
		String senderName = "DocShare";
		String subject = "Your Docshare account has been suspended and is scheduled for deletion";
		String content = "Dear [[name]],<br>"
				+ "<br><div>We regret to inform you that your Docshare account has been,<br>"
				+ "suspended due to a violation of our platform's terms of service or,<br>"
				+ "community guidelines. As a result, your account is scheduled for deletion.,</div><br>"
				+ "<div>We take the safety and security of our community seriously, and,<br>"
				+ "users to review our terms of service and community guidelines to<br>"
				+ "any violation of our policies is not tolerated. We encourage all our<br>"
				+ "community guidelines. As a result, your account is scheduled for deletion.,</div><br>"
				+ "<div>If you believe that this decision is in error or have any questions<br>"
				+ "regarding the suspension, please feel free to reach out to our<br>"
				+ "support team for further assistance. We will be happy to provide<br>"
				+ "you with more information on why your account was suspended and what steps you can take to resolve the issue.</div><br>"
				+ "Thank you for your understanding.<br>"
				+ "Best regards,<br>"
				+ "The Docshare Team";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);

		content = content.replace("[[name]]", user.getName());

		helper.setText(content, true);

		mailSender.send(message);

		System.out.println("Email has been sent");
	}

	@Override
	public void sendActive(Users user) throws UnsupportedEncodingException, MessagingException {
		sendActiceEmail(user);
	}

	// @Override
	// public List<Object[]> following(Long user_id) {
	// return userRepository.following(user_id);
	// }

		@Override
	public void sendDelete(Users user, File file) throws UnsupportedEncodingException, MessagingException {
		 sendDeleteEmail(user, file);
	}

	private void sendDeleteEmail(Users user, File file)
			throws MessagingException, UnsupportedEncodingException {
		String toAddress = user.getEmail();
		String fromAddress = "sender@example.com"; // Replace with your actual email address
		String senderName = "DocShare";
		String subject = "Your Docshare account has been suspended and is scheduled for deletion";
		String content = "Dear [[name]],<br>"
				+ "<br><div>We are writing to inform you that one or more of your files on<br>"
				+ "DocShare are scheduled for deletion. We wanted to let you know<br>"
				+ "ahead of time so that you have the opportunity to save or download any important files that you still need.</div><br>"
				+ "<div>Please note that this action is being taken because the file in<br>"
				+ "question have violated our terms of service. Specifically, the file<br>"
				+ "have been found to contain prohibited content or to have been<br>"
				+ "seriously and must take action to ensure the safety and integrity of our platform.</div><br>"
				+ "The following file are scheduled for deletion: [[namefile]]<br>"
				+ "<div>If you have any questions or concerns about this, please do not<br>"
				+ "hesitate to contact us at Email: nguyendiggory@gmail.com . Thank you for your understanding.</div><br>"
				+ "Best regards,<br>"
				+ "The Docshare Team";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);

		content = content.replace("[[name]]", user.getName());
		content = content.replace("[[namefile]]", file.getFileName());

		helper.setText(content, true);

		mailSender.send(message);

		System.out.println("Email has been sent");
	}

}
