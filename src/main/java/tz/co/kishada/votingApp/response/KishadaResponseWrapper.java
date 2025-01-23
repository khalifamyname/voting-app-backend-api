package tz.co.kishada.votingApp.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KishadaResponseWrapper<T> extends KishadaDefaultRespData {
    private T item;
}
