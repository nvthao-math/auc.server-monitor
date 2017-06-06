/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;
import org.auc.core.file.utils.Logger;

/**
 *
 * @author thaonguyen
 */
public class TestFile {

    public static final String TAG = TestFile.class.getSimpleName();

    public static void main(String args[]) throws IOException {
//        byteChannelRead();

//        String string = "foo bar";
//        byte[] byteArray = string.getBytes();
//        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
//        byteChannelWrite(byteBuffer);
        long t1 = System.currentTimeMillis();
        fileChannelRead();
        long duration = System.currentTimeMillis() - t1;
        Logger.info(TAG, "time: " + duration + " ms");
//        fileChannelWrite(byteBuffer);

    }

    public static void byteChannelRead() throws IOException {

        Path filePath = FileSystems.getDefault().getPath(".", "temp.txt");

        SeekableByteChannel byteChannel = Files.newByteChannel(filePath);
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        Charset charset = Charset.forName("US-ASCII");
        while (byteChannel.read(byteBuffer) > 0) {
            byteBuffer.rewind();
            System.out.print(charset.decode(byteBuffer));
            byteBuffer.flip();
        }
    }

    public static void byteChannelWrite(ByteBuffer byteBuffer)
            throws IOException {

        Set options = new HashSet();
        options.add(StandardOpenOption.CREATE);
        options.add(StandardOpenOption.APPEND);

        Path file = Paths.get("./byByteChannel.txt");

        SeekableByteChannel byteChannel = Files.newByteChannel(file, options);
        byteChannel.write(byteBuffer);
    }

    public static void fileChannelRead() throws IOException {
        int count = 0;
        RandomAccessFile randomAccessFile = new RandomAccessFile("/home/thaonguyen/sand-box/data/speed-profiles/speed_profile_data_from_20130528_To_20150319.csv",
                "r");
        FileChannel fileChannel = randomAccessFile.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        Charset charset = Charset.forName("US-ASCII");
        while (fileChannel.read(byteBuffer) > 0) {
            byteBuffer.rewind();
            System.out.print(charset.decode(byteBuffer).toString());
            byteBuffer.flip();
            count++;
        }
        fileChannel.close();
        randomAccessFile.close();
    }

    public static void fileChannelWrite(ByteBuffer byteBuffer)
            throws IOException {

        Set options = new HashSet();
        options.add(StandardOpenOption.CREATE);
        options.add(StandardOpenOption.APPEND);

        Path path = Paths.get("./byFileChannel.txt");

        FileChannel fileChannel = FileChannel.open(path, options);
        fileChannel.write(byteBuffer);
        fileChannel.close();
    }

}
