package co.kr.lotteon.controller;

import co.kr.lotteon.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class MainController {

    @GetMapping(value = {"/","/index"})
    public String index(){
        return "/index";
    }
}
