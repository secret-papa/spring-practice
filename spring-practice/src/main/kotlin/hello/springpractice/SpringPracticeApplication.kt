package hello.springpractice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringPracticeApplication

fun main(args: Array<String>) {
	runApplication<SpringPracticeApplication>(*args)
}
