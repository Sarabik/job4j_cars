package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.ImageFileDto;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    private final CarModelService carModelService;

    private final BodyTypeService bodyTypeService;

    private final FuelTypeService fuelTypeService;

    private final EngineSizeService engineSizeService;

    private final TransmissionService transmissionService;

    private final CarColourService carColourService;

    private final CarService carService;

    @GetMapping("/{postId}")
    public String getOnePostPage(@PathVariable int postId, Model model) {
        Optional<Post> optionalPost = postService.findById(postId);
        model.addAttribute("post", optionalPost.get());
        return "post/one";
    }

    @GetMapping("/create")
    public String getPostCreatePage(Model model) {
        model.addAttribute("carModels", carModelService.findAll());
        model.addAttribute("allBodyTypes", bodyTypeService.findAll());
        model.addAttribute("allFuelTypes", fuelTypeService.findAll());
        model.addAttribute("allEngineSizes", engineSizeService.findAll());
        model.addAttribute("allTransmissions", transmissionService.findAll());
        model.addAttribute("allCarColours", carColourService.findAll());
        return "post/create";
    }

    @PostMapping("/create")
    public String addPost(Model model,
                          @ModelAttribute Car car,
                          @RequestParam String description,
                          @RequestParam Integer price,
                          @RequestParam MultipartFile imageFileDto,
                          HttpServletRequest request) throws IOException {
        carService.save(car);
        Post post = new Post();
        post.setCar(car);
        post.setDescription(description);
        post.setPrice(price);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        post.setUser(user);
        boolean ifSaved = postService.save(post, new ImageFileDto(imageFileDto.getOriginalFilename(), imageFileDto.getBytes()));
        if (!ifSaved) {
            model.addAttribute("message", "Error! Advert is not added");
            return "post/create";
        }
        return "redirect:/";
    }

    @GetMapping("/change/{postId}")
    public String getEditPostPage(@PathVariable int postId, Model model) {
        Optional<Post> optionalPost = postService.findById(postId);
        model.addAttribute("post", optionalPost.get());
        return "post/change";
    }

    @PostMapping("/change/{postId}")
    public String editPost(@ModelAttribute Post post) {
        postService.update(post);
        return "redirect:/posts/mylist";
    }

    @GetMapping("/sold/{postId}")
    public String movePostToSold(@PathVariable int postId) {
        postService.movePostToSold(postId);
        return "redirect:/posts/mylist";
    }

    @GetMapping("/delete/{postId}")
    public String deletePost(@PathVariable int postId) {
        postService.delete(postId);
        return "redirect:/posts/mylist";
    }

    @GetMapping("/updateDate/{postId}")
    public String updatePost(@PathVariable int postId) {
        postService.updateDate(postId);
        return "redirect:/posts/mylist";
    }

}
