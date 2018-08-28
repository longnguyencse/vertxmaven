package dev.at.mikorn.com;

import dev.at.mikorn.com.debug.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class App extends AbstractVerticle {

    @Override
    public void start(Future<Void> fut) {
        vertx
                .createHttpServer()
                .requestHandler(r -> {
                    r.response().end("<h1>Hello from my first " +
                            "Vert.x 3 application</h1>");
                })
                .listen(8080, result -> {
                    if (result.succeeded()) {
                        fut.complete();
                    } else {
                        fut.fail(result.cause());
                    }
                });
    }

    public static void main(String[] args) {
//        Run
//        mvn clean package
        // java -jar target/my-first-app-1.0-SNAPSHOT-fat.jar
        Runner.runExample(App.class);
    }
}
