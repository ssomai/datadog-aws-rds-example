package com.example.m2webserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class M2WebserverApplication

fun main(args: Array<String>) {
    runApplication<M2WebserverApplication>(*args)
    //1. 드라이버 로딩 : mysql 드라이버 로딩
//            Class.forName("software.aws.rds.jdbc.mysql.Driver") // aws는 datadog과 연동이 안됨
    Class.forName("com.mysql.cj.jdbc.Driver")

}
