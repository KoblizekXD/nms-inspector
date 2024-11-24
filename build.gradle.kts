plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.7.5"
}

group = "lol.koblizek"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("1.21.3-R0.1-SNAPSHOT")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}