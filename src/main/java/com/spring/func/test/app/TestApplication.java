package com.spring.func.test.app;

import com.sun.tools.javac.Main;

import java.io.FileWriter;
import java.util.stream.IntStream;

public class TestApplication {

    public static void main(String[] args) {
        IntStream.range(1, 1000).forEach(TestApplication::createNewClass);
    }


    private static void createNewClass(int i) {
        String className = String.format("TestBean%d.java", i);

        try (FileWriter fileWriter = new FileWriter("src/main/java/com/spring/func/test/beans/" + className, true)) {
            fileWriter.write(String.format(
                    "package com.spring.func.test.beans;\n\n" +
                    "import org.springframework.stereotype.Component;\n" +
                    "\n" +
                    "@Component\n" +
                    "public class TestBean%d {\n" +
                    "}",
                    i)
            );
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int compile = Main.compile(new String[]{className});
        System.out.println(String.format("%s has been compiled with code: %d", className, compile));
    }

}
