package com.desafio.desafio.controllers;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.desafio.desafio.model.CartaoCreditoDAO;



@Controller
public class IndexController {
	
	@Autowired
	private CartaoCreditoDAO repository;
	
	
	public IndexController(CartaoCreditoDAO cartaoCreditoDAO) {
		this.repository = cartaoCreditoDAO;
	}
	

    @RequestMapping("/")
    //@ResponseBody
    String homePage() throws NoSuchAlgorithmException, UnsupportedEncodingException {
           System.out.println(gerarNumeroCartao());
           return "index";
        }
    
    private static String gerarSenha() {
        int qtdeMaximaCaracteres = 4;
        String[] caracteres = { "0", "1", "b", "2", "4", "5", "6", "7", "8",
                "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
                "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
                "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
                "V", "W", "X", "Y", "Z" };
       
        StringBuilder senha = new StringBuilder();

        for (int i = 0; i < qtdeMaximaCaracteres; i++) {
            int posicao = (int) (Math.random() * caracteres.length);
            senha.append(caracteres[posicao]);
        }
        return senha.toString();
    }
    
    private static String criptografarSenha(String senha) throws NoSuchAlgorithmException {

   	 	MessageDigest algorithm = MessageDigest.getInstance("MD5");
   	 	algorithm.update(senha.getBytes(),0,senha.length());
        String senhaCriptografada = new BigInteger(1,algorithm.digest()).toString(16);
        
        return senhaCriptografada;
    }
    
    private static String gerarValidade() {
    	
    	Date data = new Date(System.currentTimeMillis());

    	Calendar c = Calendar.getInstance();
    	c.setTime(data);
    	
    	c.set(Calendar.YEAR, c.get(Calendar.YEAR) + 2);
    	
    	String validade = new SimpleDateFormat("MM/yy").format(c.getTime());
    	
        return validade;
    }
    
 private static String gerarNumeroCartao() {
	 
	 	Random gerador = new Random();
	 	String bin = "356981";
		String numero = bin;
   
       for (int i = 0; i < 10; i++) {
    	  int random = gerador.nextInt(10);
    	  numero += ""+random;
           
        }
       
       return numero;
    }
 
 private static String gerarCVV() {
	 
	 String numero = gerarNumeroCartao();
	 String validade = gerarValidade();
	 
    return "cvv";
 }
 
private static String validarCVV(String codigo) {
	 
	 String numero = gerarNumeroCartao();
	 String validade = gerarValidade();
	 
    return "cvv";
 }
    
    
}
