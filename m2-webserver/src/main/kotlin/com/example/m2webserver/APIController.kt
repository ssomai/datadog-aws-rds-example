package com.example.m2webserver

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException


@RestController
@RequestMapping("/api")
class APIController {
    @Value("\${db.url}")
    var dburl = ""
    @Value("\${db.user}")
    var dbuser = ""
    @Value("\${db.password}")
    var dbpassword = ""

    @PostMapping("/post")
    fun post(@RequestBody requestData: Map<String, Any>): String {
        var conn: Connection? = null

        try {
            //2. mysql과 연결시키기
            conn = DriverManager.getConnection(dburl, dbuser, dbpassword)
            val ps = conn.prepareStatement("SELECT * FROM mysql.slow_log limit 10")
            val result = ps.executeQuery()

            System.out.println("Successfully Connection! $result")
        } catch (e: ClassNotFoundException) {
            System.out.println("Failed because of not loading driver")
            e.printStackTrace()
        } catch (e: SQLException) {
            System.out.println("error : " + e)
            e.printStackTrace()
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close()
                }
            } catch (e:SQLException) {
                e.printStackTrace()
            }
        }

        requestData.forEach { (key: String, value: Any) ->
            println("key : $key")
            println("value : $value")
        }

        return "hello"
    }
    @PostMapping("/poste")
    fun poste(@RequestBody requestData: Map<String, Any>): String {
        var conn: Connection? = null

        try {
            //1. 드라이버 로딩 : mysql 드라이버 로딩
            //드라이버들이 읽히기만 하면 자동 객체가 생성되고 DriverManager에 등록된다.

            //2. mysql과 연결시키기
            conn = DriverManager.getConnection(dburl, dbuser, dbpassword)
            val ps = conn.prepareStatement("SELECT * FROM mysql.slow_log222 limit 10")
            val result = ps.executeQuery()
            System.out.println("Successfully Connection! $result")
//            System.out.println("Successfully Connection! ");
        } catch (e: ClassNotFoundException) {
            System.out.println("Failed because of not loading driver")
            e.printStackTrace()
        } catch (e: SQLException) {
            System.out.println("error : " + e)
            e.printStackTrace()
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close()
                }
            } catch (e:SQLException) {
                e.printStackTrace()
            }
        }

        requestData.forEach { (key: String, value: Any) ->
            println("key : $key")
            println("value : $value")
        }

        return "hello"
    }

//    // Grant the Agent access
//    @PostMapping("/datadoguserinit")
//    fun datadoguserinit(): String {
//        var conn: Connection? = null
//
//        var result: String = ""
//
//        try {
//            //1. 드라이버 로딩 : mysql 드라이버 로딩
////            Class.forName("software.aws.rds.jdbc.mysql.Driver")
////            Class.forName("com.mysql.jdbc.Driver")
//            Class.forName("com.mysql.cj.jdbc.Driver")
//            //드라이버들이 읽히기만 하면 자동 객체가 생성되고 DriverManager에 등록된다.
//
//            //2. mysql과 연결시키기
//            val url = "jdbc:mysql://cdk-datadog-awsrds-datadogawsrdsrdscluster8b2bfb5-1izs92wl2mou5.cluster-cljb4lq5kkol.ap-northeast-2.rds.amazonaws.com:5306/ddrds?useSSL=false"
//            conn = DriverManager.getConnection(url, "testuser", "testpassword")
////            val ps = conn.prepareStatement("SELECT * FROM mysql.slow_log limit 10")
////            val result = ps.executeQuery()
//
////CREATE USER datadog@'%' IDENTIFIED BY 'datadogpassword';
////GRANT REPLICATION CLIENT ON *.* TO datadog@'%' WITH MAX_USER_CONNECTIONS 5;
////GRANT PROCESS ON *.* TO datadog@'%';
////GRANT SELECT ON performance_schema.* TO datadog@'%';
//            val ps = conn.prepareStatement("""
//                CREATE USER datadog@'%' IDENTIFIED BY 'datadogpassword';
//                GRANT REPLICATION CLIENT ON *.* TO datadog@'%' WITH MAX_USER_CONNECTIONS 5;
//                GRANT PROCESS ON *.* TO datadog@'%';
//                GRANT SELECT ON performance_schema.* TO datadog@'%';
//
//                CREATE SCHEMA IF NOT EXISTS datadog;
//                GRANT EXECUTE ON datadog.* to datadog@'%';
//                GRANT CREATE TEMPORARY TABLES ON datadog.* TO datadog@'%';
//
//                DELIMITER ${'$'}${'$'}
//                CREATE PROCEDURE datadog.explain_statement(IN query TEXT)
//                    SQL SECURITY DEFINER
//                BEGIN
//                    SET @explain := CONCAT('EXPLAIN FORMAT=json ', query);
//                    PREPARE stmt FROM @explain;
//                    EXECUTE stmt;
//                    DEALLOCATE PREPARE stmt;
//                END ${'$'}${'$'}
//                DELIMITER ;
//
//                DELIMITER ${'$'}${'$'}
//                CREATE PROCEDURE datadog.enable_events_statements_consumers()
//                    SQL SECURITY DEFINER
//                BEGIN
//                    UPDATE performance_schema.setup_consumers SET enabled='YES' WHERE name LIKE 'events_statements_%';
//                END ${'$'}${'$'}
//                DELIMITER ;
//                GRANT EXECUTE ON PROCEDURE datadog.enable_events_statements_consumers TO datadog@'%';
//            """.trimIndent())
//            result = ps.execute().toString()
////            result = conn.nativeSQL("""
////                CREATE USER datadog@'%' IDENTIFIED BY 'datadogpassword';
////                GRANT REPLICATION CLIENT ON *.* TO datadog@'%' WITH MAX_USER_CONNECTIONS 5;
////                GRANT PROCESS ON *.* TO datadog@'%';
////                GRANT SELECT ON performance_schema.* TO datadog@'%';
////
////                CREATE SCHEMA IF NOT EXISTS datadog;
////                GRANT EXECUTE ON datadog.* to datadog@'%';
////                GRANT CREATE TEMPORARY TABLES ON datadog.* TO datadog@'%';
////
////                DELIMITER ${'$'}${'$'}
////                CREATE PROCEDURE datadog.explain_statement(IN query TEXT)
////                    SQL SECURITY DEFINER
////                BEGIN
////                    SET @explain := CONCAT('EXPLAIN FORMAT=json ', query);
////                    PREPARE stmt FROM @explain;
////                    EXECUTE stmt;
////                    DEALLOCATE PREPARE stmt;
////                END ${'$'}${'$'}
////                DELIMITER ;
////
////                DELIMITER ${'$'}${'$'}
////                CREATE PROCEDURE datadog.enable_events_statements_consumers()
////                    SQL SECURITY DEFINER
////                BEGIN
////                    UPDATE performance_schema.setup_consumers SET enabled='YES' WHERE name LIKE 'events_statements_%';
////                END ${'$'}${'$'}
////                DELIMITER ;
////                GRANT EXECUTE ON PROCEDURE datadog.enable_events_statements_consumers TO datadog@'%';
////            """.trimIndent())
//
////            val result = conn.nativeSQL("SELECT 1")
//            System.out.println("Successfully Connection! $result");
////            System.out.println("Successfully Connection! ");
//        } catch (e: ClassNotFoundException) {
//            System.out.println("Failed because of not loading driver");
//            e.printStackTrace()
//        } catch (e: SQLException) {
//            System.out.println("error : " + e)
//            e.printStackTrace()
//        } finally {
//            try {
//                if (conn != null && !conn.isClosed()) {
//                    conn.close()
//                }
//            } catch (e:SQLException) {
//                e.printStackTrace()
//            }
//        }
//
////        requestData.forEach { (key: String, value: Any) ->
////            println("key : $key")
////            println("value : $value")
////        }
//
//        return "result $result"
//    }

}