package com.example.front_end.model.UI;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestUI {
    private Long id;
    private String name;
    private String email;
//    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String dob;
    private String address;
    private String phoneNumber;
    private String gender;
    private String avatarUrl;

}
