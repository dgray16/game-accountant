package com.spring.func.test.app;

import com.sun.tools.javac.Main;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.io.FileWriter;
import java.util.stream.IntStream;

@SpringBootApplication
public class TestApplication {

    @SneakyThrows
    public static void main(String[] args) {
        /*createNewClass();
        Class<?> initializerClass = Class.forName("com.spring.func.test.app.Initializer");*/
        new SpringApplicationBuilder(TestApplication.class)
                .initializers(new Initializer())
                .run(args);
    }

    private static void createNewClass() {
        String className = "Initializer.java";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("package com.spring.func.test.app;\n" +
                "\n" +
                "import com.spring.func.test.beans.*;\n" +
                "import org.springframework.context.ApplicationContextInitializer;\n" +
                "import org.springframework.context.support.GenericApplicationContext;\n" +
                "\n" +
                "public class Initializer implements ApplicationContextInitializer<GenericApplicationContext> {\n" +
                "\n" +
                "    @Override\n" +
                "    public void initialize(GenericApplicationContext context) {\n");

        IntStream.range(1, 1000).forEach(i -> {
            String string = String.format("context.registerBean(TestBean%d.class);\n", i);
            stringBuilder.append(string);
        });

        stringBuilder.append("}\n" +
                "\n" +
                "}");

        try (FileWriter fileWriter = new FileWriter("src/main/java/com/spring/func/test/app/" + className, true)) {
            fileWriter.write(stringBuilder.toString());
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Main.compile(new String[]{className});
    }

}
