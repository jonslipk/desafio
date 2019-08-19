package com.desafio.desafio.entity;

import javax.validation.constraints.NotBlank;

public class Autorizacao {

	@NotBlank(message = "Estabelecimento não pode ser vazio")
	private String estabelecimento;
	
	@NotBlank(message = "Número não pode ser vazio")
	private String numero;

	@NotBlank(message = "Validade não pode ser vazio")
	private String validade;
	
	@NotBlank(message = "Valor não pode ser vazio")
	private String valor;
	
	@NotBlank(message = "Senha não pode ser vazio")
	private String senha;

	@NotBlank(message = "CVV não pode ser vazio")
	private String cvv;

	public String getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(String estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getValidade() {
		return validade;
	}

	public void setValidade(String validade) {
		this.validade = validade;
	}

	
	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	
	

}
