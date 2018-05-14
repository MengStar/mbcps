package meng.xing.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user",
        indexes = {@Index( columnList = "username",unique = true)}) //生成的表名
@ToString(exclude = "password") //lombok标签, toString()忽略password
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;//jpa 主键和自增
    @NotEmpty
    private String username;
    @NotEmpty
    @JsonIgnore
    private String password; //转json 忽略password
    @NotEmpty
    private String nickname;
    @NonNull
    private boolean main;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastPasswordResetDate;


    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @ManyToOne(fetch = FetchType.EAGER)
    private User mainUser;

    public User(String username, String password, String nickname, Boolean main) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.main = main;
        createDate = new Date();
        lastPasswordResetDate = new Date();
    }
}
