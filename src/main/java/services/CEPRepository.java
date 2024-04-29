package services;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CEPRepository extends CrudRepository<CEPService.CEP, Long> {

    // Método para buscar um CEP pelo ID
    Optional<CEPService.CEP> findById(Long id);

    // Método para salvar um novo CEP
    <S extends CEPService.CEP> S save(S entity);

    // Método para deletar um CEP pelo ID
    void deleteById(Long id);

    // Método para atualizar um CEP existente
    <S extends CEPService.CEP> S update(S entity);
}