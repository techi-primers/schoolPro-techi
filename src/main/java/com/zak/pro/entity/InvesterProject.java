/**
 * Created by: nuwan_r
 * Created on: 3/9/2021
 **/
package com.zak.pro.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "invester_project")
@Data
public class InvesterProject {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    @Column(name = "invester_id")
    private Long investerId;
    @NotNull
    @Column(name = "project_id")
    private Long projectId;
    @Column(name = "invester_email")
    private String investerEmail;
    @CreationTimestamp
    private Date createdDate;


}
