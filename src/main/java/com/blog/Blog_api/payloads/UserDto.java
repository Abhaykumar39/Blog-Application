package com.blog.Blog_api.payloads;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private Integer id;

    @NotEmpty
    @Size(min = 3,message = "It should me minimum of 3 character")
    private String name;

    @Email(message = "Email address is not Valid")
    private String email;

    @NotNull
    @Size(min = 3,max = 10,message = "Password of 3 to 10 character")
    //@Pattern(regexp = )
    private String password;

    @NotNull
    @NotEmpty
    private String about;

    private Set<RoleDto> roles = new HashSet<>();
}
