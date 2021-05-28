package br.com.pip.springdata.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.pip.springdata.orm.Funcionario;
import br.com.pip.springdata.repository.FuncionarioRepository;
import br.com.pip.springdata.specification.FuncionarioSpec;

@Service
public class RelatorioDinamicoFuncionario {
	
	private final DateTimeFormatter dateFormatter = 
			DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private final FuncionarioRepository funcionarios;
	
	public RelatorioDinamicoFuncionario(FuncionarioRepository funcionarios) {
		this.funcionarios = funcionarios;
	}
	
	public void inicial(Scanner scanner) {
		System.out.println("Nome do funcionário (opcional):");
		String nome = scanner.nextLine();
		
		if (nome.trim().equals("")) {
			nome = null;
		}
		
		System.out.println("Data contratação (opcional): ");
		String dataContratacaoStr = scanner.nextLine();
		LocalDate dataContratacao = null;
		
		if (!dataContratacaoStr.trim().equals("")) {
			dataContratacao = LocalDate.parse(dataContratacaoStr, dateFormatter);
		}
		
		System.out.println("Salário (opcional): ");
		String salarioStr = scanner.nextLine();
		BigDecimal salario = null;
		
		if (!salarioStr.trim().equals("")) {
			salario = new BigDecimal(salarioStr);
		}
		
		List<Funcionario> list = funcionarios.findAll(
				Specification
					.where(FuncionarioSpec.nome(nome))
					.and(FuncionarioSpec.contratacao(dataContratacao))
					.and(FuncionarioSpec.salario(salario))
		);
		
		for (Funcionario f : list) {
			System.out.println(f.getNome() +  " (" + f.getCargo().getDescricao() + ") - " + 
		                       f.getSalario() + " - " + f.getDataContratacao()); 
		}
	}
	
}
