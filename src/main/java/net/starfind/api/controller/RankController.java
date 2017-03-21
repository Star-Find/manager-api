package net.starfind.api.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import net.starfind.api.model.RankedPlayer;
import net.starfind.api.model.Role;
import net.starfind.api.repository.RankRepository;

@RestController
@RequestMapping(path="/ranks/", produces="application/json", consumes="application/json")
public class RankController {
	
	@Autowired
	private RankRepository rankRepository;

	//@Secured("ROLE_ANNONYMOUS")
	@RequestMapping(method=RequestMethod.GET, consumes="*/*")
	public Page<RankedPlayer> getRanks (Pageable pageable) {
		return rankRepository.findAll(pageable);
	}
	
	@RequestMapping(path="{id}", method=RequestMethod.GET, consumes="*/*")
	public RankedPlayer getRank(@PathVariable("id") UUID id) {
		return rankRepository.findOne(id);
	}

	//@PreAuthorize("hasRole('admin')")
	@RequestMapping(method=RequestMethod.POST, consumes="application/json")
	public RankedPlayer addRank(RankedPlayer member) {
		return rankRepository.save(member);
	}

	//@PreAuthorize("hasRole('admin')")
	@RequestMapping(path="{id}/name", method={RequestMethod.POST, RequestMethod.PUT}, consumes="application/json")
	public RankedPlayer updateName (
			@PathVariable("id") UUID id, 
			@RequestPart(name="name", required=true) String name,
			@RequestPart("date") Optional<LocalDate> dateChanged) {
		RankedPlayer player = rankRepository.findOne(id);
		player.setName(name, dateChanged);
		return rankRepository.save(player);
	}

	//@PreAuthorize("hasRole('admin')")
	@RequestMapping(path="{id}/role", method={RequestMethod.POST, RequestMethod.PUT}, consumes="application/json")
	public RankedPlayer updateRole (
			@PathVariable("id") UUID id, 
			@RequestPart(name="role", required=true) Role role,
			@RequestPart("note") Optional<String> note,
			@RequestPart("date") Optional<LocalDate> dateChanged) {
		RankedPlayer player = rankRepository.findOne(id);
		player.setRole(role, note, dateChanged);
		return rankRepository.save(player);
	}
}
