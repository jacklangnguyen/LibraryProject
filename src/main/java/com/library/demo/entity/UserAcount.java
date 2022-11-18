package com.library.demo.entity;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserAcount {

    private String id;
    private String userName;
    private String userId;
    private String userAddress;
    private String totalBookCheckout;
    private String blockMember;

}
