package pl.coderslab;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDAO;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


        UserDAO dao = new UserDAO();
        User[] array = dao.findAll();
        for (User user : array) {
            System.out.println(user.getUserName());

        }





    }
}