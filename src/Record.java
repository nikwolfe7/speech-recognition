import java.io.*;
import java.nio.ByteOrder;

import javax.sound.sampled.*;
import javax.sound.sampled.spi.FormatConversionProvider;

public class Record {

	public static final long RECORD_TIME = 5000;
	
	private boolean stopCapture = false;
	private ByteArrayOutputStream audioStream;
	private AudioFormat audioFormat;
	private TargetDataLine targetDataLine;
	private AudioInputStream audioInputStream;
	private SourceDataLine sourceDataLine;

	File wavFile = new File("testaudio.wav");

	private AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

	private TargetDataLine line;

	

	public void start() {
		try {
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, null);

			if (!AudioSystem.isLineSupported(info)) {
				System.out.println("Line not supported!");
				System.exit(0);
			}

			System.out.println("Start capturing...");

			AudioInputStream ais = new AudioInputStream(line);

			System.out.println("Start recording");

			// start recording
			AudioSystem.write(ais, fileType, wavFile);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	// WAV to output
	public void finish() {
		line.stop();
		line.close();
		System.out.println("Recording stoppped!");
	}

}
