package com.forum.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "action")
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_action")
    private int id;

    @Column(name = "action_type")
    private String actionType;

    @ManyToMany(mappedBy = "actions", fetch = FetchType.LAZY)
    private List<Role> roles;

}
