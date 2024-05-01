package att.g5.hopital_app_attouch.security.service;

import att.g5.hopital_app_attouch.security.entities.AppRole;
import att.g5.hopital_app_attouch.security.entities.AppUser;
import att.g5.hopital_app_attouch.security.repo.AppRoleRepository;
import att.g5.hopital_app_attouch.security.repo.AppUserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private AppRoleRepository appRoleRepository;
    private AppUserRepository appUserRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
        AppUser user = appUserRepository.findByUserName(username);
        if(user!=null) throw new RuntimeException("This user already exists");
        if(!password.equals(confirmPassword)) throw new RuntimeException("Password not match");
        user = AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .userName(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
        AppUser savedUser = appUserRepository.save(user);
        return savedUser;
    }

    @Override
    public AppRole addNewRole(String role) {
        AppRole role1 = appRoleRepository.findById(role).orElse(null);
        if(role1!=null) throw new RuntimeException("this role already exists");
        role1 = AppRole.builder()
                .role(role)
                .build();
        return appRoleRepository.save(role1);
    }

    @Override
    public void addRoleToUser(String username, String role) {
         AppUser appUser = appUserRepository.findByUserName(username);
         AppRole appRole = appRoleRepository.findById(role).get();
         appUser.getRoles().add(appRole);
    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUserName(username);
        AppRole appRole = appRoleRepository.findById(role).get();
        appUser.getRoles().remove(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUserName(username);
    }
}
