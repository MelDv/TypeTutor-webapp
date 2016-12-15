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

    @PostConstruct
    public void init() {

        if (ac.findByUsername("user") != null) {
            return;
        }

        Account a = new Account();
        a.setUsername("user");
        a.setEmail("user@userland.tw");
        a.setPassword(pe.encode("password"));
        ac.save(a);

        a = new Account();
        a.setUsername("postman");
        a.setEmail("admin@adminland.tw");
        a.setPassword(pe.encode("pat"));
        a.setAuthority("ADMIN");
        ac.save(a);
    }

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

//    @Secured("ADMIN")
//    @RequestMapping(method = RequestMethod.POST)
//    public String add(@Valid @ModelAttribute Message message, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "login";
//        }
//
//
//        return "redirect:/typetutor";
//    }
}
