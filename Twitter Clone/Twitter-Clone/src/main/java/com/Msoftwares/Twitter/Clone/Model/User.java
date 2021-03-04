package com.Msoftwares.Twitter.Clone.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @NotNull
    private Integer id;

    @JsonIgnore
    @NotNull
    private String password;

    @NotNull
    @Column(unique = true)
    private String screenName;

    @NotNull
    private Role role;

    private String bio;

    private String profileImg;

    @ElementCollection
    private Set<String> following;
}
