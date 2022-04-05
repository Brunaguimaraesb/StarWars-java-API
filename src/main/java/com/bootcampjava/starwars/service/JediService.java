package com.bootcampjava.starwars.service;

import com.bootcampjava.starwars.model.Jedi;
import com.bootcampjava.starwars.repository.JediRepositoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JediService {
    //serve para criar log da aplicação
    private static final Logger logger = LogManager.getLogger(JediService.class);

    //repository
    private final JediRepositoryImpl jediRepositoryImpl;


    public JediService(JediRepositoryImpl jediRepositoryImpl) {
        this.jediRepositoryImpl = jediRepositoryImpl;
    }

    public Optional<Jedi> findById(Integer id) { //busca por id

        logger.info("Find Jedi with id: {}", id);

        return jediRepositoryImpl.findById(id);
    }

    public List<Jedi> findAll() { //busca todos

        logger.info("Bring  all the Jedis from the Galaxy");

        return jediRepositoryImpl.findAll();
    }

    public Jedi save(Jedi jedi) { //salva as informações do jedi
        jedi.setVersion(1);

        logger.info("Save Jedi from system");

        return jediRepositoryImpl.save(jedi);
    }

    public boolean update(Jedi jedi) {
        boolean updated = false;

        Jedi savedJedi = this.save(jedi);

        if(savedJedi != null) updated = true;

        return updated;
    }

    public boolean delete(Integer id) {
        return true;
    }
}
