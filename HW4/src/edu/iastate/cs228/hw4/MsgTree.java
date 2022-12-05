package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * @author Kyle Springborn
 * 
 * Decodes a secret message using binary trees.
 * Prints out characters and their respective codes.
 * After printing characters and codes, displays the
 * hidden message.
 *
 */
public class MsgTree {
	
	public char payloadChar;
	public MsgTree left;
	public MsgTree right;
	
	private static int index = 0;
	private static String encoder;
	private static String temp;
	private static String newEncoder;
	private static String userFile;
	
	/**
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scan = new Scanner(System.in);
		System.out.print("Please enter filename to decode: ");
		userFile = scan.nextLine().trim();
		scan.close();
		
		File file = new File(userFile);
		Scanner fileScan;
		
		try {
			fileScan = new Scanner(file);
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("File entered was not found.");
		}
		
		encoder = fileScan.nextLine();
		temp = fileScan.nextLine();
		newEncoder = "";
		
		for (int i = 0; i < temp.length(); i++) {
			if (temp.charAt(i) != '1' && temp.charAt(i) != '0') {
				encoder += "\n";
				encoder += temp;
				newEncoder = fileScan.nextLine();
				i = temp.length();
			} else {
				newEncoder = temp;
			}
			
		}
		
		fileScan.close();
		
		MsgTree mainTree = new MsgTree(encoder);
		
		System.out.println("Character\tCode: ");
		System.out.println("------------------------");		
		printCodes(mainTree, "");
		System.out.println("------------------------");
		decode(mainTree, newEncoder);
	}
	
	/**
	 * Tree constructor
	 * 
	 * @param EncodedStr
	 */
	public MsgTree(String EncodedStr) {
		this.payloadChar = EncodedStr.charAt(index);
		index++;
		
		this.left = new MsgTree(EncodedStr.charAt(index));
		if (this.left.payloadChar == '^') {
			this.left = new MsgTree(EncodedStr);
		}
		index++;
		
		this.right = new MsgTree(EncodedStr.charAt(index));
		if (this.right.payloadChar == '^') {
			this.right = new MsgTree(EncodedStr);
		}
	}
	
	/**
	 * Single node constructor
	 * 
	 * @param payloadChar
	 */
	public MsgTree(char payloadChar) {
		this.payloadChar = payloadChar;
	}
	
	/**
	 * Prints values from the binary tree
	 * 
	 * @param treeRoot
	 * @param msg
	 */
	public static void printCodes(MsgTree treeRoot, String msg) {
		if (treeRoot == null) {
			return;
		}
		
		if (treeRoot.payloadChar != '^') {
			System.out.print(treeRoot.payloadChar + "\t\t");
			System.out.println(msg);
		}
		
		printCodes(treeRoot.left, msg + "0");
		printCodes(treeRoot.right, msg + "1");
	}
	
	/**
	 * Decodes the message using binary tree
	 * 
	 * @param codes
	 * @param msg
	 */
	public static void decode(MsgTree codes, String msg) {
		MsgTree thisTree = codes;
		char current;
		int decoderIndex = 0;
		int size = msg.length();
		int previousDecoderIndex = msg.length() - 1;
		String hiddenMessage = "";
		
		while (decoderIndex < size) {
			current = msg.charAt(decoderIndex);
			
			if (current == '0') {
				if (thisTree.left == null) {
					hiddenMessage += thisTree.payloadChar;
					thisTree = codes;
				} else {
					thisTree = thisTree.left;
					decoderIndex++;
				}
			}
			else if (current == '1') {
				if (thisTree.right == null) {
					hiddenMessage += thisTree.payloadChar;
					thisTree = codes;
				} else {
					thisTree = thisTree.right;
					decoderIndex++;
				}
			}
			
			if (decoderIndex == previousDecoderIndex) {
				if (current == '0') {
					hiddenMessage += thisTree.left.payloadChar;
					decoderIndex++;
				}
				if (current == '1') {
					hiddenMessage += thisTree.right.payloadChar;
					decoderIndex++;
				}
			}
		}
		
		System.out.println(hiddenMessage);
	}
}
