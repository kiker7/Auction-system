package pl.rutynar.auctionsystem.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.rutynar.auctionsystem.data.domain.User;
import pl.rutynar.auctionsystem.repository.NotificationRepository;
import pl.rutynar.auctionsystem.service.UserService;
import pl.rutynar.auctionsystem.wrapper.PageWrapper;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    // For pagination on home page
    private static final int BUTTONS_TO_SHOW = 3;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 6;
    private static final int[] PAGE_SIZES = {6, 12};

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/")
    public String home(){

        return "redirect:/home";
    }

    @GetMapping("/home")
    public ModelAndView userHome(@RequestParam("pageSize") Optional<Integer> pageSize, @RequestParam("page") Optional<Integer> page){

        ModelAndView modelAndView = new ModelAndView("home");

        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<User> userList = userService.getAllUsers(new PageRequest(evalPage, evalPageSize));
        PageWrapper pageWrapper = new PageWrapper(userList.getTotalPages(), userList.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("userList", userList);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pageWrapper);

        // Notifications
        modelAndView.addObject("notifications", userService.getUserNotifications(userService.getCurrentUser()));

        return modelAndView;
    }

    @GetMapping("/home/user-notifications")
    public @ResponseBody long getUserNotificationsJSON(){
        return  notificationRepository.countByRecipient(userService.getCurrentUser()) ;
    }
}
