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
import wad.logic.Game;

import wad.repository.AccountRepository;

@Controller
@RequestMapping("/typetutor")
public class TypetutorController {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    private Account account;
    private Game game;
    
    @PostConstruct
    public void init() {
        if (accountRepository.findByUsername("teacher") != null || accountRepository.findByUsername("user") != null) {
            return;
        }
        
        Account a = new Account();
        a.setUsername("user");
        a.setEmail("user@userland.tw");
        a.setPassword(passwordEncoder.encode("4321"));
        accountRepository.save(a);
        
        a = new Account();
        a.setUsername("teacher");
        a.setEmail("admin@adminland.tw");
        a.setPassword(passwordEncoder.encode("1234"));
        a.setAuthority("ADMIN");
        accountRepository.save(a);
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String view(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth.getName() != "anonymousUser") {
            String username = auth.getName();
            this.account = accountRepository.findByUsername(username);
            model.addAttribute("name", username);
            return "index";
        }
        model.addAttribute("name", null);
        return "index";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String typeThis(Model model) {
        if (this.account == null) {
            account = new Account();
        }
        game = new Game(account);
        game.determineTypeThis();
        
        model.addAttribute("points", account.getPoints());
        model.addAttribute("level", account.getLevel());
        model.addAttribute("nextText", game.getTypeThis());
        model.addAttribute("name", account.getUsername());
        
        return "index";
    }
}
