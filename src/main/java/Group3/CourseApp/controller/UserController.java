package Group3.CourseApp.controller;

import Group3.CourseApp.Service.UserService;
import Group3.CourseApp.constant.ApiEndpoint;
import Group3.CourseApp.constant.UserRole;
import Group3.CourseApp.dto.request.UserUpdateRequest;
import Group3.CourseApp.dto.response.CommonResponse;
import Group3.CourseApp.dto.response.UserResponse;
import Group3.CourseApp.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<UserResponse>> getUserById(@PathVariable String id) {
        UserResponse userResponse = userService.getUserById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success", userResponse);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<UserResponse>>> findAll(
            @RequestParam(required = false) UserRole role,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(defaultValue = "username,asc") String[] sort) {
        String sortField = sort[0];
        String sortDirection = (sort.length > 1) ? sort[1] : "asc";

        Page<UserResponse> userResponsePage = userService.findAll(role, page, size, sortField, sortDirection);
        return  ResponseUtil.buildResponse(HttpStatus.OK, "Success", userResponsePage.getContent(), userResponsePage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<UserResponse>> update(@PathVariable String id, @RequestBody UserUpdateRequest userUpdateRequest) {
        UserResponse userResponse = userService.update(id, userUpdateRequest);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success", userResponse);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<UserResponse>> updateByLogin( @RequestBody UserUpdateRequest userUpdateRequest) {
        UserResponse userResponse = userService.updateByLogin( userUpdateRequest);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success", userResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<UserResponse>> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Success", null);
    }


}
