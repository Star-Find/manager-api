package net.starfind.api.controller;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.starfind.api.model.NameChange;
import net.starfind.api.model.RankedPlayer;
import net.starfind.api.model.Role;
import net.starfind.api.repository.RankRepository;

@RestController
@RequestMapping(path="/ranks/", produces="application/json")
public class RankController {
	
	@Autowired
	private RankRepository rankRepository;

	//@Secured("ROLE_ANNONYMOUS")
	@RequestMapping(method=RequestMethod.GET)
	public Page<RankedPlayer> getRanks (Pageable pageable) {
		return rankRepository.findAll(pageable);
	}
	
	@RequestMapping(path="{id}", method=RequestMethod.GET)
	public RankedPlayer getRank(@PathVariable("id") UUID id) {
		return rankRepository.findOne(id);
	}

	//@PreAuthorize("hasRole('admin')")
	@RequestMapping(method=RequestMethod.POST)
	public RankedPlayer addRank(RankedPlayer member) {
		return rankRepository.save(member);
	}

	//@PreAuthorize("hasRole('admin')")
	@RequestMapping(path="{id}/name", method={RequestMethod.POST, RequestMethod.PUT})
	public RankedPlayer updateName (
			@PathVariable("id") UUID id, 
			@RequestBody(required=true) NameChange nameChange) {
		RankedPlayer player = rankRepository.findOne(id);
		player.setName(nameChange.getName(), Optional.ofNullable(nameChange.getChangeDate()));
		return rankRepository.save(player);
	}

	//@PreAuthorize("hasRole('admin')")
	@RequestMapping(path="{id}/role", method={RequestMethod.POST, RequestMethod.PUT})
	public RankedPlayer updateRole (
			@PathVariable("id") UUID id, 
			@RequestBody
			@RequestParam(name="role", required=true) Role role,
			@RequestParam("note") Optional<String> note,
			@RequestParam("date") Optional<LocalDate> dateChanged) {
		RankedPlayer player = rankRepository.findOne(id);
		player.setRole(role, note, dateChanged);
		return rankRepository.save(player);
	}
}
