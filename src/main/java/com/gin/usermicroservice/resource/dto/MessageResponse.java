package com.gin.usermicroservice.resource.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MessageResponse {
    private String msg;
}
