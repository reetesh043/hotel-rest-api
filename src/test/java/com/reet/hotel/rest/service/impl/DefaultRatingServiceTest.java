package com.reet.hotel.rest.service.impl;

import com.reet.hotel.rest.dto.RatingReportDto;
import com.reet.hotel.rest.fixtures.HotelDataFixtures;
import com.reet.hotel.rest.model.Hotel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DefaultRatingServiceTest {

    @Autowired
    DefaultRatingService ratingService;

    @Autowired
    HotelDataFixtures hotelData;

    @Test
    @DisplayName("When hotels have ratings then the average is correctly calculated")
    void calculatedCorrectly() {
        List<Hotel> hotels = hotelData.getHotelsWithRatingValues(8.0, 8.0, 9.0, 9.0);
        RatingReportDto ratingReport = ratingService.getRatingAverage(hotels);

        assertThat(ratingReport.getNumberOfRatings(), equalTo(4));
        assertThat(ratingReport.getAverageRating(), closeTo(8.5, 0.01));
    }

    @Test
    @DisplayName("When some hotels have null ratings then null ratings are excluded")
    void ignoresNullRatings() {
        List<Hotel> hotels = hotelData.getHotelsWithRatingValues(7.0, null, 9.0, null);
        RatingReportDto ratingReport = ratingService.getRatingAverage(hotels);

        assertThat(ratingReport.getNumberOfRatings(), equalTo(2));
        assertThat(ratingReport.getAverageRating(), closeTo(8.0, 0.01));
    }

    @Test
    @DisplayName("When all hotel ratings are null then zero ratings and zero average are returned")
    void returnsZeroForAllNullRatings() {
        List<Hotel> hotels = hotelData.getHotelsWithRatingValues(null, null);
        RatingReportDto ratingReport = ratingService.getRatingAverage(hotels);

        assertThat(ratingReport.getNumberOfRatings(), equalTo(0));
        assertThat(ratingReport.getAverageRating(), closeTo(0.0, 0.01));
    }

}
