package net.starfind.api;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.starfind.api.controller.RankController;
import net.starfind.api.model.RankedPlayer;
import net.starfind.api.repository.RankRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=ManagerApplication.class)
@ContextConfiguration(classes=ManagerApplication.class)
@AutoConfigureMockMvc
public class TestRankApi {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
        MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	
	public static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired
    private RankRepository rankRepository;

	@InjectMocks
	private RankController controller;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@After
	public void cleanup () {
		rankRepository.deleteAll();
	}

	@Test
	public void testGetRank () throws Exception {
		LocalDate added = LocalDate.of(2017, 3, 19); 
		String name = "Test";
		RankedPlayer player = new RankedPlayer(name, added);
		
		player = rankRepository.save(player);
		
		mockMvc.perform(get("/ranks/"+player.getId()+"/"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.id", is(player.getId().toString())))
			.andExpect(jsonPath("$.name", is(name)))
			.andExpect(jsonPath("$.addedDate", is(added.toString())));
	}

	@Test
	public void testGetSortedRanks () throws Exception {
		RankedPlayer player1 = rankRepository.save(new RankedPlayer("Test 1", LocalDate.of(2017, 3, 19)));
		RankedPlayer player2 = rankRepository.save(new RankedPlayer("Test 2", LocalDate.of(2017, 2, 21)));
		
		mockMvc.perform(get("/ranks/?sort=name"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.content[0].id", is(player1.getId().toString())))
			.andExpect(jsonPath("$.content[0].name", is(player1.getName())))
			.andExpect(jsonPath("$.content[0].addedDate", is(player1.getAddedDate().toString())))
			.andExpect(jsonPath("$.content[1].id", is(player2.getId().toString())))
			.andExpect(jsonPath("$.content[1].name", is(player2.getName())))
			.andExpect(jsonPath("$.content[1].addedDate", is(player2.getAddedDate().toString())));
	}
	
	@Test
	public void testSetRankName () throws Exception {
		RankedPlayer player = rankRepository.save(new RankedPlayer("Test 1", LocalDate.of(2017, 3, 14)));
		
		String newName = "New Name";
		LocalDate changeDate = LocalDate.of(2017, 3, 19);
		
		Map<String, String> request = new HashMap<>();
		request.put("name", newName);
		request.put("changeDate", changeDate.toString());
		
		String requestJson = JSON_MAPPER.writeValueAsString(request);
		
		mockMvc.perform(put("/ranks/{id}/name", player.getId().toString())
					.contentType(APPLICATION_JSON_UTF8).content(requestJson))
        	.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.id", is(player.getId().toString())))
			.andExpect(jsonPath("$.name", is(newName)))
			.andExpect(jsonPath("$.nameHistory[0].name", is(newName)))
			.andExpect(jsonPath("$.nameHistory[0].changeDate", is(changeDate.toString())));
	}
}
