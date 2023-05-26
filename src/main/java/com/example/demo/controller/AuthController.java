package com.example.demo.controller;

import com.example.demo.dto.request.FollowForm;
import com.example.demo.dto.request.SignInForm;
import com.example.demo.dto.request.SignUpForm;
import com.example.demo.dto.response.FriendResponse;
import com.example.demo.dto.response.JwtResponse;
import com.example.demo.dto.response.ResponseMessage;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.model.Role;
import com.example.demo.model.RoleName;
import com.example.demo.model.Users;
import com.example.demo.security.jwt.JwtProvider;
import com.example.demo.security.userpincal.UserPrinciple;
import com.example.demo.service.impl.RoleServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;
import com.mysql.cj.result.Field;

import net.bytebuddy.utility.RandomString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm,HttpServletRequest request) 
    throws MessagingException, UnsupportedEncodingException {
        if(userService.existsByUsername(signUpForm.getUsername())){
            return new ResponseEntity<>(new ResponseMessage("The username is existed"), HttpStatus.OK);
        }
        if(userService.existsByEmail(signUpForm.getEmail())){
            return new ResponseEntity<>(new ResponseMessage("The email is existed"), HttpStatus.OK);
        }
        Users users = new Users(signUpForm.getName(), signUpForm.getUsername(), signUpForm.getEmail(), passwordEncoder.encode(signUpForm.getPassword()));
        Set<String> strRoles = signUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        // strRoles.forEach(role ->{
        //     switch (role){
        //         case "admin":
        //             Role adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow( ()-> new RuntimeException("Role not found"));
        //             roles.add(adminRole);
        //             break;
        //         case "pm":
        //             Role pmRole = roleService.findByName(RoleName.PM).orElseThrow( ()-> new RuntimeException("Role not found"));
        //             roles.add(pmRole);
        //             break;
        //         default:
        //             Role userRole = roleService.findByName(RoleName.USER).orElseThrow( ()-> new RuntimeException("Role not found"));
        //             roles.add(userRole);
        //     }
        // });
        users.setAvatar("https://thuvienlogo.com/data/01/logo-con-gau-08.jpg");
        Role adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow( ()-> new RuntimeException("Role not found"));
        roles.add(adminRole);
        String randomCode = RandomString.make(64);
		users.setVerificationCode(randomCode);
		users.setEnabled(false);
        users.setRoles(roles);
        userService.register(users,getSiteURL(request));
        return new ResponseEntity<>(new ResponseMessage("Create success!"), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@Valid @RequestBody SignInForm signInForm){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.createToken(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(token, userPrinciple.getName(), userPrinciple.getAuthorities()));
    }

    private String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

    @PostMapping("/follow")
    public ResponseEntity<?> follow(@RequestBody FollowForm followForm,HttpServletRequest request) 
    throws MessagingException, UnsupportedEncodingException {
      
        Optional<Users> optionalfriend =  userService.findById(followForm.getFriend_id());
        Optional<Users> optionalUser = userService.findById(followForm.getUser_id());
        Users user = optionalUser.isPresent() ? optionalUser.get() : null; // lấy giá trị trong optional và gán cho user, hoặc gán giá trị null nếu optional rỗng.
        if (user == null) {
            throw new NotFoundException("User not found");
        }
  
        Users friend = optionalfriend.isPresent() ? optionalfriend.get() : null; // lấy giá trị trong optional và gán cho user, hoặc gán giá trị null nếu optional rỗng.
        if (friend == null) {
            throw new NotFoundException("friend not found");
        }

        user.getFriends().add(friend);
        userService.save(user);
        return new ResponseEntity<>(new ResponseMessage("Create success!"), HttpStatus.OK);
    }
    
    
    @GetMapping("/users")
    public List<Users> getAllUsers() {
        // Gọi method getAllUsers() trong service class để lấy danh sách tất cả người dùng
        return userService.getAllUser();
    }


    @GetMapping("/user")
    public ResponseEntity<?> getUser(@RequestBody FollowForm followForm) {
          Optional<Users> optionalUser = userService.findById(followForm.getUser_id());
          Users user = optionalUser.isPresent() ? optionalUser.get() : null; // lấy giá trị trong optional và gán cho user, hoặc gán giá trị null nếu optional rỗng.
          if (user == null) {
              throw new NotFoundException("User not found");
          }
          List<FriendResponse> friendDTOs = user.getFriends().stream().map(FriendResponse::new).collect(Collectors.toList());
        return new ResponseEntity<>(new UserResponse(user.getId(), user.getName(), user.getUsername(),user.getEmail() , user.getPassword(), user.getAvatar(), user.getFiles(),friendDTOs),HttpStatus.OK);
    }

}
