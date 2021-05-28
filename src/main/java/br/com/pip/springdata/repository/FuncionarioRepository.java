package br.com.pip.springdata.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.pip.springdata.orm.Funcionario;
import br.com.pip.springdata.orm.FuncionarioProjecao;

// PagingAndSortingRepository: para consultas paginadas
// JpaSpecificationExecutor: consultas dinâmicas (semelhante à Criteria API do JPA)
@Repository
public interface FuncionarioRepository 
		extends PagingAndSortingRepository<Funcionario, Long>, JpaSpecificationExecutor<Funcionario> {
	
	// Derived query (Spring Data reconhece automaticamente)
	List<Funcionario> findByNomeLikeIgnoreCase(String nome);
	
	// Nome fora do padrão: podemos passar uma query JPQL
	@Query("SELECT f FROM Funcionario f WHERE f.dataContratacao >= :data AND f.salario >= :salario")
	List<Funcionario> findByContratacaoAndSalarioAPartirDe(LocalDate data, BigDecimal salario);
	
	// Por algum relacionamento
	// Equivale a: @Query("SELECT f FROM Funcionario f JOIN f.cargo c WHERE c.id = :id")
	List<Funcionario> findByCargoId(Long id);
	
	// Query nativa
	@Query(value = "SELECT * FROM funcionarios.funcionarios WHERE data_contratacao >= :data", nativeQuery = true)
	List<Funcionario> findByContratacaoMaior(LocalDate data);
	
	// Projeção necessita do @Query e de uma interface de projeção
	@Query(value = "SELECT id, nome, salario FROM funcionarios.funcionarios", nativeQuery = true)
	List<FuncionarioProjecao> findFuncionariosComSalario();
	
}
