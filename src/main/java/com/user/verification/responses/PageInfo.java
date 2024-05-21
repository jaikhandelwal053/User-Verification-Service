package com.user.verification.responses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo {
    private boolean hasNextPage;
    private boolean hasPreviousPage;
    private long total;
}

