package com.desafio.desafio.controllers;

import java.security.NoSuchAlgorithmException;
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

import com.desafio.desafio.entity.CartaoCredito;
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
		System.out.println(cartao.getNomeTitular());
		mv.addObject("cartaoCredito", cartao);

		return mv;
		
		
	}
	
	@RequestMapping(value = "/autorizar", method =  RequestMethod.POST)
	public ModelAndView autorizar( @Valid CartaoCredito cartaoCredito, BindingResult result) throws NoSuchAlgorithmException {
		
//		if(result.hasErrors()) {
//			ModelAndView mvReturn = new ModelAndView("cartaoCredito/autorizarVenda");
//			mvReturn.addObject("cartaoCredito", "");
//			List<String> msg = new ArrayList<String>();
//			for (ObjectError objectError : result.getAllErrors()) {
//				msg.add(objectError.getDefaultMessage());
//			}
//			mvReturn.addObject("msg",msg);
//			return mvReturn;
//		}
		
		ModelAndView mv = new ModelAndView("cartaoCredito/autorizarVenda");
		//CartaoCredito cartao = servico.save(cartaoCredito);
		System.out.println(cartaoCredito);
		//mv.addObject("cartaoCredito", cartao);

		return mv;
		
		
	}


}
