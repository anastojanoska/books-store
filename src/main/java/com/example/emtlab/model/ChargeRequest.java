package com.example.emtlab.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChargeRequest {

        private String description;
        private int amount;
        private String currency;
        private String stripeEmail;
        private String stripeToken;
}
