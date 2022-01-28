package ua.com.alevel.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ModelAndView entityNotFoundHandler(EntityNotFoundException exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("showMessage", true);
        mav.addObject("errorMessage", exception.getMessage());
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler(value = {MaxUploadSizeExceededException.class})
    public ModelAndView uploadErrorHandler(MaxUploadSizeExceededException exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("showMessage", true);
        mav.addObject("errorMessage", exception.getMessage());
        mav.setViewName("error");
        return mav;
    }
}
