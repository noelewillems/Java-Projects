// Noel Willems: MMTest
// This class is essentially the "driver" for the program.
// Handles file input.

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class MMTest {

	// Main method
	public static void main(String[] args) throws FileNotFoundException {

		String fileName = "";
		File file = null;
		BufferedReader br = null;
		Scanner sc = null;

		// Creating new MemMgr test object
		MemMgr test = new MemMgr();

		// If there is the incorrect # of arguments (i.e., nothing at all, or too many)
		try {
			if (args.length > 1 || args.length == 0) {
				System.out.println("Incorrect # of args: java MMTest <input file name>");
				System.exit(0);
			}

			// If args count is correct
			fileName = args[0];
			file = new File(fileName);
			br = new BufferedReader(new FileReader(fileName));

		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}

		// Scanning through file and sending values to appropriate methods.
		sc = new Scanner(file);
		int id;
		int size;
		String[] splitLine;

		while (sc.hasNext()) {
			String line = sc.nextLine();

			// If it finds a comment
			if (line.contains("//")) {
				continue;

				// If it finds an empty line
			} else if (line.equals("")) {
				continue;

				// If it finds the init "command"
			} else if (line.contains("init")) {
				splitLine = line.split(" ");
				size = Integer.parseInt(splitLine[1]);
				test.init(size);

				// If it finds the malloc "command"
			} else if (line.contains("malloc")) {
				splitLine = line.split(" ");
				id = Integer.parseInt(splitLine[1]);
				size = Integer.parseInt(splitLine[2]);
				try {
					test.malloc(id, size);
				} catch (MyOutOfMemoryException e) {
					System.out.println(e.toString());
				}

				// If it finds the print "command"
			} else if (line.contains("print")) {
				test.print();
			} else if (line.contains("free")) {
				splitLine = line.split(" ");
				id = Integer.parseInt(splitLine[1]);
				try {
					test.free(id);
				} catch (MyInvalidMemoryException e) {
					System.out.println(e.toString());
				}
			}
		}
		sc.close();
	}
}
