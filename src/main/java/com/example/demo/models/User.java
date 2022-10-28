package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Поле обязательно к заполнению")
    @Size(min = 4, max = 20, message = "Поле должно быть размером от 6 до 20 символов")
    @Column
    private String username;

    @NotBlank(message = "Поле обязательно к заполнению")
    //@Size(min = 8, max = 16, message = "Поле должно быть размером от 6 до 20 символов")
    private String password;

    @NotBlank(message = "Поле обязательно к заполнению")
    @Size(min = 8, max = 16, message = "Поле должно быть размером от 6 до 20 символов")
    private String email;

    @DecimalMin(value = "0.0", message = "Возраст должен быть больше 0")
    @DecimalMax(value = "100.0", message = "Возраст должен быть меньше 100")
    @Positive(message = "Возраст не может быть отрицательным")
    @NotNull(message = "Поле не должно быть пустым")
    private Integer age;

    @DecimalMin(value = "0.0", message = "IQ должен быть больше 0")
    @DecimalMax(value = "500.0", message = "IQ должен быть меньше 500")
    @Positive(message = "IQ не может быть отрицательным")
    @NotNull(message = "Поле не должно быть пустым")
    private Integer iq;

    private boolean active;

    private boolean admin;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",joinColumns = @JoinColumn(name="user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany (mappedBy = "user", fetch = FetchType.EAGER)
    private Collection<Avto> cars;

    @ManyToMany
    @JoinTable (name="user_country",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="country_id"))
    private List<Country> countries;


    public User(String username, String password, String email, Integer age, Integer iq, Role roles ,List<Country> countries) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
        this.iq = iq;
        this.roles = Collections.singleton(roles);
        this.countries = countries;

    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getIq() {
        return iq;
    }

    public void setIq(Integer iq) {
        this.iq = iq;
    }

    public Collection<Avto> getCars() {
        return cars;
    }

    public void setCars(Collection<Avto> cars) {
        this.cars = cars;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
