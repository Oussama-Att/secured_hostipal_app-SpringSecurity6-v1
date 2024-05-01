package att.g5.hopital_app_attouch.security.service;

import att.g5.hopital_app_attouch.security.entities.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Chargez les détails de l'utilisateur à partir du AccountService en utilisant le nom d'utilisateur fourni
        AppUser appUser = accountService.loadUserByUsername(username);

        // Si les détails de l'utilisateur ne sont pas trouvés, lancez une UsernameNotFoundException
        if (appUser == null) {
            throw new UsernameNotFoundException(String.format("Utilisateur %s non trouvé", username));
        }

        // Extrait les rôles de l'utilisateur et les convertit en un tableau de chaînes
        String[] roles = appUser.getRoles().stream().map(u -> u.getRole()).toArray(String[]::new);

        // Crée un objet UserDetails en utilisant les détails de l'utilisateur récupérés
        UserDetails userDetails = User
                .withUsername(appUser.getUserName())  // Définissez le nom d'utilisateur
                .password(appUser.getPassword())      // Définissez le mot de passe
                .roles(roles)                         // Définissez les rôles
                .build();                             // Construisez l'objet UserDetails

        return userDetails;
    }
}
