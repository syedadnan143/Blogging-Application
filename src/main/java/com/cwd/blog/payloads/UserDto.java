package com.cwd.blog.payloads;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.HashSet;
import java.util.Set;

import com.cwd.blog.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {

    private int id;
    
    @NotEmpty
    @Size(min = 4, message = "Username must be at least 4 characters long.")
    private String name;
    
    @Email(message = "Email address is not valid.")
    private String email;
    
    @NotBlank
    @Size(min = 3, max = 10, message = "Password must be between 3 and 10 characters long.")
    private String password;
    
    @NotBlank
    private String about;
    
    private Set<RoleDto> roles  = new HashSet<>();
}
