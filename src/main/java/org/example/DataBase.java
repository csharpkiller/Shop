package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
    public void addUserToBase(User user){
        SqlConnect sqlConnect = new SqlConnect();
        sqlConnect.connect();
        Statement statement;
        try {
            statement = sqlConnect.getConnect().createStatement();
            String sql;

            sql = String.format("INSERT INTO users(firstname, lastname, email, address, phonenumber, password) VALUES('%s', '%s', '%s', '%s', '%d', '%s');",
                    user.getFirstName(), user.getLastName(), user.getEmail(), user.getAddress(), user.getPhoneNumber(), user.getPassword());
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sqlConnect.disconnect();
    }

    public void deleteIntoBase(User user){
        SqlConnect sqlConnect = new SqlConnect();
        sqlConnect.connect();
        Statement statement;
        try {
            statement = sqlConnect.getConnect().createStatement();
            String sql = String.format("DELETE FROM users WHERE firstname = '%s' AND email = '%s';", user.getFirstName(), user.getEmail());
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sqlConnect.disconnect();
    }

    public void createOrder(User user, int idProduct, int count){
        SqlConnect sqlConnect = new SqlConnect();
        sqlConnect.connect();
        Statement statement;
        try {
            statement = sqlConnect.getConnect().createStatement();
            String sql = String.format("SELECT * FROM users WHERE firstname='%s' AND email='%s';",
                    user.getFirstName(), user.getEmail());
            ResultSet resultSet = statement.executeQuery(sql);
            int userId = 0;
            while (resultSet.next()){
                userId = resultSet.getInt(1);
            }

            sql = String.format("SELECT * FROM product WHERE idProduct=%d;", idProduct);
            resultSet = statement.executeQuery(sql);

            int productCount = 0;
            while (resultSet.next()){
                productCount = resultSet.getInt(3);
            }

            if(count > productCount){
                System.out.println("Shop have only "+productCount + " products for product_number: " + idProduct);
                statement.close();
                sqlConnect.disconnect();
                return;
            }

            sql = String.format("UPDATE product SET productCount=%d WHERE idProduct=%d", productCount - count, idProduct);
            statement.execute(sql);

            sql = String.format("INSERT INTO order_info(userid, product_number, product_count, address) VALUES (%d, %d, %d, '%s');",
                    userId, idProduct, count, user.getAddress());
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sqlConnect.disconnect();
    }

    public void checkProductType(){
        SqlConnect sqlConnect = new SqlConnect();
        sqlConnect.connect();
        Statement statement;
        try {
            statement = sqlConnect.getConnect().createStatement();
            String sql = String.format("SELECT * FROM product_type;");
            ResultSet resultSet = statement.executeQuery(sql);
            int productId;
            String desc;
            while (resultSet.next()){
                productId = resultSet.getInt(1);
                desc = resultSet.getString(2);
                String answ = String.format("PRODUCT ID: %d PRODUCT DESC: %s", productId, desc);
                System.out.println(answ);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkCorrectPassword(String email, String password){
        SqlConnect sqlConnect = new SqlConnect();
        sqlConnect.connect();
        Statement statement;
        try {
            statement = sqlConnect.getConnect().createStatement();
            String sql = String.format("SELECT * FROM users WHERE email='%s';", email);
            ResultSet resultSet = statement.executeQuery(sql);
            String pass = null;
            while (resultSet.next()){
                pass = resultSet.getString(7);
            }
            if(pass.equals(password)) return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public User getUser(String email, String password){
        SqlConnect sqlConnect = new SqlConnect();
        sqlConnect.connect();
        Statement statement;
        User user = null;
        try {
            statement = sqlConnect.getConnect().createStatement();
            String sql = String.format("SELECT * FROM users WHERE email='%s';", email);
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                user = new User(resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5),
                        resultSet.getInt(6), resultSet.getString(7));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public void checkProduct(int typeNumber){
        SqlConnect sqlConnect = new SqlConnect();
        sqlConnect.connect();
        Statement statement;
        try {
            statement = sqlConnect.getConnect().createStatement();
            String sql;
            if(typeNumber!=0){
                sql = String.format("SELECT * FROM product WHERE productType=%d;", typeNumber);
            }else {
                sql = String.format("SELECT * FROM product;");
            }
            ResultSet resultSet = statement.executeQuery(sql);
            int productId;
            String name;
            int productCount;
            int productType;
            while (resultSet.next()){
                productId = resultSet.getInt(1);
                name = resultSet.getString(2);
                productCount = resultSet.getInt(3);
                productType = resultSet.getInt(4);
                String answ = String.format("PRODUCT ID: %d PRODUCT NAME: %s PRODUCT COUNT: %d PRODUCT TYPE: %d",
                        productId, name, productCount, productType);
                System.out.println(answ);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
