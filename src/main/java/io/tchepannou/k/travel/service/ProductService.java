package io.tchepannou.k.travel.service;

import io.tchepannou.k.travel.client.request.CreateProductRequest;
import io.tchepannou.k.travel.client.request.CreateProductResponse;
import io.tchepannou.k.travel.client.request.ScheduledTimeRequest;
import io.tchepannou.k.travel.client.request.UpdateProductRequest;
import io.tchepannou.k.travel.client.response.GetProductResponse;
import io.tchepannou.k.travel.client.response.ProductDTO;
import io.tchepannou.k.travel.client.response.ProductTypeDTO;
import io.tchepannou.k.travel.client.response.UpdateProductResponse;
import io.tchepannou.k.travel.dao.ProductDao;
import io.tchepannou.k.travel.dao.ProductTypeDao;
import io.tchepannou.k.travel.dao.ScheduledTimeDao;
import io.tchepannou.k.travel.domain.Product;
import io.tchepannou.k.travel.domain.ProductType;
import io.tchepannou.k.travel.domain.ScheduledTime;
import io.tchepannou.k.travel.exception.BusinessErrors;
import io.tchepannou.k.travel.exception.InvalidRequestException;
import io.tchepannou.k.travel.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
public class ProductService {
    private static final String TIME_PATTERN = "HH:mm";

    @Autowired ProductDao productDao;
    @Autowired ProductTypeDao productTypeDao;
    @Autowired ScheduledTimeDao scheduledTimeDao;

    @Transactional
    public CreateProductResponse create(CreateProductRequest request){
        final ProductType productType = productTypeDao.findByNameIgnoreCase(request.getProductTypeName());
        if (productType == null){
            throw new InvalidRequestException(BusinessErrors.INVALID_PRODUCT_TYPE);
        }

        final Product product = new Product();
        product.setDescription(request.getDescription());
        product.setDestinationCityId(request.getDestinationCityId());
        product.setName(request.getName());
        product.setOriginCityId(request.getOriginCityId());
        product.setTravelProdiverId(request.getTravelProviderId());
        product.setTravelProductTypeId(productType.getId());
        productDao.save(product);

        final CreateProductResponse response = new CreateProductResponse();
        response.setId(product.getId());
        return response;
    }

    @Transactional
    public UpdateProductResponse update(Integer id, UpdateProductRequest request){
        final Product product = productDao.findOne(id);
        if (product == null){
            throw new NotFoundException(BusinessErrors.PRODUCT_NOT_FOUND);
        }

        product.setDescription(request.getDescription());
        product.setDestinationCityId(request.getDestinationCityId());
        product.setName(request.getName());
        product.setOriginCityId(request.getOriginCityId());
        productDao.save(product);

        final UpdateProductResponse response = new UpdateProductResponse();
        response.setId(product.getId());
        return response;
    }

    public GetProductResponse findById(Integer id){
        final Product product = productDao.findOne(id);
        if (product == null){
            throw new NotFoundException(BusinessErrors.PRODUCT_NOT_FOUND);
        }

        final ProductType productType = productTypeDao.findOne(product.getTravelProductTypeId());
        final ProductTypeDTO productTypeDto = new ProductTypeDTO();
        productTypeDto.setDescription(productType.getDescription());
        productTypeDto.setId(productType.getId());
        productTypeDto.setName(productType.getName());

        final ProductDTO productDto = new ProductDTO();
        productDto.setDescription(product.getDescription());
        productDto.setDestinationCityId(product.getDestinationCityId());
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setOriginCityId(product.getOriginCityId());
        productDto.setProductType(productTypeDto);
        productDto.setTravelProviderId(product.getTravelProdiverId());

        final PageRequest pageRequest = new PageRequest(0, Integer.MAX_VALUE, Sort.Direction.ASC, "dayOfWeek", "departureTime");
        final List<ScheduledTime> schedule = scheduledTimeDao.findByProductId(id, pageRequest);
        final DateFormat df = new SimpleDateFormat(TIME_PATTERN);
        for (ScheduledTime time : schedule){
            productDto.getSchedule().add(toScheduleTimeDto(time, df));
        }

        final GetProductResponse response = new GetProductResponse();
        response.setProduct(productDto);
        return response;
    }

    @Transactional
    public void schedule(Integer id, ScheduledTimeRequest request) {
        final Product product = productDao.findOne(id);
        if (product == null){
            throw new NotFoundException(BusinessErrors.PRODUCT_NOT_FOUND);
        }

        final Time departureTime = toTime(request.getDepartureTime(), BusinessErrors.INVALID_DEPARTURE_TIME_FORMAT);
        final Time arrivalTime = toTime(request.getArrivalTime(), BusinessErrors.INVALID_ARRIVAL_TIME_FORMAT);
        ScheduledTime time = scheduledTimeDao.findByProductIdAndDayOfWeekAndDepartureTimeAndArrivalTime(
                product.getId(),
                request.getDayOfWeek(),
                departureTime,
                arrivalTime
        );
        if (time == null) {
            time = new ScheduledTime();
            time.setProductId(product.getId());
            time.setDayOfWeek(request.getDayOfWeek());
            time.setDepartureTime(departureTime);
            time.setArrivalTime(arrivalTime);

            scheduledTimeDao.save(time);
        }
    }

    @Transactional
    public void unschedule(Integer id, ScheduledTimeRequest request) {
        final Time departureTime = toTime(request.getDepartureTime(), BusinessErrors.INVALID_DEPARTURE_TIME_FORMAT);
        final Time arrivalTime = toTime(request.getArrivalTime(), BusinessErrors.INVALID_ARRIVAL_TIME_FORMAT);
        ScheduledTime time = scheduledTimeDao.findByProductIdAndDayOfWeekAndDepartureTimeAndArrivalTime(
                id,
                request.getDayOfWeek(),
                departureTime,
                arrivalTime
        );
        if (time == null){
            throw new NotFoundException(BusinessErrors.SCHEDULED_TIME_NOT_FOUND);
        } else {
            scheduledTimeDao.delete(time);
        }
    }


    private io.tchepannou.k.travel.client.response.ScheduledTime toScheduleTimeDto(final ScheduledTime time, final DateFormat df) {
        io.tchepannou.k.travel.client.response.ScheduledTime dto = new io.tchepannou.k.travel.client.response.ScheduledTime();
        dto.setDayOfWeek(time.getDayOfWeek());
        dto.setDepartureTime(df.format(time.getDepartureTime()));
        dto.setArrivalTime(df.format(time.getArrivalTime()));
        return dto;
    }

    private Time toTime(final String time, final BusinessErrors error){
        if (time == null){
            return null;
        }

        try{
            final Date date = getTimeFormat().parse(time);
            return new Time(date.getTime());
        } catch (ParseException e){
            throw new InvalidRequestException(error, e);
        }
    }
    private DateFormat getTimeFormat(){
        return new SimpleDateFormat(TIME_PATTERN, Locale.US);
    }
}
