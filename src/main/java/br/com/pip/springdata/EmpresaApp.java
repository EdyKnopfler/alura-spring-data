package br.com.pip.springdata;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.pip.springdata.service.CrudCargoService;
import br.com.pip.springdata.service.CrudFuncionarioService;
import br.com.pip.springdata.service.CrudUnidadeTrabalhoService;
import br.com.pip.springdata.service.RelatorioDinamicoFuncionario;
import br.com.pip.springdata.service.RelatoriosService;

@SpringBootApplication
public class EmpresaApp implements CommandLineRunner {
	
	private CrudCargoService cargos;
	private CrudFuncionarioService funcionarios;
	private CrudUnidadeTrabalhoService unidades;
	private RelatoriosService relatorios;
	private RelatorioDinamicoFuncionario relatorioDinamico;

	public EmpresaApp(CrudCargoService cargo, 
					  CrudFuncionarioService funcionario,
					  CrudUnidadeTrabalhoService unidades,
					  RelatoriosService relatorios,
					  RelatorioDinamicoFuncionario relatorioDinamico) {
		this.cargos = cargo;
		this.funcionarios = funcionario;
		this.unidades = unidades;
		this.relatorios = relatorios;
		this.relatorioDinamico = relatorioDinamico;
	}

	public static void main(String[] args) {
		SpringApplication.run(EmpresaApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		boolean system = true;
		
		while (system) {
			System.out.println("Qual ação você quer executar?");
			System.out.println("0 - Sair");
			System.out.println("1 - Cargos");
			System.out.println("2 - Funcionários");
			System.out.println("3 - Unidades de Trabalho");
			System.out.println("4 - Relatórios");
			System.out.println("5 - Relatório dinâmico");
			
			int action = scanner.nextInt();
			scanner.nextLine();
			
			if (action == 1) cargos.inicial(scanner);
			else if (action == 2) funcionarios.inicial(scanner);
			else if (action == 3) unidades.inicial(scanner);
			else if (action == 4) relatorios.inicial(scanner);
			else if (action == 5) relatorioDinamico.inicial(scanner);
			else system = false;
		}
		
		
	}

}
