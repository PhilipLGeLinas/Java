import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CSVFormatter {

    // Note: When adding args, you must include the entire filepath for each CSV file

    public static void main(String[] args) throws IOException {
        // Optional method to query the user for file names
        // ArrayList<String> filePaths = queryUser();
        ArrayList<String> filePaths = new ArrayList<>(Arrays.asList(args));
        // Map file names to an array of their lines of text
        HashMap<String, ArrayList<String>> fileContent = readFile(filePaths);
        writeSummary(fileContent);
        writeDetails(fileContent);
    }

    // Returns a map, linking CSV file names to a list of their lines of text
    private static HashMap<String, ArrayList<String>> readFile(ArrayList<String> filePaths) throws IOException {
        HashMap<String, ArrayList<String>> fileContent = new HashMap<>();
        for (String filePath : filePaths) {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            ArrayList<String> values = new ArrayList<>();
            while (reader.ready()) {
                String line = reader.readLine();
                values.add(line);
                // Map each file name to its respective lines of text
                fileContent.put(filePath.split("[/_]")[2], values);
            }
            reader.close();
        }
        return fileContent;
    }

    // Generates a summary.csv file
    private static void writeSummary(HashMap<String, ArrayList<String>> fileContent) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("examples/outputs/output_summary.csv"));
        writer.write("#ID,Name,Total");
        ArrayList<String> keySet = new ArrayList<>();
        // Write the names of each file to the summary (first letter capitalized to look nice)
        for (String key : fileContent.keySet()) {
            writer.write("," + key.substring(0, 1).toUpperCase() + key.substring(1));
            keySet.add(key);
        }
        writer.newLine();
        writer.write(",Overall,");
        int maxGrade = 0;
        // Record and write the maximum grade
        for (ArrayList<String> value : fileContent.values()) {
            String[] values = value.get(1).split(",");
            maxGrade += Integer.parseInt(values[2]);
            writer.write("," + values[2]);
        }
        writer.newLine();
        // Parse each students' id, name, and scores
        // Optionally, create a student object from each line of text
        for (int i = 2; i < fileContent.get(keySet.get(0)).size(); i++) {
            // Student student = new Student();
            String[] line = fileContent.get(keySet.get(0)).get(i).split(",");
            double finalGrade = 0;
            for (ArrayList<String> value : fileContent.values()) {
                finalGrade += Double.parseDouble(value.get(i).split(",")[3]);
            }
            // Write the student's final grade, rounded to two decimal places
            writer.write(line[0] + "," + line[1] + "," + line[2] + "," +
                    Math.round(10000 * finalGrade / maxGrade) / 100.0);
            for (String key : keySet) {
                writer.write("," + fileContent.get(key).get(i).split(",")[3]);
            }
            writer.newLine();
        }
        writer.close();
    }

    // Generates a details.csv file
    private static void writeDetails(HashMap<String, ArrayList<String>> fileContent) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("examples/outputs/output_details.csv"));
        writer.write("#ID,Name");
        // Write the header of the details file
        for (ArrayList<String> list : fileContent.values()) {
            String[] values = list.get(0).split(",");
            for (int i = 3; i < values.length; i++) {
                writer.write("," + values[i]);
            }
        }
        writer.newLine();
        writer.write(",Overall");
        // Parse and write score values
        for (ArrayList<String> list : fileContent.values()) {
            String[] values = list.get(1).split(",");
            for (int i = 3; i < values.length; i++) {
                writer.write("," + values[i]);
            }
        }
        writer.newLine();
        ArrayList<String> keySet = new ArrayList<>(fileContent.keySet());
        String defaultKey = keySet.get(0);
        // Parse each students' id and name and write them to the details file
        for (int i = 2; i < fileContent.get(defaultKey).size(); i++) {
            String[] line = fileContent.get(defaultKey).get(i).split(",");
            writer.write(line[0] + "," + line[1] + "," + line[2]);
            Iterator<ArrayList<String>> values = fileContent.values().iterator();
            // Parse each students' scores
            for (int j = 0; j < keySet.size(); j++) {
                ArrayList<String> array = values.next();
                int valueCount = array.get(0).split(",").length - 3;
                // Write student scores to the details file
                for (int k = 0; k < valueCount; k++) {
                    writer.write("," + array.get(i).split(",")[k + 3]);
                }
            }
            writer.newLine();
        }
        writer.close();
    }

	/* Optional method to invoke user input for file names
	// Asks the user for the files they would like to aggregate
	private static ArrayList<String> queryUser() {
		Scanner input = new Scanner(System.in);
		ArrayList<String> filePaths = new ArrayList<>();
		while(true) {
			String filePath = "example test case/inputs/";
			System.out.println("Please input the category name:");
			filePath += input.nextLine() + "_";
			System.out.println("Please input the file number:");
			filePath += input.nextLine() + ".csv";
			filePaths.add(filePath);
			System.out.println("Do you have more files you wish to add? (y/n)");
			if(!input.nextLine().equals("y"))
				break;
		}
		input.close();
		return filePaths;
	}

	// Optionally implement a class to store student information
	// Contains the information of each student
	private class Student {

        private String name;
		private int id;

        public Student() {
            this.name = null;
            this.id = null;
        }

        public Student(String name, int id) {
            this.name = name;
            this.id = id;
        }

		public void setName(String name) {
			this.name = name;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return this.name;
		}

		public int getId() {
			return this.id;
		}
	} */
}
