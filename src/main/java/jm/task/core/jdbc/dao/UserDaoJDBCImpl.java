package jm.task.core.jdbc.dao;
import java.sql.Connection;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    private final Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS USERS (id BIGINT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(45), LastName VARCHAR(45), age SMALLINT NOT NULL, PRIMARY KEY (id))";

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE USERS");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        String sql = "INSERT INTO USERS (NAME, LASTNAME, AGE) VALUES (?, ?, ?)";

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, name);
            pst.setString(2, lastName);
            pst.setByte(3, age);
            pst.executeUpdate();
            System.out.println(name + " " + lastName + " добавлен(а) в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void removeUserById(long id) {

        String sql = "DELETE FROM USERS WHERE ID = ?";

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setLong(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        String sql = "SELECT ID, NAME, LASTNAME, AGE FROM USERS";

        try (Statement st = connection.createStatement(); ResultSet resultSet =
                st.executeQuery(sql)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("Id"));
                user.setName(resultSet.getString("Name"));
                user.setLastName(resultSet.getString("LastName"));
                user.setAge(resultSet.getByte("Age"));

                userList.add(user);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

        @Override
        public void cleanUsersTable () {
            try (Statement statement = connection.createStatement()) {
                statement.execute("Truncate USERS");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
