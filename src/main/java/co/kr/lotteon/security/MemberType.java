package co.kr.lotteon.security;

import lombok.Getter;
import lombok.Setter;

public enum MemberType {
    USER("ROLE_USER"),
    SELLER("ROLE_SELLER"),
    ADMIN("ROLE_ADMIN");

    private final String role;

    MemberType(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
