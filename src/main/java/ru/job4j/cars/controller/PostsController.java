package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;

@Controller
@AllArgsConstructor
@RequestMapping("/posts")
public class PostsController {

    private final PostsService postsService;

    private final CarModelService carModelService;

    @GetMapping("/list")
    public String getPostList(Model model,
                              @RequestParam(name = "priceFrom", required = false) Long priceFrom,
                              @RequestParam(name = "priceUntil", required = false) Long priceUntil,
                              @RequestParam(name = "yearFrom", required = false) Integer yearFrom,
                              @RequestParam(name = "yearUntil", required = false) Integer yearUntil,
                              @RequestParam(name = "carMake", required = false) String carMake) {
        model.addAttribute("carMakes", carModelService.findAllCarMakeCollection());
        model.addAttribute("sPriceFrom", priceFrom);
        model.addAttribute("sPriceUntil", priceUntil);
        model.addAttribute("sYearFrom", yearFrom);
        model.addAttribute("sYearUntil", yearUntil);
        model.addAttribute("sCarMake", carMake);

        Collection<Post> collection = postsService.findAllNotSold();
        if (carMake != null && !carMake.isEmpty()) {
            collection.retainAll(postsService.findPostsByMake(carMake));
        }
        if (priceFrom != null || priceUntil != null) {
            collection.retainAll(postsService.findPostsByPriceInterval(priceFrom, priceUntil));
        }
        if (yearFrom != null || yearUntil != null) {
            collection.retainAll(postsService.findPostsByYearInterval(yearFrom, yearUntil));
        }
        model.addAttribute("posts", collection);
        return "posts/list";
    }

    @GetMapping("/mylist")
    public String getMyPostList(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Collection<Post> posts = postsService.findAllActiveByUserId(user.getId());
        model.addAttribute("posts", posts);
        Collection<Post> sold = postsService.findAllSoldByUserId(user.getId());
        model.addAttribute("sold", sold);
        return "posts/mylist";
    }
}
