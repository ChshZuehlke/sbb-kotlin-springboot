package ch.zuehlke.gauch.kotlin.config

import org.springframework.context.annotation.Configuration


@Configuration
@EnableWebSecurity
class SecurityConfig: WebSecurityConfigurerAdapter()