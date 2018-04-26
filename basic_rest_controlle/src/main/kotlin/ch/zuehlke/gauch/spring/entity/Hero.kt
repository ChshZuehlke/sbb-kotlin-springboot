package ch.zuehlke.gauch.spring.entity

import javax.persistence.*

@Entity
@Table(name = "heroes")
data class Hero(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,
        var name: String = ""
)