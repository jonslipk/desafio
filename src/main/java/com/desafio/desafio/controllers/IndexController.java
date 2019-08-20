package com.desafio.desafio.controllers;

import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/index")
	String homePage() {
		System.out.println(gerarCVV("3569814362806074", "08/21"));
		return "index";
	}

	private static String gerarCVV(String numero, String validade) {

		String joinNumeroValidade = numero + validade.replace("/", "");
		char[] arraycar = joinNumeroValidade.toCharArray();
		
		for (int i = 0; i < arraycar.length; i++) {
			for (int j = i + 1; j < arraycar.length - 1; j++) {
				if (arraycar[i] != arraycar[j]) {
					System.out.println(arraycar[i]);
				}
			}
		}
		// System.out.println(v);

		return "111";
	}

}
