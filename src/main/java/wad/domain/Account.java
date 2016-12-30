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
import org.springframework.security.crypto.bcrypt.BCrypt;

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

    private int points;
    private int level;
    private String salt;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> authorities;

    public Account() {
        this.authorities = new ArrayList<>();
        this.authorities.add("USER");
        this.points = 0;
        this.level = 0;
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

    public int getPoints() {
        return points;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Adds a point to the user's points.
     */
    public void addPoint() {
        setPoints(getPoints() + 1);
    }

    /**
     * Deletes a point from the user's points.
     */
    public void deductPoint() {
        setPoints(getPoints() - 1);
    }

    /**
     * Counts the level by dividing user's points with 200. The level can only
     * grow.
     *
     * @return level as an integer.
     */
    public int countLevelbyPoints() {
        int temp = points / 200;
        if (level < temp) {
            level = temp;
        }
        return level;
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
