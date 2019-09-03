package com.yyatsiuk.social.sonet.model;

import com.yyatsiuk.social.sonet.model.content.Post;
import com.yyatsiuk.social.sonet.model.follow.user.UserFollow;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.style.ToStringCreator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "users")
@PrimaryKeyJoinColumn(name = "actor_id")
public class User extends Actor {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "planet")
    private String planet;

    @Column(name = "email_verification")
    private String emailVerificationToken;

    @OneToMany(mappedBy = "following")
    private List<UserFollow> followers;

    @Column(name="is_online")
    private Boolean isOnline;

    @Column(name = "last_activity")
    private LocalDateTime lastActivity;

    @OneToMany(mappedBy = "follower")
    private List<UserFollow> following;

    @ManyToMany
    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<Group> groups;

    @OneToMany(mappedBy = "creator")
    private List<Group> createdGroups;

    @ManyToMany
    @JoinTable(
            name = "user_chats",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id")
    )
    private List<Chat> chats;

    @OneToMany(mappedBy = "creator")
    protected List<Post> createdPosts;

    @Override
    protected ToStringCreator getToStringCreator() {
        return super.getToStringCreator()
                .append("email", email)
                .append("city", city)
                .append("country", country)
                .append("planet", planet)
                .append("firstName", firstName)
                .append("lastName", lastName);
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }
}
