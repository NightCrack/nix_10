package ua.com.alevel.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SiteExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    public ModelAndView defaultErrorHandler(NotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("showMessage", true);
        modelAndView.addObject("errorMessage", exception.getMessage());
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(value = {FundsException.class, InputException.class})
    public ModelAndView defaultErrorHandler(FundsException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("showMessage", true);
        modelAndView.addObject("errorMessage", exception.getMessage());
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(value = {NumberFormatException.class})
    public ModelAndView defaultErrorHandler(NumberFormatException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("showMessage", true);
        modelAndView.addObject("errorMessage", "Input must be in format \"0,00\"");
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
