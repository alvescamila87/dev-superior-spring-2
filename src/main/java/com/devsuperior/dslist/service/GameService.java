package com.devsuperior.dslist.service;

import com.devsuperior.dslist.dto.GameDTO;
import com.devsuperior.dslist.dto.GameMinDTO;
import com.devsuperior.dslist.entities.Game;
import com.devsuperior.dslist.projections.GameMinProjection;
import com.devsuperior.dslist.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository repository;

    public List<GameMinDTO> findAll() {
        List<Game> result = repository.findAll();
        List<GameMinDTO> dto = result.stream().map(GameMinDTO::new).toList();
        return dto;
    }

    @Transactional(readOnly = true)
    public GameDTO findById(Long id) {
        Game result = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID não encontrado."));
        GameDTO dto = new GameDTO(result);

        return dto;
    }

    @Transactional(readOnly = true)
    public List<GameMinDTO> findByList(Long id) {
        List<GameMinProjection> result = repository.searchByList(id);
        return result.stream().map(GameMinDTO::new).toList();
    }
}
