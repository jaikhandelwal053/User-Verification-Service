package com.user.verification.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	
	private String name;
    private int age;
    private String gender;
    private String dob;
    private String nationality;
    private String verificationStatus;

}
