package com.mihai.foursquare.convert;


import com.mihai.foursquare.domain.RequestParams;
import com.mihai.foursquare.dto.RequestParamsDTO;
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
