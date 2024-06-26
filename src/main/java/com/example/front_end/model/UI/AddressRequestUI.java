package com.example.front_end.model.UI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestUI {

    private Long id;

    private String fullName;

    private String phoneNumber;

    private String city;

    private String district;

    private String ward;

    private String orderDetail;

    private Boolean isDefault;

}
