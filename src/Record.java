import java.io.*;
import java.nio.ByteOrder;

import javax.sound.sampled.*;
import javax.sound.sampled.spi.FormatConversionProvider;

public class Record {

	public static final long RECORD_TIME = 5000;

	File wavFile = new File("testaudio.wav");

	private AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

	private TargetDataLine line;

	public AudioFormat getAudioFormat() {
		Float sampleRate = new Float(AudioConstants.KHZ16.getValue());
		Integer sampleSizeInBits = 16;
		Integer channels = AudioConstants.MONO.getValue();
		Boolean signed = true;
		Boolean bigEndian = (java.nio.ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN);
		AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
				channels, signed, bigEndian);
		return format;
	}

	public void start() {
		try {
			AudioFormat format = getAudioFormat();
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

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
