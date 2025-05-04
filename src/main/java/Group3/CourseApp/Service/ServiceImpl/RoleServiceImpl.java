package Group3.CourseApp.Service.ServiceImpl;


import Group3.CourseApp.Service.RoleService;
import Group3.CourseApp.constant.UserRole;
import Group3.CourseApp.entity.Role;
import Group3.CourseApp.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findByName(UserRole name) {
        return roleRepository.findByName(name);
    }

    @Override
    @Transactional
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public Role getOrCreate(UserRole name) {
        return findByName(name)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(name);
                    return save(newRole);
                });
    }
}
