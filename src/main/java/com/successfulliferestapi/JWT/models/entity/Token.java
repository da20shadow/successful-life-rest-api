package com.successfulliferestapi.JWT.models.entity;

import com.successfulliferestapi.JWT.models.enums.JwtTokenType;
import com.successfulliferestapi.user.models.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    @Column(unique = true)
    @Basic
    public String token;

    @Enumerated(EnumType.STRING)
    public JwtTokenType tokenType = JwtTokenType.BEARER;

    public boolean revoked;

    public boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;
}
