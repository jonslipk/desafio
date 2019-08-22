package com.desafio.desafio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/index")
	String homePage() {
		System.out.println(gerarCVV("9999999999999999", "12/99999"));
		return "index";
	}

	private static String gerarCVV(String numero, String validade) {

		String joinNumeroValidade = numero + validade.replace("/", "");
		char[] arraycar = joinNumeroValidade.toCharArray();
		int soma = 0;

		for (int i = 0; i < arraycar.length; i++) {
			soma += Integer.parseInt(String.valueOf(arraycar[i]));
		}
		String cvv = Integer.toString(soma * 3);

		return cvv;
	}

}
