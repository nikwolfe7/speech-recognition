import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;


public class Recorder extends Thread {
	
	private ByteArrayOutputStream audioOutput;
	private boolean stopCapture = false;
	private TargetDataLine targetDataLine;
	
	private byte[] audioBuffer = new byte[AudioConstants.KHZ16BUFFER.getValue()];
	
	public Recorder(Microphone mic) {
		this.targetDataLine = mic.getOpenMicrophone();
		if(!targetDataLine.isOpen()) {
			try {
				targetDataLine.open();
			} catch (LineUnavailableException e) {
				System.out.println("Couldn't open Microphone!");
				e.printStackTrace();
			}
		} else if (targetDataLine.isActive()) {
			System.out.println("Microphone opened... Recording audio!");
		} else {
			System.out.println("Microphone opened... Not yet recording audio...");
		} 
	}
	
	public void stopRecording() {
		stopCapture = true;
	}
	
	public void run() {
		this.audioOutput = new ByteArrayOutputStream();
		System.out.println("Recording started!");
		while (!this.stopCapture && !Thread.currentThread().isInterrupted()) {
			Integer count = targetDataLine.read(audioBuffer, 0, audioBuffer.length);
			if(count > 0) {
				audioOutput.write(audioBuffer, 0, count);
				for(byte b : audioBuffer) {
					System.out.println(b);
				}
			}
		}
		// We're done...
		try {
			audioOutput.close();
			System.out.println("Microphone closed.");
		} catch (IOException e) {
			System.out.println("Unable to close audio ouptut!");
			e.printStackTrace();
		}
	}
	
	
	

}
