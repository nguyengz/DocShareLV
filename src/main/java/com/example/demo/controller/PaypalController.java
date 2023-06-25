package com.example.demo.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.response.PayReponse;
import com.example.demo.model.Order;
import com.example.demo.model.OrderDetail;
import com.example.demo.model.Package;
import com.example.demo.model.Role;
import com.example.demo.model.RoleName;
import com.example.demo.model.Users;
import com.example.demo.service.OrderService;
import com.example.demo.service.OrderdetailService;
import com.example.demo.service.PackageService;
import com.example.demo.service.PaypalService;
import com.example.demo.service.impl.RoleServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller
public class PaypalController {

	@Autowired
	PaypalService service;

	@Autowired
	UserServiceImpl userService;

	@Autowired
	PackageService packageService;

	@Autowired
	RoleServiceImpl roleService;

	@Autowired
	OrderdetailService orderdetailService;

	@Autowired
	OrderService orderService;

	public static final String SUCCESS_URL = "pay/success";
	public static final String CANCEL_URL = "pay/cancel";

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/pay")
	public String payment(@RequestBody PayReponse payReponse) {
		try {
			
			Optional<Users> optionalUser = userService.findById(payReponse.getUser_id());
			Users user = optionalUser.isPresent() ? optionalUser.get() : null;
			Optional<Package> optionalPackage = packageService.findById(payReponse.getPackage_id());
			Package package1 = optionalPackage.isPresent() ? optionalPackage.get() : null;
			if (package1 == null) {
				throw new NotFoundException("package not found");
			}
			if (user == null) {
				throw new NotFoundException("User not found");
			}

			Payment payment = service.createPayment(package1.getPrice(), "USD", "paypal",
					"sale", "test", "http://localhost:8080/" + CANCEL_URL,
					"http://localhost:8080/" + SUCCESS_URL);
			LocalDateTime startDate = LocalDateTime.now();
			LocalDateTime endDate = startDate.plus(Duration.ofDays(package1.getDuration()));
			OrderDetail orderDetail = new OrderDetail(startDate, endDate,
					(new Double(package1.getPrice())).floatValue(), 0);
			Order order = new Order(package1, user, orderDetail, false, payment.getId());
			orderdetailService.save(orderDetail);
			orderService.save(order);
			for (Links link : payment.getLinks()) {
				if (link.getRel().equals("approval_url")) {

					return "redirect:" + link.getHref();
				}
			}

		} catch (PayPalRESTException e) {

			e.printStackTrace();
		}
		return "redirect:/";
	}

	@GetMapping(value = CANCEL_URL)
	public String cancelPay() {
		return "cancel";
	}

	@GetMapping(value = SUCCESS_URL)
	public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
		try {
			Payment payment = service.executePayment(paymentId, payerId);
			System.out.println(payment.toJSON());

			Optional<Order> optionalOrder = orderService.findByOrderCode(payment.getId());
			Order order1 = optionalOrder.isPresent() ? optionalOrder.get() : null;
			if (order1 == null) {
				throw new NotFoundException("package not found");
			}
			order1.setOrderStatus(true);
			orderService.save(order1);

		
			Set<Role> roles = new HashSet<>();
			Role adminRole = roleService.findByName(RoleName.ADMIN)
					.orElseThrow(() -> new RuntimeException("Role not found"));
			roles.add(adminRole);

			Users user=new Users();
			user.setRoles(roles);
			userService.save(user);

			userService.save(null);

			if (payment.getState().equals("approved")) {
				return "success";
			}
		} catch (PayPalRESTException e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/";
	}

}
