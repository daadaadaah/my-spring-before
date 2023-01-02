package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("hello") // `http://localhost:8080/hello`로 들어오면 hello() 메서드 호출 됨
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
        return "hello"; // `/resources/templates/hello.html`의 hello
    }

    @GetMapping("hello-mvc") // `http://localhost:8080/hello-mvc`로 들어오면 helloMvc() 메서드 호출 됨
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template"; // `/resources/templates/hello.html`의 hello
    }

    @GetMapping("hello-string") // `http://localhost:8080/hello-string`로 들어오면 helloMvc() 메서드 호출 됨
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; //
    }

    @GetMapping("hello-api") // `http://localhost:8080/hello-api`로 들어오면 helloMvc() 메서드 호출 됨
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
