package br.com.pip.springdata.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.pip.springdata.orm.Funcionario;
import br.com.pip.springdata.orm.FuncionarioProjecao;
import br.com.pip.springdata.repository.FuncionarioRepository;

@Service
public class RelatoriosService {
	
	// Podia refatorar os inputs para classes específicas para ler datas, números, etc.
	private final DateTimeFormatter dateFormatter = 
			DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private final FuncionarioRepository funcionarios;
	
	public RelatoriosService(FuncionarioRepository funcionarios) {
		this.funcionarios = funcionarios;
	}
	
	public void inicial(Scanner scanner) {
		int action = -1;
		
		while (action != 0) {
			System.out.println("RELATÓRIOS ------------------");
			System.out.println("0 - Voltar");
			System.out.println("1 - Buscar funcionário por nome");
			System.out.println("2 - Relatório de funcionaários");
			System.out.println("3 - Funcionários com salário");
		
			action = scanner.nextInt();
			scanner.nextLine();
			
			if (action == 1) buscaFuncionarioPorNome(scanner);
			else if (action == 2) relatorioFuncionarios(scanner);
			else if (action == 3) funcionariosComSalario();
		}
	}

	private void buscaFuncionarioPorNome(Scanner scanner) {
		System.out.println("Nome: ");
		String nome = scanner.nextLine();
		List<Funcionario> list = funcionarios.findByNomeLikeIgnoreCase("%" + nome + "%");
		list.forEach(System.out::println);
	}

	private void relatorioFuncionarios(Scanner scanner) {
		System.out.println("Data contratação: ");
		String dataContratacao = scanner.nextLine();
		if (dataContratacao.trim().equals("")) return;
		LocalDate data = LocalDate.parse(dataContratacao, dateFormatter);

		System.out.println("Salário: ");
		String salario = scanner.nextLine();
		if (salario.trim().equals("")) return;
		BigDecimal valor = new BigDecimal(salario);
		
		List<Funcionario> list = funcionarios.findByContratacaoAndSalarioAPartirDe(data, valor);
		
		for (Funcionario f : list) {
			System.out.println(f + " - " + f.getSalario() + " - " + f.getDataContratacao());
		}
	}
	
	private void funcionariosComSalario() {
		List<FuncionarioProjecao> list = funcionarios.findFuncionariosComSalario();
		for (FuncionarioProjecao f : list) {
			System.out.println(f.getId() + " - " + f.getNome() + " - " + f.getSalario());
		}
	}
	
}
