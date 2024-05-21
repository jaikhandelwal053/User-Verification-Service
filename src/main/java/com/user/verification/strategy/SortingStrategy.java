package com.user.verification.strategy;



import java.util.List;

import com.user.verification.models.User;

public interface SortingStrategy {
    List<User> sortingby(List<User> users);
}
