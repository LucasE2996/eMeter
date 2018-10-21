package emonitor.app.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private Integer id;
    @Getter @Setter private String username;
    @Getter private String email;
    @Getter @Setter private String password;

    public Client(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password  = password;
    }

    protected Client() {}
}
