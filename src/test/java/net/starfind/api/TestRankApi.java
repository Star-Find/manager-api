package net.starfind.api;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.Charset;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import net.starfind.api.controller.RankController;
import net.starfind.api.model.RankedPlayer;
import net.starfind.api.repository.RankRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestRankApi.class})
@WebAppConfiguration
public class TestRankApi {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
        MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
 
    private MockMvc mockMvc;
    
    @Mock
    private RankRepository repo;
	
	@InjectMocks
	private RankController controller;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testGetRank () throws Exception {
		String id = "fc945d35-75a1-40e3-b9e8-11c803c25807";
		LocalDate added = LocalDate.of(2017, 3, 19); 
		RankedPlayer player = new RankedPlayer(id, added);
		
		when(repo.findOne(id)).thenReturn(player);
		
		mockMvc.perform(get("/ranks/"+id+"/"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.id", is(id)));
	}
}
