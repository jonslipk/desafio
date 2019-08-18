 package com.desafio.desafio.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class IndexController {
	
    @RequestMapping("/index")
    String homePage()  {
         System.out.println(gerarCVV("123456788923569","08/21"));
         return "index";
        }
    

    private static String gerarCVV(String numero, String validade) {

		String joinNumeroValidade = numero + validade.replace("/", "");

		char[] array = joinNumeroValidade.toCharArray();
		
		List ocharaters = new ArrayList(array.length);
		
		String cvv = "";

		
		System.out.println(cvv);
		return cvv;
	}
    
}
