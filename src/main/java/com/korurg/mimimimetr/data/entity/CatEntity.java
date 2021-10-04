package com.korurg.mimimimetr.data.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cat")
public class CatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cat_id_gen")
    @SequenceGenerator(name = "cat_id_gen", sequenceName = "cat_id_seq", initialValue = 100)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "score")
    private int score;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;
}
