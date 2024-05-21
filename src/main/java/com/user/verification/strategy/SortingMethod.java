package com.user.verification.strategy;

import org.springframework.stereotype.Component;

import com.user.verification.exception.CustomErrorException;

@Component
public class SortingMethod {

	public SortingStrategy getStrategy(String sortType, String sortOrder) {
        try {
            if ("Name".equalsIgnoreCase(sortType) && "EVEN".equalsIgnoreCase(sortOrder)) {
                return new NameEvenSorting();
            } else if ("Name".equalsIgnoreCase(sortType) && "ODD".equalsIgnoreCase(sortOrder)) {
                return new NameOddSorting();
            } else if ("Age".equalsIgnoreCase(sortType) && "EVEN".equalsIgnoreCase(sortOrder)) {
                return new AgeEvenSorting();
            } else if ("Age".equalsIgnoreCase(sortType) && "ODD".equalsIgnoreCase(sortOrder)) {
                return new AgeOddSorting();
            } else {
                throw new CustomErrorException("Invalid sortType or sortOrder", "400 BAD_REQUEST");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error in SortingStrategyFactory: " + e.getMessage());
            throw e; 
        }
    }
}
