package com.murilo.portariaApp.Service;

import com.murilo.portariaApp.Entity.User;
import com.murilo.portariaApp.dto.user.UserRequestDTO;

import java.util.UUID;

public interface UserService {
    User create(UserRequestDTO request);

    User update(UUID id, UserRequestDTO request);

    void delete(UUID id);

    User findById(UUID id);
}
