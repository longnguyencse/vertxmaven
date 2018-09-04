package dev.at.mikorn.com;

import dev.at.mikorn.com.debug.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.FileSystem;
import io.vertx.core.file.OpenOptions;
import io.vertx.rx.java.RxHelper;
import rx.Observable;

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
        System.out.println(System.getProperty("user.dir"));

        FileSystem fileSystem = vertx.fileSystem();
        fileSystem.open("/data.txt", new OpenOptions(), result -> {

            AsyncFile file = result.result();
            Observable<Buffer> observable = RxHelper.toObservable(file);
            observable.doOnError(e -> {
               System.out.println("Onerror");
            });
            observable.doOnCompleted(() -> {
                System.out.println("On complete");
            });
            observable.doOnNext((s) -> {
                System.out.println("Do On Next");
            });
        });

        System.exit(0);
    }

    public static void main(String[] args) {
        Runner.runExample(App.class);
    }
}
