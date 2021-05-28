package br.com.pip.springdata.service;

import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.pip.springdata.orm.Funcionario;
import br.com.pip.springdata.orm.UnidadeTrabalho;
import br.com.pip.springdata.repository.FuncionarioRepository;
import br.com.pip.springdata.repository.UnidadeTrabalhoRepository;

@Service
public class CrudUnidadeTrabalhoService extends CrudService<UnidadeTrabalho> {

	private UnidadeTrabalhoRepository repository;
	private FuncionarioRepository funcionarios;

	public CrudUnidadeTrabalhoService(UnidadeTrabalhoRepository repository,
									  FuncionarioRepository funcionarios) {
		super(repository, "UNIDADES DE TRABALHO");
		this.repository = repository;
		this.funcionarios = funcionarios;
	}

	@Override
	protected UnidadeTrabalho instanciar() {
		return new UnidadeTrabalho();
	}

	@Override
	protected boolean popular(Scanner scanner, UnidadeTrabalho obj) {
		System.out.println("Nome: ");
		String nome = scanner.nextLine();
		
		if (nome.trim().equals("")) {
			System.out.println("É preciso informar o nome.");
			return false;
		}
		
		obj.setNome(nome);
		return true;
	}
	
	@Override
	protected void mostrarOpcoesPersonalizadas() {
		System.out.println("5 - Alocar funcionário");
		System.out.println("6 - Listar funcionários");
	}
	
	@Override
	protected void opcaoPersonalizada(Scanner scanner, int action) {
		if (action == 5) alocarFuncionario(scanner);
		else if (action == 6) listarFuncionarios(scanner);
	}

	private void alocarFuncionario(Scanner scanner) {
		Optional<UnidadeTrabalho> optUnidade = procurarUnidade(scanner);
		
		if (!optUnidade.isPresent()) {
			System.out.println("Unidade não encontrada!");
			return;
		}
		
		Optional<Funcionario> optFuncionario = procurarFuncionario(scanner);
		
		if (!optFuncionario.isPresent()) {
			System.out.println("Funcionário não encontrado!");
			return;
		}
		
		UnidadeTrabalho unidade = optUnidade.get();
		Funcionario funcionario = optFuncionario.get();
		
		unidade.alocarFuncionario(funcionario);
		
		repository.save(unidade);
	}

	private void listarFuncionarios(Scanner scanner) {
		Optional<UnidadeTrabalho> optUnidade = procurarUnidade(scanner);
		
		if (!optUnidade.isPresent()) {
			System.out.println("Unidade não encontrada!");
			return;
		}
		
		for (Funcionario f : optUnidade.get().getFuncionarios()) {
			System.out.println(f);
		}
	}
	
	private Optional<Funcionario> procurarFuncionario(Scanner scanner) {
		System.out.println("ID do funcionário: ");
		long id = scanner.nextLong();
		scanner.nextLine();
		return funcionarios.findById(id);
	}

	private Optional<UnidadeTrabalho> procurarUnidade(Scanner scanner) {
		System.out.println("ID da unidade: ");
		long id = scanner.nextLong();
		scanner.nextLine();
		return repository.findById(id);
	}
	
}
