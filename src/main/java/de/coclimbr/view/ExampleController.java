package de.coclimbr.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ExampleController {

    @GetMapping("/example")
    public String index(Model model) {
        model.addAttribute("eventName", "FIFA 2018");
        return "example";
    }

}
