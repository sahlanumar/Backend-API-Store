package Group3.CourseApp.mapper;

import Group3.CourseApp.dto.response.UserResponse;
import Group3.CourseApp.entity.Role;
import Group3.CourseApp.entity.User;

import java.util.stream.Collectors;

public class UserMapper {
    public static UserResponse toUserResponse(User user) {
        Role role = user.getRoles().stream().findFirst().get();
        return UserResponse.builder()
                .id(user.getUserId())
                .name(user.getUsername())
                .email(user.getEmail())
                .role(role.getName().name())
                .build();
    }
}
