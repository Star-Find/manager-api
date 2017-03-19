package net.starfind.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.starfind.api.model.RankedPlayer;

@RestController
@RequestMapping(path="/ranks", produces="application/json", consumes="application/json")
public class RankController {

	@RequestMapping(method=RequestMethod.GET)
	public List<RankedPlayer> getRanks () {
		return null;
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes="application/json")
	public RankedPlayer addRank(RankedPlayer member) {
		return member;
	}
	
	@RequestMapping(method=RequestMethod.PATCH)
	public RankedPlayer updateMember() {
		return null;
	}
	
	@RequestMapping(path="{id}/name", method={RequestMethod.POST, RequestMethod.PUT}, consumes="application/json")
	public RankedPlayer updateName (UUID id, String name) {
		return null;
	}
}
