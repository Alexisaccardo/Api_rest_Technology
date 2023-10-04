package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controlador {
    @PostMapping("/register_product")
    public Technology register_product(@RequestBody Technology technology) throws SQLException, ClassNotFoundException {

        String code = technology.getCode();
        String name = technology.getName();
        String type = technology.getType();
        String characteristics = technology.getCharacteristics();
        String amount = technology.getAmount();
        String value = technology.getValue();

        if (code == null || code.equals("") || code.length() < 0 || name == null || name.equals("") || name.length() < 0 ||
                type == null || type.equals("") || type.length() < 0 || characteristics == null || characteristics.equals("")
                || characteristics.length() < 0 || amount == null || amount.equals("") || amount.length() < 0 ||
                value == null || value.equals("") || value.length() < 0) {

            return new Technology(null, null, null, null, null, null);
        } else {
            if (type.equalsIgnoreCase("computador") || type.equalsIgnoreCase("celular")) {
                technology = Register(code, name, type, characteristics, amount, value);
            } else {
                return new Technology(null, null, null, null, null, null);
            }
        }

        return technology;
    }

    private Technology Register(String code, String name, String type, String characteristics, String amount, String value) {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/technology";
        String username = "root";
        String password = "";

        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM products");

            // Sentencia INSERT
            String sql = "INSERT INTO products (code, name, type, characteristics, amount, value) VALUES (?, ?, ?, ?, ?, ?)";

            // Preparar la sentencia
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, code);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, type);
            preparedStatement.setString(4, characteristics);
            preparedStatement.setString(5, amount);
            preparedStatement.setString(6, value);

            // Ejecutar la sentencia
            int files = preparedStatement.executeUpdate();

            if (files > 0) {
                System.out.println("Producto registrado de manera exitosa.");
                return new Technology(code, name, type, characteristics, amount, value);
            } else {
                System.out.println("No se pudo registrar el producto");
            }

            preparedStatement.close();
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Technology(null, null, null, null, null, null);
    }

    @PostMapping("/edit_product")
    public Technology edit_technology(@RequestBody Technology technology) throws SQLException, ClassNotFoundException {

        String code = technology.getCode();
        String name = technology.getName();
        String type = technology.getType();
        String characteristics = technology.getCharacteristics();
        String amount = technology.getAmount();
        String value = technology.getValue();

        if (code == null || code.equals("") || code.length() < 0 || name == null || name.equals("") || name.length() < 0 ||
                type == null || type.equals("") || type.length() < 0 || characteristics == null || characteristics.equals("")
                || characteristics.length() < 0 || amount == null || amount.equals("") || amount.length() < 0 ||
                value == null || value.equals("") || value.length() < 0) {

            return new Technology(null, null, null, null, null, null);
        } else {
            if (type.equalsIgnoreCase("celular")) {
                technology = Edit(code, name, type, characteristics, amount, value);
            } else {
                return new Technology(null, null, null, null, null, null);
            }
        }
        return technology;
    }

    private Technology Edit(String code, String name, String type, String characteristics, String amount, String value) throws ClassNotFoundException, SQLException {

        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/technology";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        Statement statement2 = connection2.createStatement();

        String consult = "UPDATE products SET name = ?, type = ?, characteristics = ?, amount = ?, value = ? WHERE code = ?";
        PreparedStatement preparedStatement = connection2.prepareStatement(consult);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, type);
        preparedStatement.setString(3, characteristics);
        preparedStatement.setString(4, amount);
        preparedStatement.setString(5, value);
        preparedStatement.setString(6, code);

        int files = preparedStatement.executeUpdate();
        if (files > 0) {
            System.out.println("Producto actualizado de manera exitosa");
            return new Technology(code, name, type, characteristics, amount, value);
        } else {
            System.out.println("No se encontro el producto para actualizar");
        }

        preparedStatement.close();
        connection2.close();
        return new Technology(null, null, null, null, null, null);
    }

    @DeleteMapping("/delete_product")

    public Technology delete_product(@RequestBody Technology technology) throws SQLException, ClassNotFoundException {
        String code = technology.getCode();

        if (technology.getCode() == null || technology.getCode().equals("") || technology.getCode().length() < 0) {
            return new Technology(null, null, null, null, null, null);
        } else {

            int f = Delete(code);
            if (f == 0) {
                return new Technology("No se encuentra un productos para eliminar", null, null, null, null, null);
            } else {

            }
        }

        return technology;
    }

    private int Delete(String code) throws ClassNotFoundException, SQLException {

        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/technology";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        String sentenciaSQL = "DELETE FROM products WHERE code = ?";
        PreparedStatement prepareStatement = connection2.prepareStatement(sentenciaSQL);
        prepareStatement.setString(1, code);
        int f = prepareStatement.executeUpdate();

        System.out.println("producto eliminado correctamente");
        return f;
    }

    @GetMapping("/search_computer")
    public List<Technology> search_computer() throws SQLException, ClassNotFoundException {

        List<Technology> list = Select_consult();

        return list;
    }

    private List<Technology> Select_consult() throws ClassNotFoundException, SQLException {
        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/technology";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        Statement statement2 = connection2.createStatement();

        ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM products");

        List<Technology> list = new ArrayList<>();

        while (resultSet2.next()) {
            String code = resultSet2.getString("code");
            String name = resultSet2.getString("name");
            String type = resultSet2.getString("type");
            String characteristics = resultSet2.getString("characteristics");
            String amount = resultSet2.getString("amount");
            String value = resultSet2.getString("value");

            if (type.equalsIgnoreCase("computador")) {

                Technology technology = new Technology(code, name, type, characteristics, amount, value);

                list.add(technology);
            }
        }
        return list;

    }

    @GetMapping("/search_one/{code}")
    public Technology search_one_cell(@PathVariable String code) throws SQLException, ClassNotFoundException {

        Technology technology;

        if (code == null || code.equals("") || code.length() < 0) {

            return new Technology("No se encuentra un producto con el codigo ingresado", null, null, null, null, null);

        } else {

            technology = Select_user(code);
        }


        return technology;
    }

    private Technology Select_user(String code) throws ClassNotFoundException, SQLException {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/technology";
        String username = "root";
        String password = "";

        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);

        String consult_SQL = "SELECT * FROM products WHERE code = ?";

        PreparedStatement statement = connection.prepareStatement(consult_SQL);
        statement.setString(1, code); // Establecer el valor del parámetro

        // Ejecutar la consulta
        ResultSet resultSet2 = statement.executeQuery();

        // Procesar el resultado si existe
        if (resultSet2.next()) {

            code = resultSet2.getString("code");
            String name = resultSet2.getString("name");
            String type = resultSet2.getString("type");
            String characteristics = resultSet2.getString("characteristics");
            String amount = resultSet2.getString("amount");
            String value = resultSet2.getString("value");

            if (type.equalsIgnoreCase("celular")) {
                Technology technology = new Technology(code, name, type, characteristics, amount, value);

                return technology;
            }
        }
        // Cerrar recursos
        resultSet2.close();
        statement.close();
        connection.close();

        return null;
    }
}