package com.mygroup.sbb.user;

public record SiteUserDto(
        Long id,
        String username,
        String password,
        String email
) {
    public static SiteUserDto from(SiteUser entity) {
        return new SiteUserDto(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getEmail()
        );
    }
}
