package com.majumundurcompany.onlineclothingmarketplace.service;

import com.majumundurcompany.onlineclothingmarketplace.constant.ERole;
import com.majumundurcompany.onlineclothingmarketplace.entity.Role;

public interface RoleService {
    Role getOrSave(ERole role);
}
