package Group3.CourseApp.Service;



import Group3.CourseApp.constant.UserRole;
import Group3.CourseApp.dto.request.UserUpdateRequest;
import Group3.CourseApp.dto.response.UserResponse;
import Group3.CourseApp.entity.User;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {
    User save(User user);

    Optional<User> findByUsername(String username);

    User findById(String id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Page<UserResponse> findAll(UserRole role, int page, int size, String sortField, String sortDirection);

    UserResponse update(String id, UserUpdateRequest userUpdateRequest);

    UserResponse updateByLogin(UserUpdateRequest userUpdateRequest);

    void delete(String id);

    UserResponse getUserById(String id);



}

