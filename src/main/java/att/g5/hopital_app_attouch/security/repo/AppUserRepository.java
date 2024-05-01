package att.g5.hopital_app_attouch.security.repo;

import att.g5.hopital_app_attouch.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AppUserRepository extends JpaRepository<AppUser, String> {
    AppUser findByUserName(String username);
}
