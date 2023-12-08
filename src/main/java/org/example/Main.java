package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.album.AlbumProto;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Options jsonSerializeOptions = new OptionsBuilder()
                .include(SerializationBenchmark.class.getSimpleName() + ".serializeJson")
                .forks(1)
                .build();
        new Runner(jsonSerializeOptions).run();

        Options jsonDeserializeOptions = new OptionsBuilder()
                .include(SerializationBenchmark.class.getSimpleName() + ".deserializeJson")
                .forks(1)
                .build();
        new Runner(jsonDeserializeOptions).run();

        Options protobufSerializeOptions = new OptionsBuilder()
                .include(SerializationBenchmark.class.getSimpleName() + ".serializeProtobuf")
                .forks(1)
                .build();
        new Runner(protobufSerializeOptions).run();

        Options protobufDeserializeOptions = new OptionsBuilder()
                .include(SerializationBenchmark.class.getSimpleName() + ".deserializeProtobuf")
                .forks(1)
                .build();
        new Runner(protobufDeserializeOptions).run();
    }
}


