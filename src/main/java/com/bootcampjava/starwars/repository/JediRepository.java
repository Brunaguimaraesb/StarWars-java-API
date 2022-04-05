package com.bootcampjava.starwars.repository;

import com.bootcampjava.starwars.model.Jedi;

import java.util.List;
import java.util.Optional;

public interface JediRepository {

    //retorno opcional
    Optional<Jedi> findById(int id);

    //retorna uma lista
    List<Jedi> findAll();

    //atualizar
    boolean update(Jedi jedi);

    //salva
    Jedi save(Jedi jedi);

    //deleta
    boolean delete(Integer id);
}
