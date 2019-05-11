# Converters and Command packages

`Converters` convert Domain Objects to Command Objects.

A `Command Object` brings data from a HTML form to a Java Bean. Command Objects [are an implementation of the DTO design pattern](https://stackoverflow.com/questions/40307821/command-objects-and-dtos-difference).

You should not expose your Domain Objects because your requirements for the domain may be different down the line from your web tier. So you expose your Command Objects.

`Converters` convert between your Domain and Command objects, basically to communicate between the layers of your application. `Converters` are Spring `@Component`'s that implement the [Converter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/convert/converter/Converter.html) class. Thus, for each Command Object you will have two Converters:

- One from the Command Object to the Domain Object (Web -> App)
- One from the Domain Object to the Command Object (App -> Web)

Since this is not a thread safe class, I'm using the `@Synchronized` from Project Lombok for the Converters.

## TODO
I have to research and understand why the `@Nullable` on the Converters.