package com.desafio.desafio.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.desafio.model.CartaoCreditoDAO;
import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;
import com.desafio.desafio.entity.CartaoCredito;

@Service
public class CartaoCreditoService {

	@Autowired
	private CartaoCreditoDAO repository;

	public List<CartaoCredito> findAll() {
		return repository.findAll();
	}

	public CartaoCredito save(CartaoCredito cartaoCredito) throws NoSuchAlgorithmException {

		String senha = gerarSenha();
		String senhaCriptografada = criptografarSenha(senha);
		String validade = gerarValidade();
		String numero = gerarNumeroCartao();
		String cvv = gerarCVV(numero, validade);

		cartaoCredito.setNumero(numero);
		cartaoCredito.setSenha(senhaCriptografada);
		cartaoCredito.setValidade(validade);

		CartaoCredito cartao = repository.saveAndFlush(cartaoCredito);

		cartao.setCvv(cvv);
		cartao.setSenha(senha);

		return cartao;
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	// Funções de RN

	private static String gerarSenha() {
		int qtdeMaximaCaracteres = 4;
		String[] caracteres = { "0", "1", "b", "2", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g",
				"h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B",
				"C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
				"X", "Y", "Z" };

		StringBuilder senha = new StringBuilder();

		for (int i = 0; i < qtdeMaximaCaracteres; i++) {
			int posicao = (int) (Math.random() * caracteres.length);
			senha.append(caracteres[posicao]);
		}
		return senha.toString();
	}

	private static String criptografarSenha(String senha) throws NoSuchAlgorithmException {

		MessageDigest algorithm = MessageDigest.getInstance("MD5");
		algorithm.update(senha.getBytes(), 0, senha.length());
		String senhaCriptografada = new BigInteger(1, algorithm.digest()).toString(16);

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
			int random = gerador.nextInt(9);
			numero += "" + random;

		}

		return numero;
	}

	private static String gerarCVV(String numero, String validade) {

		String joinNumeroValidade = numero + validade.replace("/", "");

		char[] array = joinNumeroValidade.toCharArray();

		List<char[]> list = Arrays.asList(array);

		String cvv = "";

		Collections.shuffle(list);
		
		for (int i = 0; i < 3; i++) {
			cvv += list.get(i);
		}
		return cvv;
	}

	private static String validarCVV(String numero, String validade, String codigo) {

		return "cvv";
	}

}