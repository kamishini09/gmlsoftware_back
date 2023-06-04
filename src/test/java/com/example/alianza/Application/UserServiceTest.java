package com.example.alianza.Application;

import com.example.alianza.Domain.Model.User;
import com.example.alianza.Domain.UserInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Mock
    private UserInterface userInterface;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate_UserDoesNotExist() {
        User user = new User();
        user.setName("John Doe");
        Mockito.when(userInterface.getByNombre("John Doe")).thenReturn(Optional.empty());
        Mockito.when(userInterface.save(Mockito.any(User.class))).thenReturn(user);

        User result = userService.create(user);

        assertEquals(user, result);
        Mockito.verify(userInterface, Mockito.times(1)).getByNombre("John Doe");
        Mockito.verify(userInterface, Mockito.times(1)).save(Mockito.any(User.class));
    }

    @Test
    public void testCreate_UserAlreadyExists() {
        User user = new User();
        user.setName("John Doe");
        Mockito.when(userInterface.getByNombre("John Doe")).thenReturn(Optional.of(user));

        User result = userService.create(user);

        assertEquals(new User(), result);
        Mockito.verify(userInterface, Mockito.times(1)).getByNombre("John Doe");
        Mockito.verify(userInterface, Mockito.never()).save(Mockito.any(User.class));
    }

    @Test
    public void testGetAll() {
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());
        Mockito.when(userInterface.getAll()).thenReturn(userList);

        List<User> result = userService.getAll();

        assertEquals(userList, result);
        Mockito.verify(userInterface, Mockito.times(1)).getAll();
    }


    @Test
    public void downloadFile() throws IOException {
        byte[] file = new byte[1];
        Mockito.when(userInterface.downloadFile()).thenReturn(file);

        byte[] result = userService.downloadFile();

        assertEquals(file, result);
        Mockito.verify(userInterface, Mockito.times(1)).downloadFile();
    }

    @Test
    public void getBySharedKey(){
        String sharedKey = "abc123";
        User user = new User();
        user.setSharedKey("John Doe");
        user.setName("John Doe");
        user.setPhone(213123l);
        user.setEmail("John Doe");
        Mockito.when(userInterface.getBySharedKey(sharedKey)).thenReturn(user);

        User result = userService.getBySharedKey(sharedKey);

        assertEquals(user, result);


        
    }
}