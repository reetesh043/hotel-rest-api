package com.reet.hotel.rest.service.impl;

import com.reet.hotel.rest.dto.RatingReportDto;
import com.reet.hotel.rest.model.Hotel;
import com.reet.hotel.rest.service.HotelService;
import com.reet.hotel.rest.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class DefaultRatingService implements RatingService {
    private final HotelService hotelService;

    @Autowired
    DefaultRatingService(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Override
    public RatingReportDto getRatingAverage(Long cityId) {
        return getRatingAverage(hotelService.getHotelsByCity(cityId));
    }

    @Override
    public RatingReportDto getRatingAverage(List<Hotel> hotels) {
        double ratingSum = 0;
        int ratingCount = 0;

        for (Hotel hotel : hotels) {
            Double rating = hotel.getRating();
            if (rating != null) {
                ratingSum += rating;
                ratingCount++;
            }
        }

        if (ratingCount == 0) {
            return new RatingReportDto(0, 0.0);
        }

        return new RatingReportDto(ratingCount, ratingSum / ratingCount);
    }
}
