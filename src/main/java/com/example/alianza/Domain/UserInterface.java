package com.example.alianza.Domain;

import com.example.alianza.Domain.Model.User;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserInterface {
    User save(User user);
    List<User>getAll();
    User getBySharedKey(String sharedKey);
    Optional<User> getByNombre(String nombre);
    byte[] downloadFile() throws IOException;



}
