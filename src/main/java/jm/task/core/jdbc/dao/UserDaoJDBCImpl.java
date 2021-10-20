package jm.task.core.jdbc.dao;
import java.sql.*;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {



    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        Connection connection = Util.getConnection();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS `users`.`users`");
            statement.executeUpdate("CREATE TABLE " +
                    "IF NOT EXISTS `users`.`users`" +
                    "(\n" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                    "name VARCHAR(30),\n" +
                    "lastName VARCHAR(30),\n" +
                    "age TINYINT\n" +
                    ")");

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.rollback();
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Connection connection = Util.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS `users`.`users`");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Таблица удалена");
        } finally {
            try {
                connection.rollback();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Connection connection = Util.getConnection();
        PreparedStatement statement = null;

        try {
           statement = connection.prepareStatement("INSERT INTO `users` " +
                    "(NAME, LASTNAME, AGE) " +
                    "VALUES (?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);

            statement.execute();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.rollback();
                connection.close();
                statement.close();
                System.out.println(name + " " + lastName + " добавлен(а) в базу данных");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Connection connection = Util.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            long rew = statement.executeUpdate("DELETE FROM `users` WHERE Id =" + id);
            System.out.println(rew);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.rollback();
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public List<User> getAllUsers() {
        Connection connection = Util.getConnection();
        List<User> users = new ArrayList<>();

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `users`");
            while (resultSet.next()) {
                long id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                user.setId(id);
                users.add(user);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.rollback();
                connection.close();
                statement.close();
                resultSet.close();
                for (User user : users) {
                    System.out.println(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable () {
        Connection connection = Util.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE users");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.rollback();
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
