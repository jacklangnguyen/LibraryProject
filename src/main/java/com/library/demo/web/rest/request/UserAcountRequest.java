package com.library.demo.web.rest.request;

import com.library.demo.entity.UserAcount;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAcountRequest {
    private String id;
    private String userName;
    private String userId;
    private String userAddress;
    private String totalBookCheckout;
    private String blockMember;

    public UserAcount toUserAcount() {
        return UserAcount.builder()
                .id(this.id)
                .userId(this.userId)
                .userName(this.userName)
                .userAddress(this.userAddress)
                .totalBookCheckout(this.totalBookCheckout)
                .blockMember(this.blockMember).build();
    }
}
