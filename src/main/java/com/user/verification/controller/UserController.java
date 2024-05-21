package com.user.verification.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user.verification.responses.UserWithPageInfoResponse;
import com.user.verification.exception.CustomErrorException;
import com.user.verification.responses.UserResponse;
import com.user.verification.service.UserService;
import com.user.verification.service.ValidatorService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	private ValidatorService validatorService = ValidatorService.getInstance();

	@PostMapping
	public ResponseEntity<List<UserResponse>> createUser(@RequestParam(defaultValue = "1") int size) {
		if (!validatorService.validate(size)) {
			throw new CustomErrorException("Invalid size parameter", "400 BAD_REQUEST");
		}
		List<UserResponse> users = userService.createUsers(size);
		return ResponseEntity.ok(users);
	}

	@GetMapping
	public ResponseEntity<List<UserResponse>> getUsers(@RequestParam(required = true) String sortType,
			@RequestParam(required = true) String sortOrder, @RequestParam(defaultValue = "5") int limit,
			@RequestParam(defaultValue = "0") int offset) {

		if (!validatorService.validate(sortType) || !validatorService.validate(sortOrder)) {
			throw new CustomErrorException("Enter valid sortType or sortOrder", "400 BAD_REQUEST");
		}
		
		if (!validatorService.validate(limit) || !validatorService.validateoffset(offset)) {
			throw new CustomErrorException("Enter valid Limit or Offset", "400 BAD_REQUEST");
		}

		List<UserResponse> users = userService.getUsers(sortType, sortOrder, limit, offset);
		return ResponseEntity.ok(users);
	}

	@GetMapping("/listUser")
	public ResponseEntity<?> listAllUsers() {
		List<UserWithPageInfoResponse> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}
}
