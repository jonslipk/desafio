package com.desafio.desafio.model;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desafio.desafio.entity.CartaoCredito;

@Repository
@Transactional
public interface CartaoCreditoDAO extends JpaRepository<CartaoCredito, Long> {

}
