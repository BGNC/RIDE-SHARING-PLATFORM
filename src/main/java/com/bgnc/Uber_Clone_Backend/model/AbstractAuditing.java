package com.bgnc.Uber_Clone_Backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.*;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class AbstractAuditing {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "create_time", nullable = false, updatable = false)
    @DateTimeFormat(iso = DATE_TIME)
    private LocalDateTime createTime;

    @Column(name = "update_time")
    @DateTimeFormat(iso = DATE_TIME)
    private LocalDateTime updateTime;


    @PrePersist
    protected void prePersist() {
        this.createTime = LocalDateTime.now();  // Entity kaydedilmeden önce oluşturma zamanı belirlenir
    }

    @PreUpdate
    protected void preUpdate() {
        this.updateTime = LocalDateTime.now();  // Entity güncellenmeden önce güncellenme zamanı belirlenir
    }

}
