package de.bananabonanza.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class LoginResponse {
    private Boolean isLoggedIn;
    private String message;
}
