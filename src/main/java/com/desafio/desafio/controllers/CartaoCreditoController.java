package com.desafio.desafio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.desafio.desafio.entity.CartaoCredito;
import com.desafio.desafio.model.CartaoCreditoDAO;

@Controller
public class CartaoCreditoController {

	private CartaoCreditoDAO repository;

	public CartaoCreditoController(CartaoCreditoDAO cartaoCreditoDAO) {
		this.repository = cartaoCreditoDAO;
	}

	@RequestMapping(value = "/gerarCartao", method = RequestMethod.GET)
	public String form() {
		return "cartaoCredito/gerarCartaoCredito";
	}

	@RequestMapping(value = "/gerarCartao", method = RequestMethod.POST)

	public ModelAndView form(CartaoCredito cartaoCredito) {

		// System.out.println(cartaoCredito.getNomeTitular());
		String bin = "123456";
		String senha = "1234";
		String validade = "08/19";

		cartaoCredito.setNumero(bin);
		cartaoCredito.setSenha(senha);
		cartaoCredito.setValidade(validade);

		repository.save(cartaoCredito);
		
		ModelAndView mv = new ModelAndView("cartaoCredito/gerarCartaoCredito");
		mv.addObject("cartaoCredito", cartaoCredito);

		return mv;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ModelAndView gerar(CartaoCredito cartaoCredito) {
		// CartaoCredito cartaoCredito = repository.findById(id);
		ModelAndView mv = new ModelAndView("cartaoCredito/gerarCartaoCredito");
		mv.addObject("cartaoCredito", cartaoCredito);

		return mv;
	}

}
