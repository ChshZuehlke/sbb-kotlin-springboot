package ch.zuehlke.gauch.spring.repository

import ch.zuehlke.gauch.spring.entity.Hero
import org.springframework.data.jpa.repository.JpaRepository

interface HeroesRepository : JpaRepository<Hero, Int>