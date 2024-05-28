package ru.leed.leedgen.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(nullable = false, name = "mail")
  private String mail;

  @Column(nullable = false, name = "sex")
  private String sex;

  @Column(nullable = false, name = "first_name")
  private String firstName;
}
