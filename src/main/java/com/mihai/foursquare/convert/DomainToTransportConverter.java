package com.mihai.foursquare.convert;

import com.mihai.foursquare.domain.Response;
import com.mihai.foursquare.domain.Venue;
import com.mihai.foursquare.dto.ExploreResponseDTO;
import com.mihai.foursquare.dto.ResponseDTO;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DomainToTransportConverter implements Converter<Response, Collection<ResponseDTO>> {

    @Override
    public Collection<ResponseDTO> convert(final Response response) {
        final List<ResponseDTO> responseDTOs = new ArrayList<>();
        response.getGroups().stream()
              .forEach(group -> group.getItems().stream()
                    .forEach(item -> {
                        final Venue venue = item.getVenue();
                        responseDTOs.add(
                              new ExploreResponseDTO(venue.getName(),
                                    venue.getFormattedPhone(),
                                    venue.getAddress(),
                                    venue.getCheckinsCount()));
                    }));
        return responseDTOs;
    }
}
