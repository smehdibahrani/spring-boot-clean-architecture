
# Clean Architecture Example

## Description

The architecture of the project follows the principles of Clean Architecture. It is a simple food delivery app. One can list stores, cousines, products and create food orders. JWT it is used for authentication.

## Running

`mvn spring-boot:run`

## Architecture

The project consists of 3 packages: *domain*, *infrastructure* and *usecase*.

### *domain* package

This module contains the models and enums and interfaces.
There are no dependencies to frameworks and/or libraries and could be extracted to its own module.

### *infrastructure* package

### *usecases* package

