package com.desafio.desafio.controllers;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.desafio.desafio.entity.Autorizacao;
import com.desafio.desafio.entity.CartaoCredito;
import com.desafio.desafio.entity.Mensagem;
import com.desafio.desafio.service.CartaoCreditoService;

@Controller
public class CartaoCreditoController {

	
	@Autowired
	private CartaoCreditoService servico;
	
	@RequestMapping("/")
    String homePage()  {
         return "cartaoCredito/gerarCartaoCredito";
        }
	
	@RequestMapping("/autorizacao")
    String autorizar()  {
         return "cartaoCredito/autorizarVenda";
        }

	@RequestMapping(value = "/salvar", method =  RequestMethod.POST)
	public ModelAndView salvar( @Valid CartaoCredito cartaoCredito, BindingResult result) throws NoSuchAlgorithmException {
		
		if(result.hasErrors()) {
			ModelAndView mvReturn = new ModelAndView("cartaoCredito/gerarCartaoCredito");
			mvReturn.addObject("cartaoCredito", "");
			List<String> msg = new ArrayList<String>();
			for (ObjectError objectError : result.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());
			}
			mvReturn.addObject("msg",msg);
			return mvReturn;
		}
		
		ModelAndView mv = new ModelAndView("cartaoCredito/gerarCartaoCredito");
		CartaoCredito cartao = servico.save(cartaoCredito);
		
		mv.addObject("cartaoCredito", cartao);

		return mv;
		
		
	}
	
	@RequestMapping(value = "/autorizar", method =  RequestMethod.POST)
	public ModelAndView autorizar( @Valid Autorizacao autorizacao, BindingResult result, RedirectAttributes attributes) throws NoSuchAlgorithmException, ParseException {
		
		if(result.hasErrors()) {
			ModelAndView mvReturn = new ModelAndView("cartaoCredito/autorizarVenda");
			
			List<String> msg = new ArrayList<String>();
			for (ObjectError objectError : result.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());
			}
			mvReturn.addObject("msg",msg);
			return mvReturn;
		}
		
		ModelAndView mv = new ModelAndView("cartaoCredito/autorizarVenda");
		List<Mensagem> mensagens = servico.validarVenda(autorizacao);
		
		mv.addObject("mensagens", mensagens);

		return mv;
		
		
	}


}
