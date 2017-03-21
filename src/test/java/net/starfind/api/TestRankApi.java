package net.starfind.api;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.Charset;
import java.time.LocalDate;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
}
