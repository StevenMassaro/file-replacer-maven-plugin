# file-replacer-maven-plugin

A simple maven plugin which replaces placeholders in a file with the contents of other files.

## Usage example

Given the following `<plugin>` declaration in your pom.xml:

```xml
<plugin>
    <groupId>com.stevenmassaro</groupId>
    <artifactId>file-replacer-maven-plugin</artifactId>
    <version>0.0.1</version>
    <executions>
        <execution>
            <goals>
                <goal>replace</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <file>${project.build.directory}/example.txt</file>
        <filters>
            <key>${project.build.directory}/replacement.txt</key>
        </filters>
    </configuration>
</plugin>
```

And the following files:

- example.txt:
  ```
  hello key
  ```
- replacement.txt:
  ```
  world
  ```
  
After execution, `example.txt` will look like:
```
hello world
```