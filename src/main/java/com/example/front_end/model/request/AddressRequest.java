package com.example.front_end.model.request;

import com.example.front_end.model.response.UserResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressRequest {

    private Long id;

    private String fullName;

    private String phoneNumber;

    private String city;

    private String district;

    private String ward;

    private String orderDetail;

    private Boolean isDefault;

    private UserResponse userResponse;

}
