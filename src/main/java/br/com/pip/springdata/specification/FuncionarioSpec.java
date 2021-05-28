package br.com.pip.springdata.specification;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import br.com.pip.springdata.orm.Funcionario;

// Uma Specification encurta a Criteria API 
public class FuncionarioSpec {
	
	public static Specification<Funcionario> nome(String nome) {
		return (root, criteriaQuery, criteriaBuilder) -> {
			String nomeLike = (nome == null ? null : "%" + nome.toLowerCase() + "%");
			return criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), nomeLike);
		};
	}
	
	public static Specification<Funcionario> contratacao(LocalDate data) {
		return (root, criteriaQuery, criteriaBuilder) ->
			criteriaBuilder.greaterThanOrEqualTo(root.get("dataContratacao"), data);
	}
	
	public static Specification<Funcionario> salario(BigDecimal salario) {
		return (root, criteriaQuery, criteriaBuilder) ->
			criteriaBuilder.greaterThanOrEqualTo(root.get("salario"), salario);
	}

}
