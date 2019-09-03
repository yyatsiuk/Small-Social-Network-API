package com.yyatsiuk.social.sonet.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class OperationStatusResponse {

    private String name;
    private String result;
    private String message;

    public OperationStatusResponse(String name, String result, String message) {
        this.name = name;
        this.result = result;
        this.message = message;
    }

    public OperationStatusResponse(String name, String result) {
        this.name = name;
        this.result = result;
    }
}
