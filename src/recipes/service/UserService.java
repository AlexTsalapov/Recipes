package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.dto.UserDetailsImpl;
import recipes.entity.User;
import recipes.repo.UserRepo;

import java.util.Collection;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private UserRepo userRepo;

    public boolean registry(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        if (!userRepo.existsByEmail(user.getEmail())) {
            userRepo.save(user);
            return true;
        }
        return false;

    }
    public User findByUsername(String email){
        return userRepo.findUserByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findUserByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Not found:");
        }

        return new UserDetailsImpl(user);
    }
}