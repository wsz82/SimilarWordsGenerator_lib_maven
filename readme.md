# Similar Words Generator

It is a maven project to make a library for generating words similar to given input.

## Description

There are two core classes: Analyser and Generator. First is responsible for analysing an input, second makes new words.
Builder design pattern is used for creating parameters for Generator.

## Installation

The project uses Java SDK 12 and JUnit 5<br/> 
For tests add in VM configurations:<br/>
 -Ddir.test.files="projectRoot\src\test\files"

## Usage

```xml
<dependency>
  <groupId>io.github.wsz82</groupId>
  <artifactId>similar-words-generator</artifactId>
  <version>1.2</version>
</dependency>
```

Access through Controller class. Parameters can be set through ProgramParameters. The library is implemented in JavaFX desktop application (https://github.com/wsz82/SimilarWordsGenerator_desktop_application_JavaFX) and web application (https://github.com/wsz82/SimilarWordsGenerator_Rest_API_Spring).

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)