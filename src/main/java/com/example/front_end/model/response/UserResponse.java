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
public class UserResponse implements Serializable {
        private Long id;

        private String username;

        private String email;

        private String name;

        private String password;

        private String phoneNumber;

        private String gender;

        private String address;

        private String avatarUrl;

        private Date dob;

        private String otp;

        private String lastModyfiedBy;

        private Date otpCreateTime;

        private Date createDate;

        private Date lastModifiedDate;

        private Boolean checkPassword;

}
