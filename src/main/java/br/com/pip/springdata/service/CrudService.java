package br.com.pip.springdata.service;

import java.util.Optional;
import java.util.Scanner;

import org.springframework.data.repository.CrudRepository;

public abstract class CrudService<T> {
	
	private CrudRepository<T, Long> repository;
	private String title;

	public CrudService(CrudRepository<T, Long> repository, String title) {
		this.repository = repository;
		this.title = title;
	}
	
	protected abstract T instanciar();
	protected abstract boolean popular(Scanner scanner, T obj);
	
	public void inicial(Scanner scanner) {
		int action = -1;
		
		while (action != 0) {
			System.out.println(title + " ------------------");
			System.out.println("0 - voltar");
			System.out.println("1 - listar");
			System.out.println("2 - criar");
			System.out.println("3 - atualizar");
			System.out.println("4 - excluir");
			
			mostrarOpcoesPersonalizadas();
		
			action = scanner.nextInt();
			scanner.nextLine();
			
			if (action == 1) listar(scanner);
			else if (action == 2) criar(scanner);
			else if (action == 3) atualizar(scanner);
			else if (action == 4) excluir(scanner);
			else if (action != 0) opcaoPersonalizada(scanner, action);
		}
	}
	
	protected void mostrarOpcoesPersonalizadas() {
	}
	
	protected void opcaoPersonalizada(Scanner scanner, int action) {
	}

	protected void listar(Scanner scanner) {
		Iterable<T> objects = repository.findAll();
		objects.forEach(System.out::println);
	}
	
	protected void criar(Scanner scanner) {
		T obj = instanciar();
		if (popular(scanner, obj)) {
			repository.save(obj);
			System.out.println("Salvo!");
		}
	}
	
	protected void atualizar(Scanner scanner) {
		Optional<T> optObj = consultaPorId(scanner);
		
		if (optObj.isPresent()) {
			if (popular(scanner, optObj.get())) {
				repository.save(optObj.get());
			}
		}
		else {
			System.out.println("Cargo não encontrado.");
		}
	}
	
	protected void excluir(Scanner scanner) {
		Optional<T> optObj = consultaPorId(scanner);
		
		if (optObj.isPresent()) {
			repository.delete(optObj.get());
		}
		else {
			System.out.println("Cargo não encontrado.");
		}
	}
	
	protected Optional<T> consultaPorId(Scanner scanner) {
		System.out.print("ID: ");
		long id = scanner.nextLong();
		scanner.nextLine();
		return repository.findById(id);
	}

}
