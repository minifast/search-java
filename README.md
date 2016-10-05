# ElasticSearch for Java Example

## Common issues

### Jar Hell

For Java 8, you may run into a JarHell exception thrown by ElasticSearch while
booting an integration test node. This is the relevant thread on ES's Github issues:

https://github.com/elastic/elasticsearch/pull/13465#issuecomment-142124441

To resolve remaining conflicts, which may exist if you're using the
`spring-boot-starter-test` package from Maven, you may need to add an exclusion:

```
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
    <exclusions>
      <exclusion>
        <groupId>org.ow2.asm</groupId>
        <artifactId>asm</artifactId>
      </exclusion>
    </exclusions>
  </dependency>

```

### Access Control Exception
https://github.com/elastic/elasticsearch/issues/16459

Resolved by adding `-Dtests.security.manager=false` to VM options in runtime configurations
