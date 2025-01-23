package tz.co.kishada.votingApp.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class RequestGenerator {
	
	 private static final Logger logger = LoggerFactory.getLogger(RequestGenerator.class);

     private static final String requestURL ="http://41.59.1.84:52100/tausi-integration-service/api/v1/nin-otp";
     private static final String requestTinURL ="http://41.59.1.84:52100/tausi-integration-service/api/v1/nin-otp";

    public String ninOtpRequest(final String data) throws IOException {
        try  {
            final CloseableHttpClient httpClient = HttpClients.createDefault();
            final HttpPost postRequest = new HttpPost(requestURL);
            postRequest.addHeader("Content-Type", "application/json; charset=UTF-8");
            logger.info("Data we send to NIDA as body request: {}",data);
            final StringEntity userEntity = new StringEntity(data);
            postRequest.setEntity((HttpEntity)userEntity);
            final CloseableHttpResponse response = httpClient.execute((HttpUriRequest)postRequest);
            final String responseString = EntityUtils.toString(response.getEntity());
            response.close();
            logger.info("Response from NIDA is : {}",responseString);
            return responseString;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
    public String tinRequest(final String data) throws IOException {
        try  {
        	final CloseableHttpClient httpClient = HttpClients.createDefault();
            final HttpPost postRequest = new HttpPost(requestTinURL);
            postRequest.addHeader("Content-Type", "application/json; charset=UTF-8");
            logger.info("Data we send to TIN as body request: {}",data);
            final StringEntity userEntity = new StringEntity(data);
            postRequest.setEntity((HttpEntity)userEntity);
            final CloseableHttpResponse response = httpClient.execute((HttpUriRequest)postRequest);
            final String responseString = EntityUtils.toString(response.getEntity());
            response.close();
            logger.info("Response from NIDA is : {}",responseString);
            return responseString;
        }catch(Exception e) {
        	e.printStackTrace();
        }
        return null;
    }

    public String httpXMLRequest(final String data, final String requestURL) throws IOException {
        try {
            final CloseableHttpClient httpClient = HttpClients.createDefault();
            final HttpPost postRequest = new HttpPost(requestURL);
            postRequest.addHeader("Content-Type", "text/xml");
            postRequest.addHeader("TRACERT", "CERT 007");
            logger.info("Data we send to TRA as body request: {}",data);
            final StringEntity userEntity = new StringEntity(data);
            postRequest.setEntity((HttpEntity)userEntity);
            final CloseableHttpResponse response = httpClient.execute((HttpUriRequest)postRequest);
            final String responseString = EntityUtils.toString(response.getEntity());
            response.close();
            logger.info("Response from TRA is : {}",responseString);
            return responseString;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String httpGetRequest(final String requestURL, final String token) throws IOException {
        try  {
        	final CloseableHttpClient httpClient = HttpClients.createDefault();
            final HttpGet getRequest = new HttpGet(requestURL);
            //getRequest.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");  
            getRequest.addHeader("Authorization","Bearer "+token);
            final CloseableHttpResponse response = httpClient.execute((HttpUriRequest)getRequest);
            final String responseString = EntityUtils.toString(response.getEntity());
            response.close();
            return responseString;
        }catch(Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
    
    
    public String httpTRAGetRequest(final String requestURL) throws IOException {
        try  {
        	final CloseableHttpClient httpClient = HttpClients.createDefault();
            final HttpGet getRequest = new HttpGet(requestURL);
            final CloseableHttpResponse response = httpClient.execute((HttpUriRequest)getRequest);
            final String responseString = EntityUtils.toString(response.getEntity());
            response.close();
            return responseString;
        }catch(Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
    

}
