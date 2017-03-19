package net.starfind.api.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import net.starfind.api.model.RankedPlayer;

@Repository
public interface RankRepository extends PagingAndSortingRepository<RankedPlayer, String> {

	public List<RankedPlayer> findAll();
}
