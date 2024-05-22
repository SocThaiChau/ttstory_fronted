package com.example.front_end.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticaResponse implements Serializable {
    private String accessToken;
    private String token;
    private String name;
    private String phone;
    private String email;
    private String gender;
    private Date dob;

    private UserResponse createdBy;
    private Date createdDate;
}
