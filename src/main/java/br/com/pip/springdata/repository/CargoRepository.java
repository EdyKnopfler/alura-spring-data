package br.com.pip.springdata.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.pip.springdata.orm.Cargo;

@Repository
public interface CargoRepository extends CrudRepository<Cargo, Long> {

}
