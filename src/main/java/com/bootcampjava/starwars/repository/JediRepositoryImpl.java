package com.bootcampjava.starwars.repository;

import com.bootcampjava.starwars.model.Jedi;
import com.bootcampjava.starwars.service.JediService
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JediRepositoryImpl implements JediRepository{

    private static final Logger logger = LogManager.getLogger(JediRepositoryImpl.class);

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    // construtor para identificar o jdbc template e validar o funcionamento dele
    public JediRepositoryImpl(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;

        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("jedis") //nome da tabela
                .usingGeneratedKeyColumns("id"); //id da coluna
    }


    @Override
    public Optional<Jedi> findById(int id) {
        try {
            Jedi jedi = jdbcTemplate.queryForObject("SELECT * FROM jedis WHERE id = ?",
                    new Object[]{id},
                    (rs, rowNum) -> { //mapear o objeto e setar as informações dentro dele
                        Jedi p = new Jedi();
                        p.setId(rs.getInt("id"));
                        p.setName(rs.getString("name"));
                        p.setStrength(rs.getInt("strength"));
                        p.setVersion(rs.getInt("version"));
                        return p;
                    });
            return Optional.of(jedi);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Jedi> findAll() {
        return jdbcTemplate.query("SELECT * FROM jedis",
                (rs, rowNumber) -> {
                    Jedi jedi = new Jedi();
                    jedi.setId(rs.getInt("id"));
                    jedi.setName(rs.getString("name"));
                    jedi.setStrength(rs.getInt("strength"));
                    jedi.setVersion(rs.getInt("version"));
                    return jedi;
                });
    }

    @Override
    public boolean update(Jedi jedi) { //busca e atualiza a tabela
        return jdbcTemplate.update("UPDATE jedis SET name = ?, strength = ?, version = ? WHERE id = ?",
                jedi.getName(),
                jedi.getStrength(),
                jedi.getVersion(),
                jedi.getId()) == 1;
    }

    @Override
    public Jedi save(Jedi jedi) { //insere a informação
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("name", jedi.getName());
        parameters.put("strength", jedi.getStrength());
        parameters.put("version", jedi.getVersion());

        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters); //define o id

        logger.info("Inserting jedi into database, generated key is: {}", newId);

        jedi.setId((Integer) newId);

        return jedi;
    }
    @Override
    public boolean delete(Integer id) {
        return jdbcTemplate.update("DELETE FROM jedis WHERE id = ?", id) == 1;
    }
}
