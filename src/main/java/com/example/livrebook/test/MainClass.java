package com.example.livrebook.test;


import com.example.livrebook.model.livraison.Livraison;
import com.example.livrebook.service.userServices.UserService;
import com.example.livrebook.service.livraisonServices.LivraisonService;


import java.sql.Date;
import java.sql.SQLException;

public class MainClass {
    public static void main(String[] args) {
        LivraisonService ls = new LivraisonService();
//        try {
//            bs.insert(book);
//            System.out.println("book inserted");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
           Livraison l1 = new Livraison("Pending","bouhmall",3000);
        try {
            ls.insert(l1);
            System.out.println("Delivery inserted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//         User u2 = new User(1,"Neji","Gherairi","Fekri.Makhlouf@Jouadi.mallouki");
//        try {
//            us.update(u2);
//            System.out.println("user updated");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }
}
