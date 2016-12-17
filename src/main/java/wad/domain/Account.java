package wad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Account extends AbstractPersistable<Long> {

    @NotNull
    @NotBlank
    @Length(min = 4, max = 20)
    @Column(unique = true)
    @JsonProperty
    private String username;

    @NotNull
    @NotBlank
    @Email
    @Column(unique = true)
    @JsonProperty
    private String email;

    @NotNull
    @NotBlank
    @JsonProperty
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> authorities;

    public Account() {
        authorities = new ArrayList<>();
        authorities.add("USER");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthority(String role) {
        authorities.add(role);
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.username != null ? this.username.hashCode() : 0);
        hash = 53 * hash + (this.email != null ? this.email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Account other = (Account) obj;
        if ((this.username == null) ? (other.username != null) : !this.username.equals(other.username)) {
            return false;
        }
        if ((this.email == null) ? (other.email != null) : !this.email.equals(other.email)) {
            return false;
        }
        return true;
    }
}
