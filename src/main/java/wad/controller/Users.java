package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.domain.Account;
import wad.repository.AccountRepository;

@Controller
@RequestMapping("/typetutor/users")
public class Users {

    @Autowired
    private AccountRepository ac;

    @Autowired
    private PasswordEncoder pe;

    @Secured("ADMIN")
    @RequestMapping(method = RequestMethod.GET)
    public String listUsers(Model model) {
        model.addAttribute("users", ac.findAll());
        return "users";
    }

    @Secured("ADMIN")
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String remove(@PathVariable Long id) {
        Account account = ac.findOne(id);
        ac.delete(account);
        return "redirect:/users";
    }
}
