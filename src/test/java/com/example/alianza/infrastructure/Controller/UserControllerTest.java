package com.example.alianza.infrastructure.Controller;

import com.example.alianza.Application.UserService;
import com.example.alianza.Domain.Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    private UserController userController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);
    }


    @Test
    public void testCreateUser_Success() {
        User user = new User();
        user.setSharedKey("John Doe");
        user.setName("John Doe");
        user.setPhone(213123l);
        user.setEmail("John Doe");

        Mockito.when(userService.create(Mockito.any(User.class))).thenReturn(user);


        ResponseEntity<User> response = userController.create(user);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testCreateUser_UserAlreadyExists() {
        User userI = new User();
        userI.setSharedKey("John Doe");
        userI.setName("John Doe");
        userI.setPhone(213123l);
        userI.setEmail("John Doe");
        User user = new User();
        Mockito.when(userService.create(Mockito.any(User.class))).thenReturn(user);

        ResponseEntity<User> response = userController.create(userI);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        User user = new User();
        user.setSharedKey("John Doe");
        user.setName("John Doe");
        user.setPhone(213123l);
        user.setEmail("John Doe");
        users.add(user);
        Mockito.when(userService.getAll()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    public void testGetUserBySharedKey() {
        String sharedKey = "abc123";
        User user = new User();
        user.setSharedKey(sharedKey);
        Mockito.when(userService.getBySharedKey(sharedKey)).thenReturn(user);

        ResponseEntity<User> response = userController.getBySharedKey(sharedKey);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testDescargarCsv() throws IOException {
        byte[] archivoExcel = "datos del archivo".getBytes();
        ByteArrayResource resource = new ByteArrayResource(archivoExcel);
        Mockito.when(userService.downloadFile()).thenReturn(archivoExcel);

        ResponseEntity<ByteArrayResource> response = userController.descargarCsv();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resource, response.getBody());
        assertEquals("attachment; filename=cvc.xlsx", response.getHeaders().get(HttpHeaders.CONTENT_DISPOSITION).get(0));
        assertEquals(MediaType.APPLICATION_OCTET_STREAM, response.getHeaders().getContentType());
        assertEquals(archivoExcel.length, response.getHeaders().getContentLength());
    }


}