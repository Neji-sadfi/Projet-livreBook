package com.example.livrebook.test;


import com.example.livrebook.model.book.Book;
import com.example.livrebook.service.bookServices.BookService;
import com.example.livrebook.service.userServices.UserService;
import com.example.livrebook.service.deliveryOrderServices.DeliveryOrderServices;


import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class MainClass {
    public static void main(String[] args) {
//        try {
//            bs.insert(book);
//            System.out.println("book inserted");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//           User u1 = new User("Neji","Gherairi","Fekri.Makhlouf@Jouadi.tn");
//        try {
//            us.insert(u1);
//            System.out.println("user inserted");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//         User u2 = new User(1,"Neji","Gherairi","Fekri.Makhlouf@Jouadi.mallouki");
//        try {
//            us.update(u2);
//            System.out.println("user updated");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        List<Book> lb;
        BookService bks = new BookService();
        try {
            lb = bks.selectAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Book bk: lb){
            System.out.println(bk.toString());
        }
    }
}
