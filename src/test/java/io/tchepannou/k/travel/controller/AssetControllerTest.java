package io.tchepannou.k.travel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tchepannou.k.travel.client.request.CreateAssetRequest;
import io.tchepannou.k.travel.client.request.UpdateAssetRequest;
import io.tchepannou.k.travel.client.request.UpdateProductRequest;
import io.tchepannou.k.travel.client.response.CreateAssetResponse;
import io.tchepannou.k.travel.client.response.UpdateAssetResponse;
import io.tchepannou.k.travel.client.response.UpdateProductResponse;
import io.tchepannou.k.travel.dao.AssetDao;
import io.tchepannou.k.travel.domain.Asset;
import io.tchepannou.k.travel.domain.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AssetDao assetDao;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp(){
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        new ResourceDatabasePopulator(
                new ClassPathResource("/sql/clean.sql"),
                new ClassPathResource("/sql/AssetController.sql")
        ).execute(dataSource);
    }

    @Test
    public void findById() throws Exception {
        mockMvc
                .perform(get("/travel/v1/asset/100"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.asset.immatriculationNumber", is("QC 123")))
                .andExpect(jsonPath("$.asset.capacity", is(30)))
                .andExpect(jsonPath("$.asset.model", is("Volvo XS")))
                .andExpect(jsonPath("$.asset.year", is(2013)))
                .andExpect(jsonPath("$.asset.travelProviderId", is(1)))

                .andExpect(jsonPath("$.asset.assetType.id", is(100)))
                .andExpect(jsonPath("$.asset.assetType.name", is("BUS")))
        ;
    }

    @Test
    public void findByIdNotFound() throws Exception {
        mockMvc
                .perform(get("/travel/v1/asset/9999999"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    public void shouldCreateProduct() throws Exception {
        // Given
        final CreateAssetRequest req = new CreateAssetRequest();
        req.setImmatriculationNumber("CreateAssetRequest");
        req.setAssetTypeName("BUS");
        req.setCapacity(11);
        req.setModel("Honda Civic");
        req.setTravelProviderId(7);
        req.setYear(2011);

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        final String jsonResponse = mockMvc
                .perform(
                        post("/travel/v1/assets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
                ;
        CreateAssetResponse resp = mapper.readValue(jsonResponse, CreateAssetResponse.class);

        // Then
        Asset asset = assetDao.findOne(resp.getId());
        assertThat(asset.getImmatriculationNumber()).isEqualTo("CreateAssetRequest");
        assertThat(asset.getModel()).isEqualTo("Honda Civic");
        assertThat(asset.getCapacity()).isEqualTo(11);
        assertThat(asset.getTravelProviderId()).isEqualTo(7);
        assertThat(asset.getYear()).isEqualTo(2011);
    }

    @Test
    public void shouldReturn400WhenCreatingProductWithInvalidTypeName() throws Exception {
        // Given
        final CreateAssetRequest req = new CreateAssetRequest();
        req.setImmatriculationNumber("CreateAssetRequest");
        req.setAssetTypeName("xxx");

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        post("/travel/v1/assets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                ;
    }

    @Test
    public void shouldUpdateProduct() throws Exception {
        // Given
        final UpdateAssetRequest req = new UpdateAssetRequest();
        req.setImmatriculationNumber("UpdateAssetRequest");
        req.setCapacity(11);
        req.setModel("Honda Civic");
        req.setYear(2011);

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        final String jsonResponse = mockMvc
                .perform(
                        post("/travel/v1/asset/200")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
                ;
        UpdateAssetResponse resp = mapper.readValue(jsonResponse, UpdateAssetResponse.class);

        // Then
        Asset asset = assetDao.findOne(resp.getId());
        assertThat(asset.getImmatriculationNumber()).isEqualTo("UpdateAssetRequest");
        assertThat(asset.getModel()).isEqualTo("Honda Civic");
        assertThat(asset.getCapacity()).isEqualTo(11);
        assertThat(asset.getTravelProviderId()).isEqualTo(5);
        assertThat(asset.getYear()).isEqualTo(2011);
    }

    @Test
    public void shouldReturn404WhenUpdatingProductWithInvalidId() throws Exception {
        // Given
        final UpdateAssetRequest req = new UpdateAssetRequest();
        req.setImmatriculationNumber("UpdateAssetRequest");
        req.setCapacity(11);
        req.setModel("Honda Civic");
        req.setYear(2011);

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        post("/travel/v1/asset/999999")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                ;
    }

}
