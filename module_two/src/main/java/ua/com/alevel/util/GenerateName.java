package ua.com.alevel.util;

import ua.com.alevel.data.CustomData;

public class GenerateName {

    private GenerateName() {
    }

    private static <Element> Element nameOpt(Element[] namesArray) {
        return namesArray[(int) (Math.round(Math.random() * (namesArray.length - 1)))];
    }

    public static String word(int option) {
        switch (option) {
            case 1 -> {
                return nameOpt(CustomData.bookStatusPool());
            }
            case 2 -> {
                return nameOpt(CustomData.firstNamePool());
            }
            case 3 -> {
                return nameOpt(CustomData.genreNamePool());
            }
            case 4 -> {
                return nameOpt(CustomData.publisherPool());
            }
            case 5 -> {
                return nameOpt(CustomData.lastNamePool());
            }
            case 6 -> {
                return nameOpt(CustomData.bookTitlePool());
            }
            case 7 -> {
                return nameOpt(CustomData.isbnPool());
            }
            case 8, 9, 10 -> {
                return System.lineSeparator();
            }
            default -> {
                return String.valueOf((char) (Math.random() * Character.MAX_VALUE));
            }
        }
    }
}
