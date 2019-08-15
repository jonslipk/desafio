package com.desafio.desafio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.desafio.desafio.entity.CartaoCredito;
import com.desafio.desafio.model.CartaoCreditoDAO;



@Controller
public class CartaoCreditoController {
	
	@Autowired
	private CartaoCreditoDAO repository;
	
	
	public CartaoCreditoController(CartaoCreditoDAO cartaoCreditoDAO) {
		this.repository = cartaoCreditoDAO;
	}
	

   @RequestMapping(value = "/gerarCartao", method=RequestMethod.GET)
   public String form() {
    	return "cartaoCredito/gerarCartaoCredito";
    }
   
   @RequestMapping(value = "/gerarCartao", method=RequestMethod.POST)
   public String form(CartaoCredito cartaoCredito) {
    	
	   String bin = "123456";
	   String senha = "1234";
	   String validade = "08/2019";
	   
	   
	   
	   cartaoCredito.setNumero(bin);
	   cartaoCredito.setSenha(senha);
	   cartaoCredito.setValidade(validade);
	   
	   repository.save(cartaoCredito);
	   
	   return "redirect:/gerarCartaoCredito";
    }
   
}
