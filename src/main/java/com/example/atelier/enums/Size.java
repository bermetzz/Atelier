package com.example.atelier.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum Size {
    XS(0.5), S(1), M(1), L(1), XL(1.5), XXL(2);
    double length;

    Size(double length) {
        this.length = length;
    }
}
