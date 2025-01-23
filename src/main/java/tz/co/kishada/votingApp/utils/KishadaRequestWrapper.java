package tz.co.kishada.votingApp.utils;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KishadaRequestWrapper<T> {

    @Valid
    private T data;

}
