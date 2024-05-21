package com.user.verification.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWithPageInfoResponse {

    private UserResponse users;
    private PageInfo pageInfo;
}
