package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.TaskManager.DbUtil;

import java.sql.*;
import java.util.Arrays;

public class UserDAO {
    private static final String CREATE_USER_QUERY = "INSERT INTO users(username,email,password) VALUES (?,?,?)";
    private static final String READ_USER_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String FIND_ALL_USERS_QUERY = "SELECT * FROM users";

    public String hashPassword(String password){
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }
    public User create(User user){
        try(Connection connection = DbUtil.getConnection()){
            PreparedStatement statement = connection.prepareStatement(CREATE_USER_QUERY,Statement.RETURN_GENERATED_KEYS );
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3,hashPassword(user.getPassword()));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                user.setId(resultSet.getInt(1));
            }
            return user;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    public User read(int userId){
        try (Connection connection = DbUtil.getConnection()){
            PreparedStatement statement = connection.prepareStatement(READ_USER_QUERY);
            statement.setString(1, String.valueOf(userId));
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                User resultUser = new User();
                resultUser.setId(resultSet.getInt(1));
                resultUser.setUserName((resultSet.getString(2)));
                resultUser.setEmail(resultSet.getString(3));
                resultUser.setPassword(resultSet.getString(4));
                return resultUser;
            }
            else return null;

        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    public boolean update(User user){
        try(Connection connection = DbUtil.getConnection()){
            PreparedStatement statement = connection.prepareStatement(UPDATE_USER_QUERY);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4,String.valueOf(user.getId()));
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(int userID){
        try(Connection connection = DbUtil.getConnection()){
            PreparedStatement statement = connection.prepareStatement(DELETE_USER_QUERY);
            statement.setString(1,String.valueOf(userID));
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public User[] findAll(){
        try(Connection connection = DbUtil.getConnection()){
            User[] users = new User[0];
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                User nextUser = new User();
                nextUser.setId(resultSet.getInt(1));
                nextUser.setUserName(resultSet.getString(2));
                nextUser.setEmail(resultSet.getString(3));
                nextUser.setPassword(resultSet.getString(4));
                users = addToUserArray(nextUser,users);
            }
            return users;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    private User[] addToUserArray(User user,User[] array){
        User[] result =  Arrays.copyOf(array,array.length+1);
        result[array.length] = user;
        return result;
    }


}
