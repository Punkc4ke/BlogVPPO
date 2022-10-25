package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Поле обязательно к заполнению")
    @Size(min = 4, max = 16, message = "Поле должно быть размером от 6 до 20 символов")
    @Column
    private String login;

    @NotBlank(message = "Поле обязательно к заполнению")
    @Size(min = 8, max = 16, message = "Поле должно быть размером от 6 до 20 символов")
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

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name="role_id")
    private Role role;

    @OneToMany (mappedBy = "user", fetch = FetchType.EAGER)
    private Collection<Avto> cars;

    @ManyToMany
    @JoinTable (name="user_country",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="country_id"))
    private List<Country> countries;


    public User(String login, String password, String email, Integer age, Integer iq, Role role, List<Country> countries) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.age = age;
        this.iq = iq;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
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


}
