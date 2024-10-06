# LunarCode

## Introduction

**LunarCode** is a JVM-based scripting language that is currently in early development. The goal is to create a dynamic, interpreted language that can seamlessly interoperate with Kotlin and other JVM languages such as Java, Groovy, and Scala. It offers a flexible, lightweight environment for scripting, testing, and automation, all while leveraging the powerful JVM ecosystem.

## Motivation

### Why Create Another Language?

As Kotlin continues to grow in popularity, its modern syntax, null-safety, and excellent tooling have made it a preferred choice for JVM development. However, while Kotlin and other JVM languages excel in compiled environments, there remains a gap for developers who need a lightweight, interpreted language that can run dynamically on the JVM.

The inspiration behind **LunarCode** is to provide a scripting solution that:
1. **Seamlessly Integrates with Kotlin** – Enabling direct access to Kotlin classes, methods, and libraries without compromising Kotlin’s strengths.
2. **Leverages the JVM Ecosystem** – Allowing easy integration with the wide array of libraries, frameworks, and tools available in the JVM world.
3. **Simplifies Scripting** – Offering a dynamically-typed, interpreted approach for rapid prototyping, testing, and automation, without the overhead of compilation.

### Key Objectives
- **Scripting on the JVM**: To provide a dynamic, interpreted language that runs on the JVM, allowing for fast iterations without the need for compilation.
- **Kotlin and JVM Interoperability**: To create a language that can directly interact with Kotlin, Java, and other JVM-based languages, reducing friction in mixed-language projects.
- **Developer Productivity**: To streamline the development process by offering a scripting solution that can easily integrate into existing Kotlin or JVM-based workflows.
- **Ease of Use**: Focus on simplicity and developer experience, especially for prototyping and automation, while maintaining the full power of JVM libraries.

## Project Status

This project is still in the early stages of development. Core features, language syntax, and interoperability capabilities are actively being designed and implemented.

### Planned Features
- **Interpreted Language**: The language will be interpreted, providing immediate feedback without requiring a compilation step.
- **Full JVM Interoperability**: Directly call and utilize Kotlin, Java, and other JVM-based libraries from within scripts.
- **Dynamic Typing**: Unlike Kotlin, the language will be dynamically typed, allowing for more flexible scripting use cases.
- **Lightweight Design**: The language aims to be minimalistic and efficient, making it ideal for scripting tasks, small automation jobs, or rapid prototyping.

## Future Roadmap

Given the project is in its infancy, here’s a high-level view of what’s planned:

1. **Basic Language Features**:
   - Core language syntax and grammar.
   - Primitive types and basic control structures.
   
2. **Kotlin and JVM Interoperability**:
   - Ability to directly call Kotlin and Java methods.
   - Seamless handling of Kotlin classes, including support for null safety and data classes.
   
3. **Scripting and REPL**:
   - Development of an interactive Read-Eval-Print Loop (REPL) for running code dynamically.
   - Support for executing standalone script files.

4. **Tooling and Integration**:
   - IDE support (starting with IntelliJ IDEA) for syntax highlighting, code completion, and debugging.
   - Gradle and Maven integration to allow easy use of the language in JVM-based projects.

5. **Standard Library Expansion**:
   - Adding standard libraries for common scripting tasks (file handling, networking, etc.).

## Use Cases

Here are some potential use cases for **LunarCode**:

- **Plugin Development**: Build plugins for existing JVM applications.
- **Prototyping**: Quickly prototype features by scripting with direct access to JVM libraries and Kotlin.
- **Scripting Automation**: Write dynamic scripts to automate JVM-based application tasks without recompiling.
- **Testing**: Use the language as a testbed for rapidly experimenting with code and concepts in a Kotlin/JVM project.
- **Interfacing with Kotlin Projects**: Directly interact with and extend Kotlin applications using an interpreted scripting language.

## Getting Started

Since development is ongoing, there is no official release yet. for now you can run the language by compiling the project

```bash

git clone https://github.com/RohitKushvaha01/LunarCode
cd LunarCode
bash gradlew run

```
