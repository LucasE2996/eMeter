package emonitor.app.data;

import emonitor.app.database.ClientRepository;
import emonitor.app.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyData implements CommandLineRunner {

    private ClientRepository clientRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public MyData(ClientRepository clientRepository, PasswordEncoder passwordEncoder){
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... strings) throws Exception {
        List<Client> accountList = new ArrayList<>();
        accountList.add(new Client("Lucas Rosa", "lucas@teste.com", passwordEncoder.encode("adm")));
        accountList.add(new Client("Breno Moreira", "breno@teste.com", passwordEncoder.encode("adm")));
        accountList.add(new Client("Beatriz", "beatriz@teste.com", passwordEncoder.encode("adm")));
        accountList.add(new Client("Helen", "helen@teste.com", passwordEncoder.encode("adm")));

        clientRepository.saveAll(accountList);
    }
}
