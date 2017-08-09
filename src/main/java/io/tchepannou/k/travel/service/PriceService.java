package io.tchepannou.k.travel.service;

import com.google.common.collect.Lists;
import io.tchepannou.k.travel.client.request.SearchPriceRequest;
import io.tchepannou.k.travel.client.request.SetPriceRequest;
import io.tchepannou.k.travel.client.response.GetPriceResponse;
import io.tchepannou.k.travel.client.response.GetProductPricesResponse;
import io.tchepannou.k.travel.client.response.PriceDTO;
import io.tchepannou.k.travel.client.response.PriceTypeDTO;
import io.tchepannou.k.travel.client.response.SearchPriceResponse;
import io.tchepannou.k.travel.client.response.SetPriceResponse;
import io.tchepannou.k.travel.dao.PriceDao;
import io.tchepannou.k.travel.dao.PriceRowMapper;
import io.tchepannou.k.travel.dao.PriceTypeDao;
import io.tchepannou.k.travel.dao.ProductDao;
import io.tchepannou.k.travel.domain.Price;
import io.tchepannou.k.travel.domain.PriceType;
import io.tchepannou.k.travel.domain.Product;
import io.tchepannou.k.travel.exception.BusinessErrors;
import io.tchepannou.k.travel.exception.BusinessException;
import io.tchepannou.k.travel.exception.InvalidRequestException;
import io.tchepannou.k.travel.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PriceService {
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    @Autowired PriceTypeDao priceTypeDao;
    @Autowired PriceDao priceDao;
    @Autowired ProductDao productDao;
    @Autowired PriceSearchQueryBuilder queryBuilder;
    @Autowired DataSource dataSource;

    @Transactional
    public SetPriceResponse setPrice(Integer productId, final String priceTypeName, SetPriceRequest request){
        final Product product = productDao.findOne(productId);
        if (product == null){
            throw new NotFoundException(BusinessErrors.PRODUCT_NOT_FOUND);
        }

        final PriceType priceType = priceTypeDao.findByNameIgnoreCase(priceTypeName);
        if (priceType == null){
            throw new InvalidRequestException(BusinessErrors.INVALID_PRICE_TYPE);
        }

        Price price = priceDao.findByProductIdAndPriceTypeId(productId, priceType.getId());
        if (price == null) {
            price = new Price();
            price.setPriceTypeId(priceType.getId());
            price.setProductId(product.getId());
        }

        price.setAmount(request.getAmount());
        price.setCurrencyCode(request.getCurrencyCode());
        price.setFromDate(toDate(request.getFromDate(), BusinessErrors.INVALID_FROM_DATE_FORMAT));
        price.setToDate(toDate(request.getToDate(), BusinessErrors.INVALID_FROM_DATE_FORMAT));
        priceDao.save(price);

        final SetPriceResponse response = new SetPriceResponse();
        response.setId(price.getId());
        return response;
    }

    public GetPriceResponse findById(Integer id){
        final Price price = priceDao.findOne(id);
        if (price == null){
            throw new NotFoundException(BusinessErrors.PRICE_NOT_FOUND);
        }

        final PriceType priceType = priceTypeDao.findOne(price.getPriceTypeId());

        final GetPriceResponse response = new GetPriceResponse();
        final PriceTypeDTO priceTypeDto = toPriceTypeDto(priceType);
        response.setPrice(toPriceDto(price, priceTypeDto));
        return response;
    }

    public GetProductPricesResponse findByProductId (Integer productId){
        final List<Price> prices = priceDao.findByProductId(productId);
        final Map<Integer, PriceTypeDTO> priceTypeDtoMap = toPriceTypeDtoMap(prices);

        // Response
        final GetProductPricesResponse response = new GetProductPricesResponse();
        response.setPrices(
                prices.stream()
                .map(price -> toPriceDto(price, priceTypeDtoMap.get(price.getPriceTypeId())))
                .collect(Collectors.toList()));
        return response;
    }

    public SearchPriceResponse search (SearchPriceRequest request){
        final String sql = queryBuilder.toSql(request);
        final Object[] args = queryBuilder.toArgs(request);
        final JdbcTemplate jdbc = new JdbcTemplate(dataSource);

        final List<Price> prices = jdbc.query(sql, args, new PriceRowMapper());
        final Map<Integer, PriceTypeDTO> priceTypeDtoMap = toPriceTypeDtoMap(prices);

        final SearchPriceResponse response = new SearchPriceResponse();
        response.setPrices(
                prices.stream()
                        .map(price -> toPriceDto(price, priceTypeDtoMap.get(price.getPriceTypeId())))
                        .collect(Collectors.toList()));
        return response;
    }


    //-- Private
    private Date toDate (String value, BusinessErrors error){
        if (value == null){
            return null;
        }
        try {

            final SimpleDateFormat fmt = new SimpleDateFormat(DATE_PATTERN, Locale.US);

            return new Date(fmt.parse(value).getTime());

        } catch (ParseException e){
            throw new BusinessException(error);
        }
    }

    private Map<Integer, PriceTypeDTO> toPriceTypeDtoMap(List<Price> prices){
        final Set<Integer> priceTypeIds = prices.stream()
                .map(type -> type.getPriceTypeId())
                .collect(Collectors.toSet());
        final List<PriceType> priceTypes = Lists.newArrayList(priceTypeDao.findAll(priceTypeIds));
        final List<PriceTypeDTO> priceTypeDtos = priceTypes.stream()
                .map(type -> toPriceTypeDto(type))
                .collect(Collectors.toList());

        return priceTypeDtos.stream()
                .collect(Collectors.toMap(dto  -> dto.getId(), dto -> dto));

    }

    private PriceTypeDTO toPriceTypeDto (final PriceType priceType){
        final PriceTypeDTO dto = new PriceTypeDTO();
        dto.setDescription(priceType.getDescription());
        dto.setId(priceType.getId());
        dto.setName(priceType.getName());
        return dto;
    }

    private PriceDTO toPriceDto (final Price price, final PriceTypeDTO priceType){
        final PriceDTO dto = new PriceDTO();
        dto.setAmount(price.getAmount());
        dto.setCurrencyCode(price.getCurrencyCode());
        dto.setFromDate(price.getFromDate());
        dto.setId(price.getId());
        dto.setProductId(price.getProductId());
        dto.setPriceType(priceType);
        dto.setToDate(price.getToDate());
        return dto;
    }
}
