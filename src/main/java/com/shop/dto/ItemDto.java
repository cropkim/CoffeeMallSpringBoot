package com.shop.dto;

import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.asm.Advice;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDto {
    private Long id;
    private String itemNm;
    private int price;
    private String itemDetail;
    private String sellStatCd;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;
}
