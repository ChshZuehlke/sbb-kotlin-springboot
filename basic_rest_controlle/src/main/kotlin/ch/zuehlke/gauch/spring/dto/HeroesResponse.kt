package ch.zuehlke.gauch.spring.dto

import ch.zuehlke.gauch.spring.entity.Hero

data class HeroesResponse(val heroes: List<Hero>)