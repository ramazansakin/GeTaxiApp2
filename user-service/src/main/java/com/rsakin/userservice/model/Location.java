package com.rsakin.userservice.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Location {

    private int userId;
    private long latitude;
    private long longitude;

}
