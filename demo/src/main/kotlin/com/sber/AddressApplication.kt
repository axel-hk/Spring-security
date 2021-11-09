package com.sber

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan

@ServletComponentScan
@SpringBootApplication
class AddressApplication
fun main(args: Array<String>) {
	runApplication<AddressApplication>(*args)
}
