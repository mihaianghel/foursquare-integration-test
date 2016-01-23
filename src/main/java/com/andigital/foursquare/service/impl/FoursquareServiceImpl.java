package com.andigital.foursquare.service.impl;

import com.andigital.foursquare.dao.FoursquareDAO;
import com.andigital.foursquare.domain.RequestParams;
import com.andigital.foursquare.dto.ExploreResponseDTO;
import com.andigital.foursquare.dto.RequestParamsDTO;
import com.andigital.foursquare.dto.ResponseDTO;
import com.andigital.foursquare.domain.*;
import com.andigital.foursquare.serialization.JSONDeserializer;
import com.andigital.foursquare.service.FoursquareService;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Implementation for the foursquare service
 */
@Component
public class FoursquareServiceImpl implements FoursquareService {

    @Autowired
    private FoursquareDAO foursquareDAO;

    protected Logger LOG = LoggerFactory.getLogger(FoursquareServiceImpl.class);

    @Override
    public Collection<ResponseDTO> execute(final RequestParamsDTO requestParamsDTO) {

        final RequestParams requestParams = convertTransferObjectToDomainObject(requestParamsDTO);
        try {
            final String fourSquareResponseAsString = foursquareDAO.getFoursquareMetadata(requestParams);
            if (StringUtils.isNotBlank(fourSquareResponseAsString)) {

                AbstractFoursquareResponse foursquareResponse = JSONDeserializer.fromString(fourSquareResponseAsString, Explore.class);

                if (isValidResponse(foursquareResponse)) {
                    final Response response = foursquareResponse.getResponse();
                    final Collection<ResponseDTO> exploreResponseDTO = convertDomainObjectsToTransferObjects(response);
                    return exploreResponseDTO;
                }

            } else {
                LOG.error("Request to Foursquare API was unsuccessful");
            }
            return Collections.emptyList();
        } catch (Exception e) {
            LOG.error("Unexpected error occurred during processing", e);
            return Collections.emptyList();
        }
    }

    //TODO: use spring converter annotation
    private RequestParams convertTransferObjectToDomainObject(final RequestParamsDTO requestParamsDTO) {
        return new RequestParams(requestParamsDTO.getLocation(),
              requestParamsDTO.getRadius(),
              requestParamsDTO.getLimit(),
              requestParamsDTO.getOperation());
    }

    private List<ResponseDTO> convertDomainObjectsToTransferObjects(final Response response) {
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

    private boolean isValidResponse(final AbstractFoursquareResponse foursquareResponse) {
        if (foursquareResponse != null) {
            final Meta meta = foursquareResponse.getMeta();
            if (meta.getCode() == HttpStatus.SC_OK) {
                return true;
            } else {
                LOG.error(
                      String.format("Request to Foursquare API had incorrect parameters. StatusCode= %s Error= %s",
                            meta.getCode(), meta.getErrorDetail()));
            }
        }
        return false;

    }

}
