package com.example.user;

public record UserDto(
        String firstName,
        String lastName,
        Integer age,
        String address
        ) {

    public static UserDto fromEntity(User user) {
        return new UserDto(
                user.getFirstName(),
                user.getLastName(),
                user.getAge(),
                user.getAddress()
        );
    }
}
