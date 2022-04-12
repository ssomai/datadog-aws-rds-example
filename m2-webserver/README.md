01_properties/props 폴더를 생성하시고
01_properties/props_sample 폴더의 파일들을 복사하시고,
01_properties/props.properties에 값을 세팅해주시면 됩니다.

application.properties에   
db.url  
db.user  
db.password   
값을 세팅해주시면 됩니다.

gradlew b_build 하시면 
build/dockerfiles에 build file들이 생성됩니다.


datadog agent와 webserver를 실행시키기기 전에  
아래 링크를 참조해서 mysql에 datadog user와 permission등을 세팅하셔야 합니다.  
https://docs.datadoghq.com/integrations/mysql/  
https://docs.datadoghq.com/database_monitoring/setup_mysql/aurora/?tab=docker#configure-mysql-settings



    CREATE USER datadog@'%' IDENTIFIED BY 'datadogpassword';
    GRANT REPLICATION CLIENT ON *.* TO datadog@'%' WITH MAX_USER_CONNECTIONS 5;
    GRANT PROCESS ON *.* TO datadog@'%';
    GRANT SELECT ON performance_schema.* TO datadog@'%';

    CREATE SCHEMA IF NOT EXISTS datadog;
    GRANT EXECUTE ON datadog.* to datadog@'%';
    GRANT CREATE TEMPORARY TABLES ON datadog.* TO datadog@'%';

    DELIMITER ${'$'}${'$'}
    CREATE PROCEDURE datadog.explain_statement(IN query TEXT)
        SQL SECURITY DEFINER
    BEGIN
        SET @explain := CONCAT('EXPLAIN FORMAT=json ', query);
        PREPARE stmt FROM @explain;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;
    END ${'$'}${'$'}
    DELIMITER ;

    DELIMITER ${'$'}${'$'}
    CREATE PROCEDURE datadog.enable_events_statements_consumers()
        SQL SECURITY DEFINER
    BEGIN
        UPDATE performance_schema.setup_consumers SET enabled='YES' WHERE name LIKE 'events_statements_%';
    END ${'$'}${'$'}
    DELIMITER ;
    GRANT EXECUTE ON PROCEDURE datadog.enable_events_statements_consumers TO datadog@'%';

