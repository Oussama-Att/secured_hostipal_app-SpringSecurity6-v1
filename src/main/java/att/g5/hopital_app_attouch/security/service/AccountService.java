package att.g5.hopital_app_attouch.security.service;

import att.g5.hopital_app_attouch.security.entities.AppRole;
import att.g5.hopital_app_attouch.security.entities.AppUser;

public interface AccountService {
    AppUser addNewUser(String username,String password,String email,String confirmPassword);
    AppRole addNewRole(String role);
    void addRoleToUser(String username,String role);
    void removeRoleFromUser(String username,String role);
    AppUser loadUserByUsername(String username);
}
