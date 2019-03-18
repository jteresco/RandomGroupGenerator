
/**
 * Random group generator for labs and projects
 * 
 * Command-line parameters:
 * 
 * args[0]: the name of a file containing people who to be added to groups
 * args[1]: the name of a file containing the GitHub Ids of those people
 * args[2]: preferred number of people per group (default: 2)
 * 
 * @author Jim Teresco
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class RandomGroupGenerator {

    public static void main(String args[]) {
        
        // must have 2 or 3 command-line parameters
        if (args.length < 2 || args.length > 3) {
            System.err.println("Usage: java RandomGroupGenerator listfile {idfile|-} [groupsize]");
            System.exit(1);
        }
        
        // ensure valid group size
        int groupSize = 2;
        if (args.length == 3) {
            try {
                groupSize = Integer.parseInt(args[2]);
            }
            catch (NumberFormatException e) {
                System.err.println("Group size must be an integer >= 2");
                System.exit(1);
            }
        
            if (groupSize < 2) {
                System.err.println("Group size must be an integer >= 2");
                System.exit(1);
            
            }
        }
        
        // read list of students from the file
        Scanner s = null;
        Scanner s2 = null;
        ArrayList<String> sList = new ArrayList<String>();
        try {
            s = new Scanner(new File(args[0]));
            if (!args[1].equals("-")) s2 = new Scanner(new File(args[1]));
            while (s.hasNextLine()) {
		String entry = s.nextLine().trim();
		if (s2 != null) {
		    entry += " (" + s2.nextLine().trim() + ")";
		}
                sList.add(entry);
            }
            s.close();
            if (s2 != null) s2.close();
        }
        catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
        
        // choose our random groups
        Random r = new Random();
        int groupNum = 1;
        while (sList.size() >= groupSize) {
            System.out.print("Group " + groupNum + ": " + 
                sList.remove(r.nextInt(sList.size())));
            for (int i = 1; i < groupSize; i++) {
                System.out.print(" + " + sList.remove(r.nextInt(sList.size())));
            }
            System.out.println();
            groupNum++;
        }
        
        if (!sList.isEmpty()) {
            System.out.print("Tack on:");
            while (!sList.isEmpty()) {
                System.out.print(" " + sList.remove(r.nextInt(sList.size())));
            }
            System.out.println();
        }
    }
}
