package net.starfind.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import net.starfind.api.model.RankedPlayer;

@Repository
public interface RankRepository extends PagingAndSortingRepository<RankedPlayer, UUID> {

	public List<RankedPlayer> findAll();
}
