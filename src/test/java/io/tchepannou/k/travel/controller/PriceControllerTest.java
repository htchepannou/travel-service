package io.tchepannou.k.travel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tchepannou.k.travel.client.request.SearchPriceRequest;
import io.tchepannou.k.travel.client.request.SetPriceRequest;
import io.tchepannou.k.travel.client.response.SetPriceResponse;
import io.tchepannou.k.travel.dao.PriceDao;
import io.tchepannou.k.travel.domain.Price;
import io.tchepannou.k.travel.util.DateUtil;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import static io.tchepannou.k.travel.util.DateUtil.createDateFormat;
import static io.tchepannou.k.travel.util.DateUtil.toDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PriceDao priceDao;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp(){
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        new ResourceDatabasePopulator(
                new ClassPathResource("/sql/clean.sql"),
                new ClassPathResource("/sql/PriceController.sql")
        ).execute(dataSource);
    }

    @Test
    public void findById() throws Exception {
        mockMvc
                .perform(get("/travel/v1/price/100"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price.id", is(100)))
                .andExpect(jsonPath("$.price.productId", is(100)))
                .andExpect(jsonPath("$.price.amount", is(101.0)))
                .andExpect(jsonPath("$.price.currencyCode", is("USD")))
                .andExpect(jsonPath("$.price.fromDateTime", is("2017-01-02T00:00:00-0500")))
                .andExpect(jsonPath("$.price.toDateTime", is("2017-04-05T00:00:00-0400")))

                .andExpect(jsonPath("$.price.priceType.id", is(1)))
                .andExpect(jsonPath("$.price.priceType.name", is("ONE_WAY")))

        ;
    }

    @Test
    public void shouldReturn404WhenSearchingInvalidPriceId() throws Exception {
        mockMvc
                .perform(get("/travel/v1/price/9999999"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    public void findByProductId() throws Exception {
        mockMvc
                .perform(get("/travel/v1/product/300/prices"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prices.length()", is(2)))
                .andExpect(jsonPath("$.prices[0].id", is(301)))
                .andExpect(jsonPath("$.prices[0].productId", is(300)))
                .andExpect(jsonPath("$.prices[0].amount", is(311.0)))
                .andExpect(jsonPath("$.prices[0].currencyCode", is("USD")))
                .andExpect(jsonPath("$.prices[0].fromDateTime", is("2017-01-01T00:00:00-0500")))
                .andExpect(jsonPath("$.prices[0].toDateTime", is("2017-04-05T00:00:00-0400")))
                .andExpect(jsonPath("$.prices[0].priceType.id", is(1)))
                .andExpect(jsonPath("$.prices[0].priceType.name", is("ONE_WAY")))

                .andExpect(jsonPath("$.prices[1].id", is(302)))
                .andExpect(jsonPath("$.prices[1].productId", is(300)))
                .andExpect(jsonPath("$.prices[1].amount", is(312.0)))
                .andExpect(jsonPath("$.prices[1].currencyCode", is("USD")))
                .andExpect(jsonPath("$.prices[1].fromDateTime", startsWith("2017-01-03")))
                .andExpect(jsonPath("$.prices[1].toDateTime", startsWith("2017-04-06")))
                .andExpect(jsonPath("$.prices[1].priceType.id", is(2)))
                .andExpect(jsonPath("$.prices[1].priceType.name", is("RETURN")))
        ;
    }


    // CREATE
    @Test
    public void shouldCreatePrice() throws Exception {
        // Given
        final SetPriceRequest req = new SetPriceRequest();
        req.setAmount(150.0);
        req.setCurrencyCode("USD");
        req.setFromDateTime(toDate("2017-01-02", "yyyy-MM-dd"));
        req.setToDateTime(toDate("2017-03-05", "yyyy-MM-dd"));

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        final String jsonResponse = mockMvc
                .perform(
                        post("/travel/v1/product/200/prices/ONE_WAY")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
                ;
        SetPriceResponse resp = mapper.readValue(jsonResponse, SetPriceResponse.class);

        // Then
        final DateFormat df = createDateFormat("yyyy-MM-dd");

        Price price = priceDao.findOne(resp.getId());
        assertThat(df.format(price.getFromDateTime())).isEqualTo("2017-01-02");
        assertThat(df.format(price.getToDateTime())).isEqualTo("2017-03-05");
        assertThat(price.getCurrencyCode()).isEqualTo("USD");
        assertThat(price.getAmount()).isEqualTo(150.0);
        assertThat(price.getProductId()).isEqualTo(200);
    }



    // UPDATE
    @Test
    public void shouldUpdatePrice() throws Exception {
        // Given
        final SetPriceRequest req = new SetPriceRequest();
        req.setAmount(150.0);
        req.setCurrencyCode("USD");
        req.setFromDateTime(toDate("2017-01-02", "yyyy-MM-dd"));
        req.setToDateTime(toDate("2017-03-05", "yyyy-MM-dd"));

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        final String jsonResponse = mockMvc
                .perform(
                        post("/travel/v1/product/200/prices/RETURN")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
                ;
        SetPriceResponse resp = mapper.readValue(jsonResponse, SetPriceResponse.class);

        // Then
        final DateFormat df = createDateFormat("yyyy-MM-dd");

        Price price = priceDao.findOne(resp.getId());
        assertThat(df.format(price.getFromDateTime())).isEqualTo("2017-01-02");
        assertThat(df.format(price.getToDateTime())).isEqualTo("2017-03-05");
        assertThat(price.getCurrencyCode()).isEqualTo("USD");
        assertThat(price.getAmount()).isEqualTo(150.0);
        assertThat(price.getProductId()).isEqualTo(200);
    }

    @Test
    public void shouldReturn404WhenSettingPriceWithInvalidProductId() throws Exception {
        // Given
        final SetPriceRequest req = new SetPriceRequest();
        req.setAmount(150.0);
        req.setCurrencyCode("USD");

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
         mockMvc
                .perform(
                        post("/travel/v1/product/99999999/prices/RETURN")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                ;
    }

    @Test
    public void shouldReturn400WhenSettingPriceWithInvalidPriceType() throws Exception {
        // Given
        final SetPriceRequest req = new SetPriceRequest();
        req.setAmount(150.0);
        req.setCurrencyCode("USD");

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        post("/travel/v1/product/200/prices/__INVALID__")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
        ;
    }


    // SEARCH
    @Test
    public void searchPricesHappyPath () throws Exception{
        // Given
        final SearchPriceRequest req = new SearchPriceRequest();
        req.setProductIds(Arrays.asList(1000, 2000, 3000, 29990));
        req.setPriceTypeNames(Arrays.asList("ONE_WAY", "RETURN"));
        req.setDepartureDate(DateUtil.toDate("2017-02-02", "yyyy-MM-dd"));

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        post("/travel/v1/prices/search")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prices.length()", is(6)))
                .andExpect(jsonPath("$..id", hasItems(1001, 1002, 2001, 2002, 3001, 3002)))
        ;
    }

    @Test
    public void searchPricesDepartureLowerBound () throws Exception{
        // Given
        final SearchPriceRequest req = new SearchPriceRequest();
        req.setProductIds(Arrays.asList(1000, 2000, 3000, 29990));
        req.setPriceTypeNames(Arrays.asList("ONE_WAY", "RETURN"));
        req.setDepartureDate(DateUtil.toDate("2017-01-02", "yyyy-MM-dd"));

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        post("/travel/v1/prices/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prices.length()", is(4)))
                .andExpect(jsonPath("$..id", hasItems(1001, 2001, 2002, 3001)))
        ;
    }

    @Test
    public void searchPricesDepartureUpperBound () throws Exception{
        // Given
        final SearchPriceRequest req = new SearchPriceRequest();
        req.setProductIds(Arrays.asList(1000, 2000, 3000, 29990));
        req.setPriceTypeNames(Arrays.asList("ONE_WAY", "RETURN"));
        req.setDepartureDate(DateUtil.toDate("2017-05-16", "yyyy-MM-dd"));

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        post("/travel/v1/prices/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prices.length()", is(3)))
                .andExpect(jsonPath("$..id", hasItems(2001, 2002, 3002)))
        ;
    }

    @Test
    public void searchPricesDepartureBelowLowerBound () throws Exception{
        // Given
        final SearchPriceRequest req = new SearchPriceRequest();
        req.setProductIds(Arrays.asList(1000, 2000, 3000, 29990));
        req.setPriceTypeNames(Arrays.asList("ONE_WAY", "RETURN"));
        req.setDepartureDate(DateUtil.toDate("2017-01-01", "yyyy-MM-dd"));

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        post("/travel/v1/prices/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prices.length()", is(2)))
                .andExpect(jsonPath("$..id", hasItems(2001, 2002)))
        ;
    }

    @Test
    public void searchPricesDepartureAboveUpperBound () throws Exception{
        // Given
        final SearchPriceRequest req = new SearchPriceRequest();
        req.setProductIds(Arrays.asList(1000, 2000, 3000, 29990));
        req.setPriceTypeNames(Arrays.asList("ONE_WAY", "RETURN"));
        req.setDepartureDate(DateUtil.toDate("2020-01-01", "yyyy-MM-dd"));

        // When
        final String jsonRequest = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        post("/travel/v1/prices/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest)
                )

                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prices.length()", is(2)))
                .andExpect(jsonPath("$..id", hasItems(2001, 2002)))
        ;
    }
}
