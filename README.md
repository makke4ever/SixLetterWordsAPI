# WordCombinationsAPI

You can download the latest release to start.

To run it, download the jar file, go to the location of the file on your command line and execute: `java -jar word-combinations-api-0.0.1.jar`.

For your convenience, I have provided a postman collection with a call to the rest-api.<br>
In the body, you can specify the input file of the application.

The jar file contains an `application.properties` file.<br>
Modify the value of parameter `combination.word.size` to change the number of characters the combination needs to have to be eligable as a result. The default value is 6.<br>
**Stop the application before modifying! Auto reloading of the application properties is not supported.**
