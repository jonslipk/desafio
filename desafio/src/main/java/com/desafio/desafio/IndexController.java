package com.desafio.desafio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.desafio.desafio.model.CartaoCredito;
import com.desafio.desafio.model.CartaoCreditoDAO;



@Controller
public class IndexController {
	
	@Autowired
	private CartaoCreditoDAO repository;
	
	
	public IndexController(CartaoCreditoDAO cartaoCreditoDAO) {
		this.repository = cartaoCreditoDAO;
	}
	

    @RequestMapping("/")
    @ResponseBody
    Iterable<CartaoCredito> homePage() {
        //model.addAttribute("appName", appName);
    	return repository.findAll();
        //return "index";
    }
}
