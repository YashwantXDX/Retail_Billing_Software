package in.yashwant.billingsoftware.service;

import in.yashwant.billingsoftware.io.UserRequest;
import in.yashwant.billingsoftware.io.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest request);
    String getUserRole(String email);
    List<UserResponse> readUsers();
    void deleteUser(String id);

}
