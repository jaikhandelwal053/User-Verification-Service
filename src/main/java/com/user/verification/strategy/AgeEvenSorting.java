package com.user.verification.strategy;


import java.util.List;
import java.util.stream.Collectors;

import com.user.verification.models.User;

public class AgeEvenSorting implements SortingStrategy {

    @Override
    public List<User> sortingby(List<User> users) {
        //return users.stream()
         //       .sorted(Comparator.comparingInt(user -> user.getAge() % 2 == 0 ? 0 : 1))
          //      .collect(Collectors.toList());
    	return users.stream().sorted((u1, u2) -> Integer.compare(u1.getAge() % 2, u2.getAge() % 2)).collect(Collectors.toList());
    }
}
