package com.gbf.auth;

import com.gbf.auth.repository.AdminRepository;
import com.gbf.auth.repository.ClientRepository;
import com.gbf.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AuthApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AdminRepository adminRepository;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) throws Exception {
       //KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
         /*System.out.println("keyPair = " + keyPair);
        System.out.println(Arrays.toString(keyPair.getPublic().getEncoded()));
        String publicKeyString = javax.xml.bind.DatatypeConverter.printBase64Binary(keyPair.getPublic().getEncoded());
        System.out.println("publicKeyString = " + publicKeyString);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        factory.*/


    }
}
