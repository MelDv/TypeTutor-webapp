package wad.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.domain.Account;
import wad.repository.AccountRepository;

@Controller
@RequestMapping("/typetutor/register")
public class RegisterController {

    @Autowired
    private AccountRepository ar;

    @RequestMapping(method = RequestMethod.GET)
    public String form(@ModelAttribute("account") Account account) {
        return "register";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String register(@Valid @ModelAttribute("account") Account account,
            @RequestBody String password, @RequestBody String repassword,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors() || !password.equals(repassword)) {
            return "redirect:/register";
        }

        ar.save(account);
        return "redirect:/login";
    }

}
