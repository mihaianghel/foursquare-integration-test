package com.mihai.foursquare.service.impl;

import com.mihai.foursquare.convert.DomainToTransportConverter;
import com.mihai.foursquare.convert.TransportToDomainConverter;
import com.mihai.foursquare.dao.FoursquareDAO;
import com.mihai.foursquare.domain.AbstractFoursquareResponse;
import com.mihai.foursquare.domain.Meta;
import com.mihai.foursquare.domain.RequestParams;
import com.mihai.foursquare.domain.Response;
import com.mihai.foursquare.dto.RequestParamsDTO;
import com.mihai.foursquare.dto.ResponseDTO;
import com.mihai.foursquare.service.FoursquareService;
import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

/**
 * Implementation for the foursquare service
 */
@Component
public class FoursquareServiceImpl implements FoursquareService {

    @Autowired
    @Qualifier(value = "infinispanReplicated")
    private FoursquareDAO foursquareDAO;

    protected Logger LOG = LoggerFactory.getLogger(FoursquareServiceImpl.class);

    private static final TransportToDomainConverter TRANSPORT_TO_DOMAIN_CONVERTER = new TransportToDomainConverter();

    private static final DomainToTransportConverter DOMAIN_TO_TRANSPORT_CONVERTER = new DomainToTransportConverter();

    @Override
    public Collection<ResponseDTO> execute(final RequestParamsDTO requestParamsDTO) {

        final RequestParams requestParams = TRANSPORT_TO_DOMAIN_CONVERTER.convert(requestParamsDTO);
        try {
            final AbstractFoursquareResponse foursquareResponse = foursquareDAO.getFoursquareMetadata(requestParams);
            if (foursquareResponse != null) {
                if (isValidResponse(foursquareResponse)) {
                    final Response response = foursquareResponse.getResponse();
                    final Collection<ResponseDTO> exploreResponseDTO = DOMAIN_TO_TRANSPORT_CONVERTER.convert(response);
                    return exploreResponseDTO;
                } else {
                    logErrorsFromFoursquare(foursquareResponse);
                }
            }
        } catch (Exception e) {
            LOG.error("Unexpected error occurred during processing", e);
        }
        return Collections.emptyList();
    }

    private boolean isValidResponse(final AbstractFoursquareResponse foursquareResponse) {
        final Meta meta = foursquareResponse.getMeta();
        if (meta.getCode() == HttpStatus.SC_OK) {
            return true;
        } else {
            return false;
        }
    }

    private void logErrorsFromFoursquare(final AbstractFoursquareResponse foursquareResponse) {
        final Meta meta = foursquareResponse.getMeta();
        LOG.error(
              String.format("Request to Foursquare API had incorrect parameters. StatusCode= %s Error= %s",
                    meta.getCode(), meta.getErrorDetail()));
    }


}
