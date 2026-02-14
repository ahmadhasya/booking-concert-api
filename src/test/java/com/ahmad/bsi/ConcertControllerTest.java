package com.ahmad.bsi;

import com.ahmad.bsi.model.Concert;
import com.ahmad.bsi.model.User;
import com.ahmad.bsi.service.ConcertService;
import com.ahmad.bsi.service.UserService;
import com.ahmad.bsi.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ConcertControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ConcertService concertService;
    @Autowired
    private UserService userService;

    @Test
    void testSaveConcert() throws Exception {
        User user = this.userService.findByEmail("admin@gmail.com");
        String token = JwtUtil.generateToken(user.getEmail());
        String concertJson = """
                {
                  "name": "Konser Noah 2026",
                  "artist": "Noah",
                  "venue": "Lapangan",
                  "datetime": "2026-06-15T20:00:00+07:00",
                  "status": "upcoming",
                  "vipPrice": 900000,
                  "vipCapacity": 30,
                  "standardPrice": 300000,
                  "standardCapacity": 100,
                  "generalAdmissionPrice": 0,
                  "generalAdmissionCapacity": 0
                }
                """;

        mockMvc.perform(post("/api/v1/concerts")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(concertJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Konser Noah 2026"))
                .andExpect(jsonPath("$.artist").value("Noah"));

        assertThat(this.concertService.findAll()).isNotNull().isNotEmpty();
    }

    @Test
    void testUpdateConcert() throws Exception {
        User user = this.userService.findByEmail("admin@gmail.com");
        String token = JwtUtil.generateToken(user.getEmail());
        String concertJson = """
                {
                  "name": "Konser Noah 2026",
                  "artist": "Noah",
                  "venue": "Stadion",
                  "datetime": "2026-06-15T20:00:00+07:00",
                  "status": "upcoming",
                  "vipPrice": 900000,
                  "vipCapacity": 30,
                  "standardPrice": 300000,
                  "standardCapacity": 100,
                  "generalAdmissionPrice": 0,
                  "generalAdmissionCapacity": 0
                }
                """;

        mockMvc.perform(put("/api/v1/concerts/1")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(concertJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Konser Noah 2026"))
                .andExpect(jsonPath("$.artist").value("Noah"));

        Concert newConcert = this.concertService.findById(1L);
        assertThat(newConcert.getVenue()).isEqualTo("Stadion");
    }

    @Test
    void testGetConcerts() throws Exception {
        mockMvc.perform(get("/api/v1/concerts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testSearchConcerts() throws Exception {
        mockMvc.perform(get("/api/v1/concerts")
                        .param("search", "Noah"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].artist").value("Noah"));
    }

    @Test
    void testDetailConcerts() throws Exception {
        Concert concert = new Concert();
        concert.setName("Konser Noah 2026");
        concert.setArtist("Noah");
        concert.setVenue("Stadion");
        concert.setDatetime("2026-06-15T20:00:00+07:00");
        concert.setStatus("upcoming");
        concert.setVipPrice(900000L);
        concert.setVipCapacity(30);
        concert.setVipSold(0);
        concert.setStandardPrice(300000L);
        concert.setStandardCapacity(100);
        concert.setStandardSold(0);
        concert.setGeneralAdmissionPrice(0L);
        concert.setGeneralAdmissionCapacity(0);
        concert.setGeneralAdmissionSold(0);

        this.concertService.save(concert);

        mockMvc.perform(get("/api/v1/concerts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.venue").value("Stadion"));
    }
}

