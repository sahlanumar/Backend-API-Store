package Group3.CourseApp.Service;


import Group3.CourseApp.constant.UserRole;
import Group3.CourseApp.entity.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(UserRole name);

    Role save(Role role);

    Role getOrCreate(UserRole name);
}
