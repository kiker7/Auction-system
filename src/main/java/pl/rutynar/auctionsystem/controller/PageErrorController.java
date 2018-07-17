package pl.rutynar.auctionsystem.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PageErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request){

        // For each code do smth..
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(status != null){
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
                return "error/500";
            }
        }

        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
