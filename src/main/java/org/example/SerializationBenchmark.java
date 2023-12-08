package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import org.example.album.AlbumProto;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1)
@Warmup(iterations = 5, timeUnit = TimeUnit.MILLISECONDS, time = 5000)
@Measurement(iterations = 5, timeUnit = TimeUnit.MILLISECONDS, time = 5000)
public class SerializationBenchmark {

    private AlbumModel albumModel;
    private String jsonStringPrep;
    private byte[] protobufBytesPrep;

    @Setup(Level.Trial)
    public void setup() throws JsonProcessingException {
        albumModel = new AlbumModel();
        albumModel.title = "Album Title";
        albumModel.artist = new String[]{"Artist 1", "Artist 2", "Artist 3", "Other", "Snooooop", "Tyler Swift"};
        albumModel.release_year = 2023;
        albumModel.song_title = new String[]{"Bentley,benz and beamer","1 to 100","Heartbreak","Song 1", "Song 2"};

        AlbumProto.Album tmpProto = AlbumProto.Album.newBuilder()
                .setTitle(albumModel.title)
                .addAllArtist(List.of(albumModel.artist))
                .setReleaseYear(albumModel.release_year)
                .addAllSongTitle(List.of(albumModel.song_title))
                .build();

        jsonStringPrep = new ObjectMapper().writeValueAsString(albumModel);
        protobufBytesPrep = tmpProto.toByteArray();

    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public String serializeJson() throws JsonProcessingException {
       return new ObjectMapper().writeValueAsString(albumModel);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public byte[] serializeProtobuf() {
        AlbumProto.Album tmpProto = AlbumProto.Album.newBuilder()
                .setTitle(albumModel.title)
                .addAllArtist(List.of(albumModel.artist))
                .setReleaseYear(albumModel.release_year)
                .addAllSongTitle(List.of(albumModel.song_title))
                .build();
        return tmpProto.toByteArray();
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public AlbumModel deserializeJson() throws JsonProcessingException {
        return new ObjectMapper().readValue(jsonStringPrep, AlbumModel.class);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public AlbumModel deserializeProtobuf() throws InvalidProtocolBufferException {
        AlbumProto.Album protoBufObject = AlbumProto.Album.parseFrom(protobufBytesPrep);
        AlbumModel albumModel = new AlbumModel();
        albumModel.setTitle(protoBufObject.getTitle());
        albumModel.setArtist(protoBufObject.getArtistList().toArray(new String[0]));
        albumModel.setRelease_year(protoBufObject.getReleaseYear());
        albumModel.setSong_title(protoBufObject.getSongTitleList().toArray(new String[0]));

        return albumModel;
    }
}
