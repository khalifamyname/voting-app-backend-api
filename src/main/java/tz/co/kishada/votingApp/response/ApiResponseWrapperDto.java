package tz.co.kishada.votingApp.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApiResponseWrapperDto implements Serializable {
    private Object data;
    public Boolean status;
    public Integer statusCode;
    public String description;
}
