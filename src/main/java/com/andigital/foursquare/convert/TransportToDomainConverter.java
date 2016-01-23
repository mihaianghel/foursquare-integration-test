package com.andigital.foursquare.convert;


import com.andigital.foursquare.domain.RequestParams;
import com.andigital.foursquare.dto.RequestParamsDTO;
import org.springframework.core.convert.converter.Converter;

public class TransportToDomainConverter implements Converter<RequestParamsDTO, RequestParams> {

    @Override
    public RequestParams convert(final RequestParamsDTO requestParamsDTO) {
        return new RequestParams(requestParamsDTO.getLocation(),
              requestParamsDTO.getRadius(),
              requestParamsDTO.getLimit(),
              requestParamsDTO.getOperation());
    }
}
