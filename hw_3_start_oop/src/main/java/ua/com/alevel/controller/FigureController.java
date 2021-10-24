package ua.com.alevel.controller;

import ua.com.alevel.entity.Figure;
import ua.com.alevel.service.FiguresService;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Scanner;

public class FigureController {

    private final FiguresService figuresService = new FiguresService();
    private static final Scanner userInput = new Scanner(System.in);

    private static final String CREATE_VALUE = "new";
    private static final String UPDATE_VALUE = "upd";
    private static final String DELETE_VALUE = "del";
    private static final String FIND_ONE_VALUE = "one";
    private static final String FIND_ALL_VALUE = "all";
    private static final String EXIT_VALUE = "ext";

    private String breaker;
    private String id;
    private String name;
    private String shape;
    private String lengthString;
    private String heightString;
    private String widthString;
    private String volumeString;
    private BigDecimal length;
    private BigDecimal height;
    private BigDecimal width;
    private BigDecimal volume;

    public void execute() {

        do {

            menu();
            crud(userInput.nextLine());

        } while (!Objects.equals(breaker, EXIT_VALUE));

    }

    private void menu() {

        System.out.println();
        System.out.println("Make your choice:");
        System.out.println("Create figure - enter '" + CREATE_VALUE + "';" );
        System.out.println("Update figure - enter '" + UPDATE_VALUE + "';");
        System.out.println("Delete figure - enter '" + DELETE_VALUE + "';");
        System.out.println("Find figure by ID - enter '" + FIND_ONE_VALUE + "';");
        System.out.println("List all figures - enter '" + FIND_ALL_VALUE + "';");
        System.out.println("Exit the program - enter '" + EXIT_VALUE + "';");
        System.out.print("Your input: ");
    }

    private void crud(String position) {

        switch (position) {

            case CREATE_VALUE : create();
                break;
            case UPDATE_VALUE: update();
                break;
            case DELETE_VALUE: delete();
                break;
            case FIND_ONE_VALUE: findById();
                break;
            case FIND_ALL_VALUE: findAll();
                break;
            case EXIT_VALUE: breaker = EXIT_VALUE;
                break;
            default : System.out.println();
                System.out.println("Wrong input");
        }
    }

    private void create() {

        System.out.println();
        System.out.print("Enter figure's name: ");
        name = userInput.nextLine();
        System.out.println();
        System.out.print("Enter figure's shape: ");
        shape = userInput.nextLine();
        System.out.println();
        System.out.print("Enter figure's length: ");
        lengthString = userInput.nextLine();
        length = new BigDecimal(lengthString);
        System.out.println();
        System.out.print("Enter figure's height: ");
        heightString = userInput.nextLine();
        height = new BigDecimal(heightString);
        System.out.println();
        System.out.print("Enter figure's width: ");
        widthString = userInput.nextLine();
        width = new BigDecimal(widthString);
        System.out.println();
        System.out.print("Enter figure's volume: ");
        volumeString = userInput.nextLine();
        volume = new BigDecimal(volumeString);

        Figure figure = new Figure();
        figure.setName(name);
        figure.setShape(shape);
        figure.setLength(length);
        figure.setHeight(height);
        figure.setWidth(width);
        figure.setVolume(volume);
        figuresService.create(figure);
    }

    private void update() {

        System.out.println();
        System.out.print("Enter ID of figure to alter: ");
        id = userInput.nextLine();
        System.out.println("Enter new value to edit, leave empty and press \"Enter\" to leave unchanged.");
        System.out.println();
        System.out.print("New name: ");
        name = userInput.nextLine();
        System.out.println();
        System.out.print("New shape: ");
        shape = userInput.nextLine();
        System.out.println();
        System.out.print("New length: ");
        lengthString = userInput.nextLine();
        length = new BigDecimal(lengthString);
        System.out.println();
        System.out.print("New height: ");
        heightString = userInput.nextLine();
        height = new BigDecimal(heightString);
        System.out.println();
        System.out.print("New width: ");
        widthString = userInput.nextLine();
        width = new BigDecimal(widthString);
        System.out.println();
        System.out.print("New volume: ");
        volumeString = userInput.nextLine();
        volume = new BigDecimal(volumeString);

        Figure figure = new Figure();
        figure.setId(id);
        figure.setName(name);
        figure.setShape(shape);
        figure.setLength(length);
        figure.setHeight(height);
        figure.setWidth(width);
        figure.setVolume(volume);
        figuresService.update(figure);
    }

    private void delete() {

        System.out.println();
        System.out.print("Enter ID of figure to delete: ");
        id = userInput.nextLine();
        figuresService.delete(id);
    }

    private void findById() {

        System.out.println();
        System.out.print("Enter figure's ID: ");
        id = userInput.nextLine();
        Figure figure = figuresService.findById(id);
        System.out.println("Your figure: ");
        System.out.println(figure);
    }

    private void findAll() {

        Figure[] figures = figuresService.findAll();
        if((figures != null) && (figures.length != 0)) {
            for(Figure figure: figures) {

                if (figure != null) {

                    System.out.println("Figure: " + figure);
                }
            }
        } else {

            System.out.println("The base is empty.");
        }
    }
}
