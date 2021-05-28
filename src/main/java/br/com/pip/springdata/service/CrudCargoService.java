package br.com.pip.springdata.service;

import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.pip.springdata.orm.Cargo;
import br.com.pip.springdata.orm.Funcionario;
import br.com.pip.springdata.repository.CargoRepository;
import br.com.pip.springdata.repository.FuncionarioRepository;

@Service
public class CrudCargoService extends CrudService<Cargo> {

	private FuncionarioRepository funcionarios;

	public CrudCargoService(CargoRepository repository, FuncionarioRepository funcionarios) {
		super(repository, "CARGOS");
		this.funcionarios = funcionarios;
	}

	@Override
	protected Cargo instanciar() {
		return new Cargo();
	}

	@Override
	protected boolean popular(Scanner scanner, Cargo cargo) {
		System.out.println("Descrição: ");
		String descricao = scanner.nextLine();
		
		if (descricao.trim().equals("")) {
			System.out.println("É preciso informar uma descrição.");
			return false;
		}
		
		cargo.setDescricao(descricao);
		return true;
	}
	
	@Override
	protected void mostrarOpcoesPersonalizadas() {
		System.out.println("5 - Listar funcionários");
	}
	
	@Override
	protected void opcaoPersonalizada(Scanner scanner, int action) {
		if (action == 5) {
			System.out.println("ID do cargo: ");
			long id = scanner.nextLong();
			List<Funcionario> list = funcionarios.findByCargoId(id);
			list.forEach(System.out::println);
		}
	}

}
