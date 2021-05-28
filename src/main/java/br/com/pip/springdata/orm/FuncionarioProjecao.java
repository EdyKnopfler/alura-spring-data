package br.com.pip.springdata.orm;

import java.math.BigDecimal;

public interface FuncionarioProjecao {

	// Tipos e getters padronizados pelos nomes dos atributos
	Long getId();
	String getNome();
	BigDecimal getSalario();
	
}
