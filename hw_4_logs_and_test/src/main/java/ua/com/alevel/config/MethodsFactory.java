//package ua.com.alevel.config;
//
//import ua.com.alevel.service.impl.AuthorsServiceImpl;
//import ua.com.alevel.service.impl.BookInstancesServiceImpl;
//import ua.com.alevel.service.impl.BooksServiceImpl;
//import ua.com.alevel.service.impl.GenresServiceImpl;
//
//import java.io.BufferedReader;
//import java.util.Collection;
//
//public class MethodsFactory <ENTITY>{
//
//    Class<T> workService;
//
//    Object serviceSelector(String serviceName) {
//        switch(serviceName) {
//            case "authors" -> workService = new AuthorsServiceImpl();
//            case "books" -> workService = new BooksServiceImpl();
//            case "bookInstances" -> workService = new GenresServiceImpl();
//            default -> workService = new BookInstancesServiceImpl();
//        }
//        return workService;
//    }
//
//    void findAll(String serviceName, BufferedReader reader) {
//        ENTITY[] entities = serviceSelector(serviceName).findAll();
//        Collection<User> users = userService.findAll();
//        if (users != null && users.size() != 0) {
//            for (User user : users) {
//
//                System.out.println("user = " + user);
//            }
//        } else
//        {
//
//            System.out.println("users empty");
//        }
//    }
//}
