package br.com.pip.springdata.orm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "unidades_trabalho")
public class UnidadeTrabalho {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String nome;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Funcionario> funcionarios = new ArrayList<>();

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return id + " - " + nome;
	}
	
	public void alocarFuncionario(Funcionario funcionario) {
		funcionarios.add(funcionario);
	}

	public List<Funcionario> getFuncionarios() {
		return Collections.unmodifiableList(funcionarios);
	}

}
