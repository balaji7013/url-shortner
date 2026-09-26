package com.mini.url.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="urlmapping")
public class UrlMapping {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(name="id")
private Long id;

@Column(name="originalUrl",nullable=false ,length=2048)
private String originalUrl;

@Column(name="shortcode",unique=true,length=10)
private String shortcode;

@ManyToOne (fetch = FetchType.LAZY)
@JoinColumn (name="user_id",nullable = false)
private User user;

@Column(name="count")
@Builder.Default
private Long count=0L;

}
