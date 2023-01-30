package com.assignment.demo.entity.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponse implements Serializable {
    private Long userId;
    private String token;
    private Date issuedAt;
    private Date expiresAt;
}
