package com.devsuperior.dslist.service;

import com.devsuperior.dslist.dto.GameListDTO;
import com.devsuperior.dslist.entities.GameList;
import com.devsuperior.dslist.projections.GameMinProjection;
import com.devsuperior.dslist.repositories.GameListRepository;
import com.devsuperior.dslist.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameListService {

    @Autowired
    private GameListRepository repository;

    @Autowired
    private GameRepository gameRepository;

    @Transactional(readOnly = true)
    public List<GameListDTO> findAll() {
       List<GameList> result = repository.findAll();
       return result.stream().map(GameListDTO::new).toList();
    }

    @Transactional
    public void move(Long listId, int soucerIndex, int destinationIndex) {
        List<GameMinProjection> list = gameRepository.searchByList(listId);

        GameMinProjection obj = list.remove(soucerIndex);
        list.add(destinationIndex, obj);

        int min = soucerIndex < destinationIndex ? soucerIndex : destinationIndex;
        int max = soucerIndex < destinationIndex ? destinationIndex : soucerIndex;

        for(int i = min; i <= max; i++) {
            repository.updateBelongingPosition(listId, listId.get(i).getId(), i);
        }
    }
}
