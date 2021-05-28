package br.com.pip.springdata.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.pip.springdata.orm.Cargo;
import br.com.pip.springdata.orm.Funcionario;
import br.com.pip.springdata.repository.CargoRepository;
import br.com.pip.springdata.repository.FuncionarioRepository;

@Service
public class CrudFuncionarioService extends CrudService<Funcionario> {

	private final DateTimeFormatter dateFormatter = 
			DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private CargoRepository cargos;
	private FuncionarioRepository funcionarios;

	public CrudFuncionarioService(FuncionarioRepository repository, CargoRepository cargos) {
		super(repository, "FUNCIONÁRIOS");
		this.funcionarios = repository;
		this.cargos = cargos;
	}

	@Override
	protected Funcionario instanciar() {
		return new Funcionario();
	}
	
	@Override
	protected void listar(Scanner scanner) {
		System.out.println("Página: ");
		int pageNo = scanner.nextInt();
		scanner.nextLine();	
		
		pageNo--;  // contado a partir de 0
		
		int linesPerPage = 15;
		
		PageRequest pageable = PageRequest.of(pageNo, linesPerPage, Sort.by(Sort.Direction.ASC, "nome"));
		Page<Funcionario> page = funcionarios.findAll(pageable);
		
		System.out.println((page.getNumber() + 1) + "/" + page.getTotalPages());
		page.forEach(System.out::println);
	}

	@Override
	protected boolean popular(Scanner scanner, Funcionario funcionario) {
		System.out.println("Nome: ");
		String nome = scanner.nextLine();
		
		if (nome.trim().equals("")) {
			System.out.println("É preciso informar o nome.");
			return false;
		}
		
		funcionario.setNome(nome);
		
		System.out.println("ID do cargo: ");
		long id = scanner.nextLong();
		scanner.nextLine();
		Optional<Cargo> optCargo = cargos.findById(id);
		
		if (optCargo.isPresent()) {
			funcionario.setCargo(optCargo.get());
		}
		else {
			System.out.println("Cargo não encontrado!");
			return false;
		}
		
		System.out.println("Data contratação: ");
		String dataContratacao = scanner.nextLine();
		
		if (!dataContratacao.trim().equals("")) {
			funcionario.setDataContratacao(LocalDate.parse(dataContratacao, dateFormatter));
		}
		
		System.out.println("Salário: ");
		String salario = scanner.nextLine();
		
		if (!salario.trim().equals("")) {
			funcionario.setSalario(new BigDecimal(salario));
		}
		
		return true;
	}

}
