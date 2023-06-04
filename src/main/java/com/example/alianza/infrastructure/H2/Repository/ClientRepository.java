package com.example.alianza.infrastructure.H2.Repository;

import com.example.alianza.Domain.Model.User;
import com.example.alianza.Domain.UserInterface;
import com.example.alianza.infrastructure.H2.Entity.ClientEntity;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public class ClientRepository implements UserInterface {

    @Autowired
    private ClientCrudRepository clientCrudRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public User save(User user) {
        ClientEntity clientEntity = modelMapper.map (user, ClientEntity.class);
        System.out.println(clientEntity);
        clientCrudRepository.save(clientEntity);
        return user;
    }

    @Override
    public List<User> getAll() {
        List<ClientEntity> result = (List<ClientEntity>) clientCrudRepository.findAll();
        return  result.stream().map(
                user -> modelMapper.map(user, User.class)
                ).collect(Collectors.toList());
    }

    @Override
    public User getBySharedKey(String sharedKey) {
        return modelMapper.map(clientCrudRepository.findBySharedKey(sharedKey), User.class);
    }

    @Override
    public Optional<User> getByNombre(String nombre) {
        return clientCrudRepository.findByName(nombre).map(
                user -> modelMapper.map(user, User.class));
    }




    public byte[] downloadFile() throws IOException {
        List<ClientEntity> result = (List<ClientEntity>) clientCrudRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Clientes");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Shared Key");
            headerRow.createCell(2).setCellValue("Nombre");
            headerRow.createCell(3).setCellValue("Phone");
            headerRow.createCell(4).setCellValue("Email");
            headerRow.createCell(5).setCellValue("Start Date");
            headerRow.createCell(6).setCellValue("End Date");

            int rowNum = 1;
            for (ClientEntity clientEntity : result) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(clientEntity.getId());
                row.createCell(1).setCellValue(clientEntity.getSharedKey());
                row.createCell(2).setCellValue(clientEntity.getName());
                row.createCell(3).setCellValue(clientEntity.getPhone());
                row.createCell(4).setCellValue(clientEntity.getEmail());
                row.createCell(5).setCellValue(clientEntity.getStartDate());
                row.createCell(6).setCellValue(clientEntity.getEndDate());
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }

    }
}