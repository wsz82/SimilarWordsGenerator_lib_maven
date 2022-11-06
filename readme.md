# Similar Words Generator

A library for generating words similar to given input.

## Description

There are two core classes: Seed and WordsGenerator. First is responsible for keeping data to generate output, second
generates output. GenerateParameters object is used to specify expected output.

## Installation

The project uses Java SDK 17<br/>

## Usage

```xml

<dependency>
    <groupId>io.github.wsz82</groupId>
    <artifactId>similar-words-generator</artifactId>
    <version>2.0</version>
</dependency>
```

Pass list with words to Seed and generate new words with WordsGenerator. The library is used in:

- desktop application (https://github.com/wsz82/SimilarWordsGenerator_desktop_application_JavaFX)
- web application (https://github.com/wsz82/SimilarWordsGenerator_web_application_Spring).

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License

[MIT](https://choosealicense.com/licenses/mit/)