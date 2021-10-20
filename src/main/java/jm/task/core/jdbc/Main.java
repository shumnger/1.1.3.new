package jm.task.core.jdbc;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;


public class Main {

       public static void main(String[] args)  {
        // реализуйте алгоритм здесь
        UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();
        userDaoJDBC.createUsersTable();
        userDaoJDBC.saveUser("Ivan", "Dub4ak", (byte)34);
        userDaoJDBC.saveUser("Aleksandr", "Akimov", (byte)31);
        userDaoJDBC.saveUser("Timyr", "Vergush", (byte)40);
        userDaoJDBC.saveUser("Svyatoslav", "Panifidkin", (byte)29);

        userDaoJDBC.getAllUsers();
        userDaoJDBC.cleanUsersTable();
        userDaoJDBC.dropUsersTable();
    }
}