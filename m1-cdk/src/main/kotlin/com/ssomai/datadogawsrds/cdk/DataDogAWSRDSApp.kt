package com.ssomai.datadogawsrds.cdk

import software.amazon.awscdk.*
import software.amazon.awscdk.services.ec2.*
import software.amazon.awscdk.services.rds.*
import software.amazon.awscdk.services.rds.InstanceProps
import software.constructs.Construct

class DataDogAWSRDSStack(scope: Construct?, id: String?, props: StackProps?) : Stack(scope, id, props) {
    val idprefix = "datadogawsrds"

    init {
        try {
            // https://bobbyhadz.com/blog/aws-cdk-vpc-example
            val vpc = Vpc.Builder.create(this, "$idprefix-vpc")
                .vpcName("$idprefix-vpc-name")
                .maxAzs(2)
                .subnetConfiguration(
                    listOf(
                        SubnetConfiguration.builder()
                            .name("$idprefix-subnet-public1")
                            .subnetType(SubnetType.PUBLIC)
                            .build(),
                    )
                )
                .build()

            // https://docs.datadoghq.com/database_monitoring/setup_mysql/aurora/?tab=mysql57#configure-mysql-settings
            val rdspg = ParameterGroup.Builder.create(this, "${idprefix}-rds-parametergroup")
                .description("for datadog-aws-rds webinar")
                .engine(
                    DatabaseClusterEngine.aurora(
                        AuroraClusterEngineProps.builder().version(AuroraEngineVersion.VER_1_22_2).build()
                    )
                )
                // https://docs.datadoghq.com/database_monitoring/setup_mysql/aurora/?tab=mysql56#configure-mysql-settings
                .parameters(
                    mapOf(
                        "performance_schema" to "1",
                        "performance_schema_consumer_events_statements_current" to "1",
                        "performance_schema_consumer_events_statements_history" to "1",
                        "performance_schema_consumer_events_statements_history_long" to "1",
                        "performance_schema_max_digest_length" to "4096",
                    )
                )
                .build()

            val rdsport = 5306
            val rdsclustersecuritygroup = SecurityGroup.Builder
                .create(this, "$idprefix-rdscluster-securitygroup")
                .vpc(vpc)
                .allowAllOutbound(true)
                .build()
            rdsclustersecuritygroup.addIngressRule(Peer.anyIpv4(), Port.tcp(rdsport))

            // rdscluster
            // auroraDB는 클러스터로만 생성할수 있음
            val rdscluster = DatabaseCluster.Builder.create(this, "${idprefix}-rdscluster")
                .engine(
                    DatabaseClusterEngine.aurora(
                        AuroraClusterEngineProps.builder().version(AuroraEngineVersion.VER_1_22_2).build()
                    )
                )
                .instanceProps(
                    InstanceProps.builder()
                        .vpc(vpc)
                        .vpcSubnets(SubnetSelection.builder().subnetType(SubnetType.PUBLIC).build())
                        .securityGroups(listOf(rdsclustersecuritygroup))
                        .instanceType(InstanceType.of(InstanceClass.BURSTABLE3, InstanceSize.SMALL))
                        .build()
                )
                .parameterGroup(rdspg)
                .credentials(Credentials.fromPassword("testuser", SecretValue.Builder.create("testpassword").build()))
                .instances(1)
                .port(rdsport)
                .defaultDatabaseName("ddrds")
                .build()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun main() {
    val app = App()
    val props = StackProps.Builder()
        .env(
            Environment.builder()
                .region("ap-northeast-2")
                .build()
        )
        .build()
    DataDogAWSRDSStack(app, "CDK-DataDog-AWSRDS", props)
    app.synth()
}
