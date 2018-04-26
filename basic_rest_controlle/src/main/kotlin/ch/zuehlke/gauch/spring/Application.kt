package ch.zuehlke.gauch.spring

import ch.zuehlke.gauch.spring.entity.Hero
import ch.zuehlke.gauch.spring.repository.HeroesRepository
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = [(HeroesRepository::class)])
@EntityScan(basePackageClasses = [(Hero::class)])
class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
