package tz.co.kishada.votingApp.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import tz.co.kishada.votingApp.response.ApiResponseWrapperDto;
import tz.co.kishada.votingApp.response.KishadaDefaultNullRespData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Component
public class GlobalMethod {
    Locale currentLocale = LocaleContextHolder.getLocale();
    private static final Logger logger = LoggerFactory.getLogger("IKMIS-GENERAL-LOG");

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ObjectMapper mapper;

    public ResponseEntity<?> response(Integer code,String statusMsg, Object data) {

       ApiResponseWrapperDto r = new ApiResponseWrapperDto();

        if (data == null) {
            data = new KishadaDefaultNullRespData();
        }
        r.setStatus(KishadaResponseCode.FAILURE.equals(code) ? Boolean.FALSE : Boolean.TRUE);
        r.setStatusCode(code);
        r.setDescription(statusMsg);

        r.setData(data);

        logger.info("Response : " + r);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    public ResponseEntity<Object> responseError(Integer code,Boolean status,String statusMsg, List<String> errors) {

        ApiResponseWrapperDto r = new ApiResponseWrapperDto();
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("errors", errors);
        r.setData(body);
        r.setStatus(status);
        r.setStatusCode(code);
        r.setDescription(statusMsg);

        logger.info("Response : " + r);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    public ResponseEntity<?> customResponse(Integer code, String description, Object data) {

        ApiResponseWrapperDto r = new ApiResponseWrapperDto();

        if (data == null) {
            data = new KishadaDefaultNullRespData();
        }
        r.setStatus(KishadaResponseCode.FAILURE.equals(code) ? Boolean.FALSE : Boolean.TRUE);
        r.setStatusCode(code);
        r.setDescription(description);
        r.setData(data);

        return new ResponseEntity<>(r, HttpStatus.OK);
    }


    public String currentFinancialYear() {
        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        int months[] = {1, 2, 3, 4, 5, 6}; // first six month of normal year
        String financialYear;
        if (Arrays.stream(months).anyMatch(value -> value == currentMonth)) {
            int lastYear = today.minusYears(1).getYear();
            financialYear = lastYear + "/" + today.format(DateTimeFormatter.ofPattern("yy"));
        } else {
            LocalDate nextYear = today.plusYears(1);
            financialYear = today.getYear() + "/" + nextYear.format(DateTimeFormatter.ofPattern("yy"));
        }
        return financialYear;
    }


    /**
     * sort by parameters
     * @param sort
     * @return
     */
    public List<Sort.Order> sortByParameter(String[] sort){
        List<Sort.Order> orders = new ArrayList<Sort.Order>();

        if (sort[0].contains(",")) {
            // will sort more than 2 fields
            // sortOrder="field, direction"
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            // sort=[field, direction]
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        return orders;
    }
    /**
     * Sorting Direction
     * @param direction
     * @return
     */
    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

}
