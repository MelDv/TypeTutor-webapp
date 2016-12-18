package wad.controller;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.domain.Account;

import wad.repository.AccountRepository;

@Controller
@RequestMapping("/typetutor")
public class TypetutorController {

    @Autowired
    private AccountRepository ac;

    @Autowired
    private PasswordEncoder pe;

//    @PostConstruct
//    public void init() {
//
//        if (ac.findByUsername("teacher") != null) {
//            return;
//        }
//
//        Account a = new Account();
//        a.setUsername("user");
//        a.setEmail("user@userland.tw");
//        a.setPassword(pe.encode("4321"));
//        ac.save(a);
//
//        a = new Account();
//        a.setUsername("teacher");
//        a.setEmail("admin@adminland.tw");
//        a.setPassword(pe.encode("1234"));
//        a.setAuthority("ADMIN");
//        ac.save(a);
//    }

    @RequestMapping(method = RequestMethod.GET)
    public String view(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getName() != "anonymousUser") {
            String username = auth.getName();
            model.addAttribute("name", username);
            return "index";
        }
        model.addAttribute("name", null);
        return "index";
    }

    @Secured("ADMIN")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String listUsers(Model model) {
        model.addAttribute("users", ac.findAll());
        return "users";
    }

    @Secured("ADMIN")
    @RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
    public String remove(@PathVariable Long id) {
        Account account = ac.findOne(id);
        ac.delete(account);
        return "users";
    }
}
