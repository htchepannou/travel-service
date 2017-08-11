package io.tchepannou.k.travel.service;

import io.tchepannou.k.travel.client.request.CreateScheduleRequest;
import io.tchepannou.k.travel.client.request.UpdateScheduledRequest;
import io.tchepannou.k.travel.client.response.CreateScheduleResponse;
import io.tchepannou.k.travel.client.response.UpdateScheduleResponse;
import io.tchepannou.k.travel.dao.AssetDao;
import io.tchepannou.k.travel.dao.ProductDao;
import io.tchepannou.k.travel.dao.ScheduleDao;
import io.tchepannou.k.travel.domain.Asset;
import io.tchepannou.k.travel.domain.Product;
import io.tchepannou.k.travel.domain.Schedule;
import io.tchepannou.k.travel.exception.BusinessErrors;
import io.tchepannou.k.travel.exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;

@Component
public class ScheduleService {
    @Autowired
    private ScheduleDao scheduleDao;
    
    @Autowired
    private ProductDao productDao;
    
    @Autowired
    private AssetDao assetDao;
    
    @Transactional
    public CreateScheduleResponse create(CreateScheduleRequest request){
        final Product product = productDao.findOne(request.getProductId());
        if (product == null){
            throw new InvalidRequestException(BusinessErrors.PRODUCT_NOT_FOUND);    
        }
        
        final Asset asset = assetDao.findOne(request.getAssetId());
        if (asset == null){
            throw new InvalidRequestException(BusinessErrors.ASSET_NOT_FOUND);
        }
        
        final Schedule schedule = new Schedule();
        schedule.setArrivalDateTime(toTimestamp(request.getArrivalDateTime()));
        schedule.setAssetId(request.getAssetId());
        schedule.setCapacity(request.getCapacity());
        schedule.setDepartureDateTime(toTimestamp(request.getDepartureDateTime()));
        schedule.setProductId(request.getProductId());
        scheduleDao.save(schedule);
        
        final CreateScheduleResponse response = new CreateScheduleResponse();
        response.setId(schedule.getId());
        return null;
    }

    @Transactional
    public UpdateScheduleResponse update(Integer id, UpdateScheduledRequest request){
        final Schedule schedule = scheduleDao.findOne(id);
        if (schedule == null){
            throw new InvalidRequestException(BusinessErrors.SCHEDULE_NOT_FOUND);
        }

        schedule.setArrivalDateTime(toTimestamp(request.getArrivalDateTime()));
        schedule.setCapacity(request.getCapacity());
        schedule.setDepartureDateTime(toTimestamp(request.getDepartureDateTime()));
        scheduleDao.save(schedule);

        final CreateScheduleResponse response = new CreateScheduleResponse();
        response.setId(schedule.getId());
        return null;
    }
    

    private Timestamp toTimestamp(Date date){
        return date == null ? null : new Timestamp(date.getTime());
    }
}
