package com.andigital.foursquare.client.impl;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.andigital.foursquare.client.AbstractFoursquareClient;
import com.andigital.foursquare.domain.RequestParams;
import com.andigital.foursquare.util.Operation;

import static com.andigital.foursquare.util.Constants.*;

/**
 * Implementation of AbstractFoursquareClient
 */
@Component
public class FoursquareClientImpl extends AbstractFoursquareClient {
	
	protected Logger LOG = LoggerFactory.getLogger(AbstractFoursquareClient.class);
	
	private HttpClient httpClient;
	
	@PostConstruct
	public void init() {
		final HttpConnectionManagerParams params = new HttpConnectionManagerParams();
		params.setConnectionTimeout(5000);
		params.setMaxTotalConnections(50);
		final MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		connectionManager.setParams(params);
		httpClient = new HttpClient(connectionManager);
		LOG.info("HTTP client initialised");
	}

	@PreDestroy
	public void destroy() throws IOException {
		((Closeable) httpClient).close();
		LOG.info("HTTP client destroyed");
		
	}

	@Override
	public String execute(final RequestParams requestParams, final Operation operation) {

		try {
			final String url = buldRequestURL(requestParams, operation);
		    final GetMethod get = new GetMethod(url);
		    LOG.info("Executing request " + url);

		    try {
				httpClient.executeMethod(get);
				final Reader reader = new InputStreamReader(get.getResponseBodyAsStream(), get.getResponseCharSet());
				return IOUtils.toString(reader);
			} finally {
				get.releaseConnection();
			}
		} catch (URISyntaxException e) {
			LOG.error("Syntax error when building the request url", e);
		} catch (Exception e) {
			LOG.error("Request to Foursquare API was unsuccessful", e);
		}
		return null;
	}

	private String buldRequestURL(final RequestParams requestParams, final Operation operation) throws URISyntaxException {
		
		final List<NameValuePair> requestQueryParams = new LinkedList<>();
		addAuthenticationParams(requestQueryParams);
		addUserInputParams(requestQueryParams, requestParams);
		addVersionParam(requestQueryParams);

		final URIBuilder builder = new URIBuilder().setHost(endpoint.concat(operation.getPath()))
				.setParameters(requestQueryParams);
		return builder.toString().replaceFirst("//", StringUtils.EMPTY);
	}
	
	private void addAuthenticationParams(List<NameValuePair> queryParams) {
		queryParams.add(new BasicNameValuePair(CLIENT_ID, clientId));
		queryParams.add(new BasicNameValuePair(CLIENT_SECRET, clientSecret));
	}

	private void addUserInputParams(final List<NameValuePair> queryParams, final RequestParams modelObject) {
		queryParams.add(new BasicNameValuePair(NEAR, modelObject.getLocation()));
		queryParams.add(new BasicNameValuePair(RADIUS, modelObject.getRadius().toString()));
		queryParams.add(new BasicNameValuePair(LIMIT, modelObject.getLimit().toString()));
	}
	
	private void addVersionParam(List<NameValuePair> queryParams) {
		queryParams.add(new BasicNameValuePair("v", apiVersion));
	}
}
