package com.majumundurcompany.onlineclothingmarketplace.service;

import com.majumundurcompany.onlineclothingmarketplace.entity.User;

public interface UserService {
    User loadUserById(String userId);
    User update(User user);
}
