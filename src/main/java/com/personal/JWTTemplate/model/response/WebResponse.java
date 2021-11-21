package com.personal.JWTTemplate.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebResponse {
    public int code;
    public String status;
    public Object data;
}
