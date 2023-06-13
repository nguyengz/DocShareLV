package com.example.demo.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.Config;
import com.example.demo.dto.request.PaymentForm;
import com.example.demo.dto.response.ResponseMessage;
import com.example.demo.model.Order;
import com.example.demo.model.OrderDetail;
import com.example.demo.model.Package;
import com.example.demo.model.Role;
import com.example.demo.model.RoleName;
import com.example.demo.model.Users;
import com.example.demo.service.OrderService;
import com.example.demo.service.OrderdetailService;
import com.example.demo.service.PackageService;
import com.example.demo.service.impl.UserServiceImpl;

@RestController
@RequestMapping("api/payment")
public class PaymentController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    PackageService packageService;

    @Autowired
    OrderdetailService orderdetailService;

    @Autowired
    OrderService orderService;

    @GetMapping("/create_payment")
    public ResponseEntity<?> createPayMent() throws UnsupportedEncodingException {
        Long user_id = (long) 1;
        Long package_id = (long) 1;
        Optional<Users> optionalUser = userService.findById(user_id);
        Users user = optionalUser.isPresent() ? optionalUser.get() : null;
        Optional<Package> optionalPackage = packageService.findById(package_id);
        Package package1 = optionalPackage.isPresent() ? optionalPackage.get() : null;
        if (package1 == null) {
            throw new NotFoundException("package not found");
        }
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        // String orderType = req.getParameter("ordertype");
        // long amount = Integer.parseInt(req.getParameter("amount"))*100;
        // String bankCode = req.getParameter("bankCode");

        String vnp_TxnRef = Config.getRandomNumber(8);
        // String vnp_IpAddr = Config.getIpAddress(req);
        String vnp_TmnCode = Config.vnp_TmnCode;

        long amount = package1.getPrice();

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", Config.vnp_Version);
        vnp_Params.put("vnp_Command", Config.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);// vnp_TxnRef Mã đơn hàng
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

        PaymentForm paymentForm = new PaymentForm();
        paymentForm.setStatus("Ok");
        paymentForm.setMessage("Successfully");
        paymentForm.setURL(paymentUrl);

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plus(Duration.ofDays(package1.getDuration()));
        OrderDetail orderDetail = new OrderDetail(startDate, endDate, amount, 0);
        Order order = new Order(package1, user, orderDetail, false, vnp_TxnRef);
        orderdetailService.save(orderDetail);
        orderService.save(order);

        return ResponseEntity.status(HttpStatus.OK).body(paymentForm);
    }

    @GetMapping("payment_infor")
    public ResponseEntity<?> transation(
            @RequestParam(value = "vnp_Amount") String amount,
            @RequestParam(value = "vnp_BankCode") String bankcode,
            @RequestParam(value = "vnp_OrderInfo") String order,
            @RequestParam(value = "vnp_ResponseCode") String responseCode,
            @RequestParam(value = "vnp_TxnRef") String vnp_TxnRef) {
        ResponseMessage message = new ResponseMessage();
        if (responseCode.equals("00")) {
            Optional<Order> optionalOrder = orderService.findByOrderCode(vnp_TxnRef);
            Order order1 = optionalOrder.isPresent() ? optionalOrder.get() : null;
            if (order1 == null) {
                throw new NotFoundException("package not found");
            }

        //    if(order1.getPackages().getId()==1){
        //     Set<Role> roles =  order1.getUser().getRoles();
        //     RoleName roleName = RoleName.ADMIN;
        //     Optional<Role> optionalRole = roles.stream()
        //             .filter(role -> role.getName() == roleName)
        //             .findFirst();
        //     if (optionalRole.isPresent()) {
        //         Role role = optionalRole.get();
        //         role.setCreatedAt(role.getCreatedAt().plusMinutes(10));
        //         roleRepository.save(role);
        //     } else {
        //         Role role = new Role(roleName);
        //         role.setCreatedAt(LocalDateTime.now());
        //         roles.add(role);
        //         userRepository.save(user);
        //     }}else{

        //     }

            order1.setOrderStatus(true);
            orderService.save(order1);
            message.setStatus("Ok");
            message.setMessage("Successfully");
            message.setData(order);
        } else {
            message.setStatus("No");
            message.setMessage("Failed");
            message.setData("");
        }
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    // Package Controller

    @GetMapping("/package/list")
    public ResponseEntity<List<Package>> getAllPackages() {
        try {
            List<Package> packages = packageService.getAllPackages();
            return new ResponseEntity<>(packages, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
