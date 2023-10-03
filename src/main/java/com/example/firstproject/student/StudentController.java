package com.example.firstproject.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StudentController {
    @Autowired
    private StudentRepository studentRepo;

    public StudentController(StudentRepository studentRepo) {
        this.studentRepo = studentRepo;
    }

    @GetMapping("/hello")
    public String sayHello(){
        return "hello";
    }
    @GetMapping("/register")
    public String registerForm(Model model){
        Student student = new Student();
        model.addAttribute("student",student);
        model.addAttribute("error",true);
        return "register";
    }

    @PostMapping("/register")
    public String saveRegisterInfo(Model model, @ModelAttribute Student student){
        Student db_student = studentRepo.checkByEmail(student.getEmail());
        if(db_student == null){
            studentRepo.save(student);
            return "login";
        }else{
            model.addAttribute("error",false);

        }
        model.addAttribute("student",student);
        return "register";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("student",new Student());
        model.addAttribute("loginError",true);
        return "login";
    }
    //submit button
    @PostMapping("/login")
    public String submitLogin(Model model,@ModelAttribute Student student){
        Student db_student = studentRepo.checkLogin(student.getEmail(),student.getPassword());
        if(db_student == null){
            model.addAttribute("loginError",false);
        }else{
            model.addAttribute("students",studentRepo.findAll());
            return "home";
        }
        model.addAttribute("student",student);
        return "login";
    }

//    @GetMapping("/home")
//    public String homePage(Model model){
//        model.addAttribute("students",studentRepo.findAll());
//        return "home";
//    }
//    @GetMapping("/update/{id}")
//    public String showUpdateForm(Model model, @PathVariable("id") Long id){
//        Student updateStudent = studentRepo.findById(id).orElseThrow();
//        model.addAttribute("student",updateStudent);
//        return "update";
//    }
//    @PostMapping("/update")
//    public String updateSubmit(Model model,@ModelAttribute Student student){
//        studentRepo.save(student);
//        model.addAttribute("students",studentRepo.findAll());
//        return "redirect:/home";
//    }

    @RequestMapping("/update/{id}")
    public String updatePage(Model model, @PathVariable("id") long id) {
        Student student = studentRepo.findById(id).get();
        model.addAttribute("student", student);

        return "update";

    }
    // Edit Submit
    @RequestMapping("/update")
    public String updateSubmit(Model model, @ModelAttribute Student student) {
        studentRepo.save(student);

        model.addAttribute("students", studentRepo.findAll());
        return "home";
    }

    @RequestMapping("/delete/{id}")
    public String DeletePage(Model model, @PathVariable("id") long id) {

        studentRepo.deleteById(id);
        model.addAttribute("students", studentRepo.findAll());

        return "home";

    }

}
