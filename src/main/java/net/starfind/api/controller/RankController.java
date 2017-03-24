package net.starfind.api.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.starfind.api.model.NameChange;
import net.starfind.api.model.RankedPlayer;
import net.starfind.api.model.RoleChange;
import net.starfind.api.repository.RankRepository;

@RestController
@RequestMapping(path="/ranks/", produces="application/json")
public class RankController {
	
	@Autowired
	private RankRepository rankRepository;

	@RequestMapping(method=RequestMethod.GET)
	public Page<RankedPlayer> getRanks (Pageable pageable) {
		return rankRepository.findAll(pageable);
	}
	
	@RequestMapping(path="{id}", method=RequestMethod.GET)
	public RankedPlayer getRank(@PathVariable("id") UUID id) {
		return rankRepository.findOne(id);
	}

	@PreAuthorize("hasRole('admin')")
	@RequestMapping(method=RequestMethod.POST)
	public RankedPlayer addRank(@Valid @RequestBody(required=true) RankedPlayer member) {
		return rankRepository.save(member);
	}

	@PreAuthorize("hasRole('admin')")
	@RequestMapping(path="{id}/name", method={RequestMethod.POST, RequestMethod.PUT})
	public RankedPlayer updateName (
			@PathVariable("id") UUID id, 
			@Valid @RequestBody(required=true) NameChange nameChange) {
		RankedPlayer player = rankRepository.findOne(id);
		if (nameChange.getChangeDate() == null) {
			player.updateName(nameChange.getName());			
		} else {
			player.updateName(nameChange.getName(), nameChange.getChangeDate());			
		}
		return rankRepository.save(player);
	}

	@PreAuthorize("hasRole('admin')")
	@RequestMapping(path="{id}/role", method={RequestMethod.POST, RequestMethod.PUT})
	public RankedPlayer updateRole (
			@PathVariable("id") UUID id, 
			@Valid @RequestBody(required=true) RoleChange roleChange) {
		RankedPlayer player = rankRepository.findOne(id);
		if (roleChange.getChangeDate() == null) {
			player.updateRole(roleChange.getRole(), roleChange.getNotes());
		} else {
			player.updateRole(roleChange.getRole(), roleChange.getNotes(), roleChange.getChangeDate());
		}
		return rankRepository.save(player);
	}
}
