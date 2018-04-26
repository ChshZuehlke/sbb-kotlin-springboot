package ch.zuehlke.gauch.spring.controller

import ch.zuehlke.gauch.spring.dto.HeroesResponse
import ch.zuehlke.gauch.spring.entity.Hero
import ch.zuehlke.gauch.spring.exception.HeroNotFoundException
import ch.zuehlke.gauch.spring.repository.HeroesRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@Component
@RequestMapping("/api")
class HeroesController(val heroesRepository: HeroesRepository) {

    @GetMapping("/heroes")
    fun getHeroes() = HeroesResponse(heroesRepository.findAll())

    @GetMapping(value = ["/heroes/{id}"])
    fun getHeroById(@PathVariable("id") id: Int) = heroesRepository.getOne(id)
            ?: HeroNotFoundException("Hero with id: $id not found")

    @DeleteMapping(value = ["heroes/{id}"])
    fun deleteHeroById(@PathVariable("id") id: Int): ResponseEntity<Unit> {
        heroesRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    @PutMapping("/heroes/{id}")
    fun updateHero(@PathVariable("id") id: Int, @RequestBody hero: Hero): Hero = heroesRepository.save(hero)

    @PostMapping("/heroes")
    fun createHero(@RequestBody hero: Hero, uirComponentsBuilder: UriComponentsBuilder): ResponseEntity<Hero> {
        val savedHero = heroesRepository.save(hero)
        val location = uirComponentsBuilder.path("heroes/{id}").buildAndExpand(savedHero.id).toUri()
        return ResponseEntity.created(location).body(savedHero)
    }
}