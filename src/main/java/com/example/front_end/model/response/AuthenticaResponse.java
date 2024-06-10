package com.example.front_end.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticaResponse implements Serializable {
    private String accessToken;
    private String token;
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String gender;
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dob;

    private UserResponse userResponse;
    private Date createdDate;
    private RoleResponse role;
}
