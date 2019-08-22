package com.desafio.desafio.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.desafio.entity.Autorizacao;
import com.desafio.desafio.entity.CartaoCredito;
import com.desafio.desafio.entity.Mensagem;
import com.desafio.desafio.model.CartaoCreditoDAO;

@Service
public class CartaoCreditoService {

	@Autowired
	private CartaoCreditoDAO repository;
	
	private final static String BIN = "356981";

	public List<CartaoCredito> findAll() {
		return repository.findAll();
	}

	public CartaoCredito save(CartaoCredito cartaoCredito) throws NoSuchAlgorithmException {

		String senha = gerarSenha();
		String senhaCriptografada = criptografarSenha(senha);
		String validade = gerarValidade();
		String numero = gerarNumeroCartao();
		String cvv = gerarCVV(numero, validade);
		String saldo = cartaoCredito.getSaldo();
		
		cartaoCredito.setNumero(numero);
		cartaoCredito.setSenha(senhaCriptografada);
		cartaoCredito.setValidade(validade);
		cartaoCredito.setSaldo(unfotmatValor(saldo).toString());

		 repository.saveAndFlush(cartaoCredito);

		 cartaoCredito.setCvv(cvv);
		 cartaoCredito.setSaldo(saldo);
		 cartaoCredito.setSenha(senha);

		return cartaoCredito;
	}

	public List<Mensagem> validarVenda(Autorizacao autorizacao) throws ParseException, NoSuchAlgorithmException {

		Mensagem msg = new Mensagem();
		List<Mensagem> lista = new ArrayList<Mensagem>();

		CartaoCredito cartaoCredito = repository.findByNumero(autorizacao.getNumero());

		System.out.println(cartaoCredito);
		if (cartaoCredito != null) {
			Boolean flag = true;
			if (cartaoCredito.getValidade() != null) {

				String data1 = cartaoCredito.getValidade().replace("/", "");
				String data2 = autorizacao.getValidade().replace("/", "");
				Date data = new Date(System.currentTimeMillis());
				Calendar c = Calendar.getInstance();
				c.setTime(data);
				String dataHoje = new SimpleDateFormat("MMyy").format(c.getTime());

				SimpleDateFormat format = new SimpleDateFormat("MMyy");
				Date dat1 = new Date(format.parse(data1).getTime());
				Date dat2 = new Date(format.parse(data2).getTime());
				Date dat3 = new Date(format.parse(dataHoje).getTime());

				if (!dat1.equals(dat2)) {
					flag = false;
					msg.setCodigo("01");
					msg.setMensagem("Validade não confere");
					lista.add(msg);
				}
				if (dat1.before(dat3)) {
					flag = false;
					msg.setCodigo("07");
					msg.setMensagem("Cartão expirado");
					lista.add(msg);
				}
			}

			if (!validarCVV(cartaoCredito.getNumero(), cartaoCredito.getValidade(), autorizacao.getCvv())) {
				flag = false;
				msg.setCodigo("05");
				msg.setMensagem("CVV não é valido");
				lista.add(msg);
			}

			if (!criptografarSenha(autorizacao.getSenha()).equals(cartaoCredito.getSenha())) {
				flag = false;
				msg.setCodigo("06");
				msg.setMensagem("Senha não é válida");
				lista.add(msg);
			}

			if (unfotmatValor(autorizacao.getValor()) > Double.parseDouble(cartaoCredito.getSaldo())) {
				flag = false;
				msg.setCodigo("02");
				msg.setMensagem("Cartão não possui saldo");
				lista.add(msg);

			}
			if (flag) {
				Double saldoFinal = Double.parseDouble(cartaoCredito.getSaldo()) - unfotmatValor(autorizacao.getValor());
				msg.setCodigo("00");
				msg.setMensagem("Saldo Atual: " + NumberFormat.getCurrencyInstance().format(saldoFinal));
				lista.add(msg);
			}

		} else {
			msg.setCodigo("04");
			msg.setMensagem("Número do Cartão não existe");
			lista.add(msg);
		}

		return lista;

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
		
		String numero = BIN;

		for (int i = 0; i < 10; i++) {
			int random = gerador.nextInt(9);
			numero += "" + random;

		}

		return numero;
	}

	private static Double unfotmatValor(String valor) {

		String valorSemPonto = valor.replace(".", "");
		String valorSemVirgula = valorSemPonto.replace(",", ".");
	
		double valorDouble = Double.parseDouble(valorSemVirgula);
		
		return valorDouble;
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

	private static Boolean validarCVV(String numero, String validade, String codigo) {
		
		String cvv = gerarCVV(numero, validade);
		Boolean flag = false;
		
		if(cvv.equals(codigo)) {
			flag = true;
		}
		
		return flag;
	}

}