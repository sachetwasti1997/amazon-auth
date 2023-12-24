package com.sachet.authserveramazonjava.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserLogin {

    private String userEmail;
    private String password;

}
