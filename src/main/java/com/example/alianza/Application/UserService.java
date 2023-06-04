package com.example.alianza.Application;

import com.example.alianza.Domain.Model.User;
import com.example.alianza.Domain.UserInterface;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    @Autowired
    private UserInterface userInterface;

    public User create(User user) {
        LOGGER.info("Creating user: {}", user);
        if ( getByNombre(user.getName()) ){
            LOGGER.error("User already exists: {}", user);
            User userVacio = new User();
            return userVacio;
        }else{
            LOGGER.info("User created: {}", user);
            String[] parts = user.getName().split(" ");
            String primeraLetra = parts[0].substring(0, 1);
            String apellido = parts[1];
            String sharedKey = primeraLetra + apellido ;
            user.setSharedKey(sharedKey.toLowerCase(Locale.ROOT));
            return userInterface.save(user);
        }
    }

    public List<User>getAll(){
        LOGGER.info("Getting all users");
        return userInterface.getAll();
    }
    public User getBySharedKey(String sharedKey){
        LOGGER.info("Getting user by sharedKey: {}", sharedKey);
        return userInterface.getBySharedKey(sharedKey);
    }

    private Boolean getByNombre(String nombre) {
        LOGGER.info("Getting user by nombre: {}", nombre);
        return userInterface.getByNombre(nombre).isPresent();
    }

   public byte[] downloadFile() throws IOException {
        LOGGER.info("Downloading file");
        return userInterface.downloadFile();
   }

}
