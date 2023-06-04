package com.example.alianza.infrastructure.Controller;

import com.example.alianza.Application.UserService;
import com.example.alianza.Domain.Model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;



    @PostMapping("create")
    public ResponseEntity<User> create(@RequestBody User user){
        User user1 = userService.create(user);
        if (user1.getName() == null){
            System.out.println("El usuario ya existe");
           return new ResponseEntity<>( user1 , HttpStatus.BAD_REQUEST) ;
        }else {
            return new ResponseEntity<>( user1, HttpStatus.OK);
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<User>> getAll(){
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("sharedKey/{sharedKey}")
    public ResponseEntity<User> getBySharedKey(@PathVariable String sharedKey){
        return new ResponseEntity<>(userService.getBySharedKey(sharedKey), HttpStatus.OK);
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> descargarCsv() throws IOException {

        byte[] archivoExcel = userService.downloadFile();
        ByteArrayResource resource = new ByteArrayResource(archivoExcel);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cvc.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(archivoExcel.length)
                .body(resource);

    }


}
